package com.cqu.newblog.dao;

import com.cqu.newblog.domain.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  14:21
 */
@Repository
@Mapper
public interface BlogDao {

    @Select("select id,description,update_time,title,views,first_picture,user_id,type_id,published from t_blog order by id desc")
    List<Blog> getBlogList();

    @Select("select count(type_id) from t_blog where type_id=#{id}")
    Integer getTypeCountById(@Param("id") int id);

    @Select("select id,description,update_time,title,views,first_picture,user_id,type_id from t_blog where type_id=#{typeId}")
    List<Blog> getBlogListByTypeId(@Param("typeId") int typeId);

    @Select("select max(id) from t_blog")
    Integer getMaxBlogId();

    @Select("select id,description,update_time,title,views,first_picture,user_id,type_id from t_blog order by id desc limit  3")
    List<Blog> getThreeRecentBlog();

    @Select("select id,description,update_time,title,views,first_picture,user_id,type_id from t_blog where id=#{id}")
    Blog getBlogById(@Param("id") Integer blogId);

    @Select("select id,update_time,title,mark from t_blog")
    List<Blog> getBlogListForArchive();

    @Select("select count(id) from t_blog")
    Integer getBLogCount();

    @Select("select id,description,update_time,title,views,first_picture,user_id,type_id from t_blog order by id desc limit  #{i}")
    List<Blog> getRecentBlog(@Param("i") int i);

    @Select("select id from t_blog order by id desc")
    List<Integer> getBlogIdList();

    @Select("select id,content from t_blog")
    List<Blog> getBlogToRearchList();

    @Select("select id,description,update_time,title,views,first_picture,user_id,type_id from t_blog where id=#{id}")
    Blog getBlogForIndexById(@Param("id") int blogId);

    @Select("select id,mark,content,update_time,title,views,first_picture,user_id,type_id,copy_right from t_blog where id=#{id}")
    Blog getBlogDetailById(@Param("id") Integer blogId);

    @Insert("insert into t_blog  set appreciation=#{appreciation},copy_right=#{copyRight},content=#{content}," +
            "creation_time=#{creationTime},description=#{description},mark=#{mark},published=#{published}," +
            "recommend=#{recommend},title=#{title},views=#{views},first_picture=#{firstPicture}," +
            "user_id=#{userId},type_id=#{typeId},update_time=#{updateTime}")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int createBlog(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    void delete(@Param("id") int id);

    @Select("select * from t_blog where id=#{id}")
    Blog getBlogAllById(@Param("id") int id);

    @Select("select id from t_blog where id=#{id}")
    Integer exitBlog(@Param("id") int id);

    @Update("update t_blog  set appreciation=#{appreciation},copy_right=#{copyRight},content=#{content}," +
            "creation_time=#{creationTime},description=#{description},mark=#{mark},published=#{published}," +
            " recommend=#{recommend},title=#{title},views=#{views},first_picture=#{firstPicture}," +
            " user_id=#{userId},type_id=#{typeId},update_time=#{updateTime} where id=#{id}")
    void update(@Param("views")int views, @Param("appreciation") boolean appreciation, @Param("copyRight") boolean copyRight,
                @Param("content") String content, @Param("creationTime") Date creationTime, @Param("description") String description,
                @Param("mark") String mark, @Param("published") boolean published, @Param("recommend") boolean recommend,
                @Param("title") String title, @Param("firstPicture") String firstPicture, @Param("userId") int userId,
                @Param("typeId") int typeId, @Param("updateTime") Date updateTime, @Param("id") int id);
    @Select("select max(id) from t_blog")
    int getMaxId();
}
