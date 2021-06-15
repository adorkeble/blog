package com.cqu.newblog.vo;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.User;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/5  13:50
 */
@Data
public class BlogDetailVo {
    private Blog blog;
    private User user;
    private List<Tag> tags;

}
