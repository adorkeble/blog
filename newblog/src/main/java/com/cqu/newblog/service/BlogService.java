package com.cqu.newblog.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.druid.util.Utils;
import com.cqu.newblog.dao.BlogDao;
import com.cqu.newblog.dao.TypeDao;
import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.redis.*;
import com.cqu.newblog.utils.MarkdownUtils;
import com.cqu.newblog.vo.*;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  14:20
 */
@Service
public class BlogService {
    @Autowired
    BlogDao blogDao;
    @Autowired
    SearchService searchService;
    @Autowired
    TagService tagService;
    @Autowired
    TypeService typeService;
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    BlogTagService blogTagService;

    public List<Blog> getBlogList() {
        return blogDao.getBlogList();
    }

    public List<IndexVo> getIndexVoList() {

        List<IndexVo> indexVos = new ArrayList<IndexVo>();
        List<Integer> blogIds = blogDao.getBlogIdList();
        for (Integer blogId : blogIds) {
            IndexVo indexVo = redisService.get(IndexVoKey.getIndexVos, "" + blogId, IndexVo.class);
            if (indexVo == null) {
                break;
            }
            indexVos.add(indexVo);
        }
        if (indexVos.isEmpty()) {

            List<Blog> blogs = getBlogList();

            for (Blog blog : blogs) {
                Type type = typeService.getTypeByTypeId(blog.getTypeId());
                User user = userService.getUserByUserId(blog.getUserId());
                IndexVo indexVo = new IndexVo();
                indexVo.setBlog(blog);
                indexVo.setType(type);
                indexVo.setUser(user);

                redisService.set(IndexVoKey.getIndexVos, "" + blog.getId(), indexVo);

                indexVos.add(indexVo);
            }
            return indexVos;
        } else return indexVos;
    }


    public Integer getTypeCountById(int id) {
        return blogDao.getTypeCountById(id);
    }


    public List<Blog> getBlogListByTypeId(int typeId) {
        return blogDao.getBlogListByTypeId(typeId);
    }

    public List<TypeDetailVo> getTypeDetailVoByTypeId(int typeId) {
        List<TypeDetailVo> typeDetailVos = new ArrayList<TypeDetailVo>();
        List<Blog> blogList = getBlogListByTypeId(typeId);
        for (Blog blog : blogList) {
            User user = userService.getUserByUserId(blog.getUserId());
            Type type = typeService.getTypeByTypeId(blog.getTypeId());
            TypeDetailVo typeDetailVo = new TypeDetailVo();
            typeDetailVo.setBlog(blog);
            typeDetailVo.setUser(user);
            typeDetailVo.setType(type);
            typeDetailVos.add(typeDetailVo);
        }
        return typeDetailVos;
    }

    public List<Blog> getThreeRecentBlog() {
        Integer maxBlogId = getMaxBlogId();
        if (maxBlogId < 3) {
            List<Blog> blogList = getBlogList();
            return blogList;
        } else {
            List<Blog> threeRecentBlog = blogDao.getThreeRecentBlog();
            return threeRecentBlog;
        }
    }

    private Integer getMaxBlogId() {
        return blogDao.getMaxBlogId();
    }

    public Blog getBlogById(Integer blogId) {
        return blogDao.getBlogById(blogId);
    }

    public List<ArchivesVo> getBlogListForEveryYear() {
        List<ArchivesVo> archivesVoList = new ArrayList<ArchivesVo>();
        List<Blog> blogs = blogDao.getBlogListForArchive();
        for (Blog blog : blogs) {
            if (StringUtils.isEmpty(blog.getMark())) {
                blog.setMark("未知");
            }
            Date updateTime = blog.getUpdateTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            String year = dateFormat.format(updateTime);
            Integer currentYear = Integer.valueOf(year);
            if (archivesVoList == null) {
                ArchivesVo archivesVo = new ArchivesVo();
                ArrayList<Blog> blogList = new ArrayList<Blog>();
                blogList.add(blog);
                archivesVo.setYear(currentYear);
                archivesVo.setBlogList(blogList);
                archivesVoList.add(archivesVo);
            } else {
                boolean sign = false;
                for (ArchivesVo archivesVo : archivesVoList) {
                    if (archivesVo.getYear() == currentYear) {
                        List<Blog> blogList = archivesVo.getBlogList();
                        blogList.add(blog);
                        sign = true;
                    }
                }
                if (!sign) {
                    ArchivesVo archivesVo = new ArchivesVo();
                    ArrayList<Blog> blogArrayList = new ArrayList<Blog>();
                    blogArrayList.add(blog);
                    archivesVo.setYear(currentYear);
                    archivesVo.setBlogList(blogArrayList);
                    archivesVoList.add(archivesVo);
                }
            }
        }

        return archivesVoList;
    }

    public Integer getBLogCount() {
        return blogDao.getBLogCount();
    }

    public List<Blog> getRecentBlog(int i) {
        List<Blog> blogList = new ArrayList<Blog>();
        Integer maxBlogId = redisService.get(MaxCount.getMaxBlogId, "max", Integer.class);
        if (maxBlogId == null) {
            maxBlogId = getMaxBlogId();
            redisService.set(MaxCount.getMaxBlogId, "max", maxBlogId);
        }

        if (maxBlogId < i) {
            for (int j = maxBlogId; j > 0; j--) {
                Blog blog = redisService.get(BlogKey.getRecentBlog, "" + j, Blog.class);
                if (blog == null) {
                    break;
                }
                blogList.add(blog);
            }
            if (blogList.isEmpty()) {
                blogList = getBlogList();
                int count = 1;
                for (Blog blog : blogList) {
                    redisService.set(BlogKey.getRecentBlog, "" + count, blog);
                    count++;
                }
                return blogList;
            } else return blogList;

        } else {
            for (int j = maxBlogId; j > maxBlogId - i; j--) {
                Blog blog = redisService.get(BlogKey.getRecentBlog, "" + j, Blog.class);
                if (blog == null) {
                    break;
                }
                blogList.add(blog);

            }
            if (blogList.isEmpty()) {
                blogList = blogDao.getRecentBlog(i);
                int count = maxBlogId;
                for (Blog blog : blogList) {
                    redisService.set(BlogKey.getRecentBlog, "" + count, blog);
                    count--;
                }
                return blogList;
            } else return blogList;

        }
    }


    public List<BlogVo> getBlogVoList() {
        List<Blog> blogs = blogDao.getBlogToRearchList();
        List<BlogVo> blogVos = new ArrayList<BlogVo>();
        for (Blog blog : blogs) {
            BlogVo blogVo = new BlogVo();
            blogVo.setId("" + blog.getId());
            blogVo.setContent(blog.getContent());
            blogVos.add(blogVo);
        }
        return blogVos;
    }

    public void toSearchList() {
        List<BlogVo> blogVos = getBlogVoList();
        for (BlogVo blogVo : blogVos) {
            searchService.updateBlogVo(blogVo);
        }
    }

    public List<IndexVo> getIndexVoListByBlogVoList(List<BlogVo> blogVos) {
        List<IndexVo> indexVos = new ArrayList<>();
        if (blogVos == null) {
            return indexVos;
        }
        for (BlogVo blogVo : blogVos) {
            int blogId = Integer.valueOf(blogVo.getId());
            IndexVo indexVo = getIndexVoByBlogId(blogId);
            indexVos.add(indexVo);
        }
        return indexVos;
    }

    private IndexVo getIndexVoByBlogId(int blogId) {

        IndexVo indexVo = redisService.get(IndexVoKey.getIndexVos, "" + blogId, IndexVo.class);
        if (indexVo != null) {
            return indexVo;
        }
        indexVo = new IndexVo();
        Blog blog = blogDao.getBlogForIndexById(blogId);
        Type type = typeService.getTypeByTypeId(blog.getTypeId());
        User user = userService.getUserByUserId(blog.getUserId());
        indexVo.setBlog(blog);
        indexVo.setType(type);
        indexVo.setUser(user);
        redisService.set(IndexVoKey.getIndexVos, "" + blog.getId(), indexVo);
        return indexVo;
    }

    public BlogDetailVo getBlogDetailVo(Integer blogId) {
        //取缓存
        BlogDetailVo detailVo = redisService.get(BlogDetailVoKey.getBlogDetailVo, "" + blogId, BlogDetailVo.class);
        if (detailVo != null) {
            return detailVo;
        }

        BlogDetailVo blogDetailVo = new BlogDetailVo();

        Blog blog = getBlogDetailById(blogId);
        String content = blog.getContent();
        String html = MarkdownUtils.markdownToHtml(content);
        blog.setContent(html);
        User user = userService.getUserByUserId(blog.getUserId());
        List<Integer> tagIdList = blogTagService.getTagIdListByBlogId(blogId);
        List<Tag> tags = new ArrayList<>();
        for (Integer id : tagIdList) {
            Tag tag = tagService.getTagById(id);
            tags.add(tag);
        }

        blogDetailVo.setBlog(blog);
        blogDetailVo.setTags(tags);
        blogDetailVo.setUser(user);

        //存入redis
        redisService.set(BlogDetailVoKey.getBlogDetailVo, "" + blogId, blogDetailVo);
        return blogDetailVo;

    }

    private Blog getBlogDetailById(Integer blogId) {
        return blogDao.getBlogDetailById(blogId);
    }


    public boolean createBlogInputVo(BlogInputVo blogInputVo, User user) {


        if (blogInputVo == null) {
            return false;
        }
        Blog blog = blogInputVo.getBlog();

        if (blog.getMark() == null || blog.getMark().length() == 0) {
            blog.setMark("原创");
        }
        //查询数据库是否存在
        boolean isExit = exit(blog.getId());

        int typeId = blogInputVo.getType().getId();
        blog.setTypeId(typeId);
        blog.setUserId(user.getId());
        Date date = new Date(System.currentTimeMillis());
        blog.setUpdateTime(date);
        blog.setCreationTime(date);
       //获取Tags
        List<Tag> tags = StringToTags(blogInputVo.getTagIds());


        //   Tag tag = blogInputVo.getTag();
        if (isExit) {
           // blogTagService.updateBlogTag(tag, blog.getId());
            blogTagService.updateBlogTags(tags, blog.getId());
            updateBlog(blog, blog.getId());
            return true;
        }
    //    blogTagService.creatBlogTag(tag, blog.getId());
        int blogCount = blogDao.createBlog(blog);
        int curId=blogDao.getMaxId();
        blogTagService.creatBlogTags(tags, curId);

        return blogCount > 0;
    }

    private List<Tag> StringToTags(String tagIds) {
        if(StringUtils.isEmpty(tagIds)){
            return null;
        }
        String[] split = tagIds.split(",");
        List<Tag> tags=new ArrayList<>();
        for(String s:split){
            Integer id = Integer.valueOf(s);
            Tag tag = new Tag();
            tag.setId(id);
            tags.add(tag);
        }
        return tags;
    }

    private void updateBlog(Blog blog, int id) {
        blogDao.update(blog.getViews(), blog.isAppreciation(), blog.isCopyRight(), blog.getContent(),
                blog.getCreationTime(), blog.getDescription(), blog.getMark(), blog.isPublished(),
                blog.isRecommend(), blog.getTitle(), blog.getFirstPicture(), blog.getUserId(),
                blog.getTypeId(), blog.getUpdateTime(), id);
    }

    private boolean exit(Integer id) {
        return blogDao.exitBlog(id) != null;
    }

    public void delete(int id) {
        blogTagService.deleteALlByblogId(id);
        blogDao.delete(id);
    }

    public Blog getBlogAllById(int id) {
        return blogDao.getBlogAllById(id);
    }
}

