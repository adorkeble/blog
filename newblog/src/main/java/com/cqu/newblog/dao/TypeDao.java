package com.cqu.newblog.dao;

import com.cqu.newblog.domain.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  19:56
 */
@Repository
@Mapper
public interface TypeDao {
    @Select("select name,id from t_type")
    List<Type> getTypeList();
    @Select("select * from t_type where id=#{id} ")
    Type getTypeByTypeId(@Param("id") int id);
    @Select("select id from t_type")
    List<Integer> getTypeIdList();
    @Insert("insert into t_type set name=#{name}")
    int createByName(@Param("name") String name);
     @Select("select name from t_type where name=#{name}")
    String  getTypeByName(@Param("name") String name);
    @Update("update t_type set name=#{name} where id=#{id}")
    void update(@Param("id") int id, @Param("name") String name);
    @Delete("delete from t_type where id=#{id}")
    void delete(@Param("id") int id);
}
