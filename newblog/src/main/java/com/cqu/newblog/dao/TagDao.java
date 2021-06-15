package com.cqu.newblog.dao;

import com.cqu.newblog.domain.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  19:51
 */
@Repository
@Mapper
public interface TagDao {
    @Select("select name,id from t_tag")
    List<Tag> getTagList();

    @Select("select name,id from t_tag where id=#{id}")
    Tag getTagByTagId(@Param("id") Integer tagid);

    @Select("select id from t_tag")
    List<Integer> getTagIdList();

    @Select("select name from t_tag where name=#{name}")
    String getTagByName(@Param("name") String name);

    @Insert("insert into t_tag set name=#{name}")
    void create(@Param("name") String name);

    @Delete("delete from t_tag where id=#{id}")
    void delete(@Param("id") int id);
    @Update("update t_tag set name=#{name} where id=#{id}")
    void updateTag(@Param("id") int id,@Param("name") String name);
}
