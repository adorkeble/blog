package com.cqu.newblog.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/30  16:54
 */
@Repository
@Mapper
public interface  BlogTagDao {
    @Select("select count(blog_list_id) from t_blog_tag_list where tag_list_id=#{id}")
    Integer getBlogToTagCountByTagId(@Param("id") int id);
    @Select("select blog_list_id from t_blog_tag_list where tag_list_id=#{tagId}")
    List<Integer> getBLogIdsByTagId(@Param("tagId") int tagId);
    @Select("select tag_list_id from t_blog_tag_list where blog_list_id=#{id}")
    List<Integer> getTagIdListByBlogId(@Param("id") Integer id);
    @Insert("insert into t_blog_tag_list set blog_list_id=#{blogId},tag_list_id=#{id}")
    void create(@Param("blogId") int blogId,@Param("id") int id);
    @Update("update t_blog_tag_list  set tag_list_id=#{tagId}  where blog_list_id=#{id}")
    void update(@Param("tagId") int tagId, @Param("id") int id);
    @Delete("delete from t_blog_tag_list  where blog_list_id=#{id}")
    void deleteAll(@Param("id") int id);
    @Insert("insert into t_blog_tag_list (tag_list_id,blog_list_id) values (#{tagId}, #{id})")
    void insertBlogTag(@Param("tagId")int tagId, @Param("id")int id);
    @Delete("delete from t_blog_tag_list  where tag_list_id=#{id}")
    void deleteAllTagId(int id);
}
