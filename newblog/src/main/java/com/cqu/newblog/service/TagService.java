package com.cqu.newblog.service;

import com.cqu.newblog.dao.TagDao;
import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.redis.RedisService;
import com.cqu.newblog.redis.TagVoKey;
import com.cqu.newblog.vo.TagDetailVo;
import com.cqu.newblog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  19:50
 */
@Service
public class TagService {
    @Autowired
    TagDao tagDao;
    @Autowired
    BlogService blogService;
    @Autowired
    BlogTagService blogTagService;
    @Autowired
    TypeService typeService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    RedisService redisService;


    public List<TagVo> getTagVoList() {
        List<TagVo> tagVos = new ArrayList<TagVo>();
        List<Integer> tagIds = getTagIdList();

        for (Integer tagId : tagIds) {
            TagVo tagVo = redisService.get(TagVoKey.getTagVoList, "" + tagId, TagVo.class);
            if (tagVo == null) {
                break;
            }
            tagVos.add(tagVo);
        }
        if (tagVos .isEmpty()) {
            List<Tag> tagList = tagDao.getTagList();

            for (Tag tag : tagList) {
                Integer num = blogTagService.getBlogToTagCountByTagId(tag.getId());
                if (num == null) {
                    num = 0;
                }
                TagVo tagVo = new TagVo();
                tagVo.setBlogToTagsNumber(num);
                tagVo.setTag(tag);
                redisService.set(TagVoKey.getTagVoList, "" + tag.getId(), tagVo);
                tagVos.add(tagVo);
            }
            return tagVos;
        } else return tagVos;
    }

    private List<Integer> getTagIdList() {
        return tagDao.getTagIdList();
    }

    public List<Tag> getTagList() {
        return tagDao.getTagList();
    }

    public List<TagDetailVo> getTagDetailVoListByTagId(int tagId) {
        List<TagDetailVo> tagDetailVos = new ArrayList<TagDetailVo>();
        List<Blog> bloglist = blogTagService.getBlogListByTagId(tagId);
        for (Blog blog : bloglist) {
            TagDetailVo tagDetailVo = new TagDetailVo();
            Type type = typeService.getTypeByTypeId(blog.getTypeId());
            User user = userService.getUserByUserId(blog.getUserId());
            List<Integer> tagIdList = blogTagService.getTagIdListByBlogId(blog.getId());
            List<Tag> tags = new ArrayList<Tag>();
            for (Integer tagid : tagIdList) {
                Tag tag = tagService.getTagByTagId(tagid);
                tags.add(tag);
            }
            tagDetailVo.setTags(tags);
            tagDetailVo.setBlog(blog);
            tagDetailVo.setType(type);
            tagDetailVo.setUser(user);
            tagDetailVos.add(tagDetailVo);
        }
        return tagDetailVos;
    }

    private Tag getTagByTagId(Integer tagid) {
        return tagDao.getTagByTagId(tagid);
    }

    public Tag getTagById(Integer id) {
        return tagDao.getTagByTagId(id);
    }

    public boolean createTag(Tag tag) {
      String getName=  tagDao.getTagByName(tag.getName());
      if(getName==null){
          tagDao.create(tag.getName());
          return true;
      }
      return false;
    }

    public void delete(int id) {
        //先删除blog-tag关系表
        blogTagService.deleteALlByTagId(id);
        //删除tag
        tagDao.delete(id);
    }

    public void updateTag(Tag tag, int id) {
        tagDao.updateTag(id,tag.getName());
    }
}
