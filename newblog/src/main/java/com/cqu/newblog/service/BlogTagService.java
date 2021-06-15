package com.cqu.newblog.service;

import com.cqu.newblog.dao.BlogTagDao;
import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/30  16:02
 */
@Service
public class BlogTagService {
    @Autowired
    BlogTagDao blogTagDao;
    @Autowired
    BlogService blogService;

    public Integer getBlogToTagCountByTagId(int id) {
        return blogTagDao.getBlogToTagCountByTagId(id);
    }

    public List<Blog> getBlogListByTagId(int tagId) {
        List<Integer> blogIds = blogTagDao.getBLogIdsByTagId(tagId);
        List<Blog> blogList = new ArrayList<Blog>();
        for (Integer blogId : blogIds) {
            Blog blog = blogService.getBlogById(blogId);
            blogList.add(blog);
        }
        return blogList;
    }

    public List<Integer> getTagIdListByBlogId(Integer id) {
        return blogTagDao.getTagIdListByBlogId(id);
    }

    public void creatBlogTag(Tag tag,int blogId) {
        if(tag==null||blogId<=0){
            return;
        }

            blogTagDao.create(blogId,tag.getId());


    }

    public void updateBlogTag(Tag tag, int id) {
        blogTagDao.update(tag.getId(),id);
    }

    public void updateBlogTags(List<Tag> tags, int id) {
        //删除blogid对应所有数据
        blogTagDao.deleteAll(id);
        //插入数据
        for(Tag tag:tags){
            insertBlogTag(tag.getId(),id);
        }
    }

    private void insertBlogTag(int tagId, int id) {
        blogTagDao.insertBlogTag(tagId,id);
    }

    public void creatBlogTags(List<Tag> tags, int id) {
        for(Tag tag:tags){
            creatBlogTag(tag,id);
        }
    }

    public void deleteALlByblogId(int id) {
        blogTagDao.deleteAll(id);
    }

    public void deleteALlByTagId(int id) {
        blogTagDao.deleteAllTagId(id);
    }
}
