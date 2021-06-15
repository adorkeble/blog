package com.cqu.newblog.dao;

import com.cqu.newblog.vo.BlogVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/4  19:45
 */
@Repository
@Mapper
public interface SearchDao {
    @Insert("insert into t_search (id,content) values (#{id},#{content})")
    void updateBlogVo(@Param("id") String id, @Param("content") String content);
}
