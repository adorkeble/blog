package com.cqu.newblog.dao;

import com.cqu.newblog.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  20:08
 */
@Repository
@Mapper
public interface UserDao {
    @Select("select id,avatar,nickname,username,mailbox from t_user where id=#{userId}")
    User getUserByUserId(@Param("userId") int userId);

    @Select("select password from t_user where username=#{username}")
    String getPasswordByUsername(@Param("username") String username);

    @Select("select * from t_user  where username=#{username}")
    User getUserByusername(@Param("username") String username);

    @Insert("insert into t_user  set mailbox=#{mailbox},nickname=#{nickname}," +
            "password=#{password},avatar=#{avatar},username=#{username},headportrait=#{headportrait}")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int createUser(User user);
}
