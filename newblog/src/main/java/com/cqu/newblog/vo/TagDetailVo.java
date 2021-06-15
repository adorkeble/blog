package com.cqu.newblog.vo;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Tag;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  13:12
 */
@Data
public class TagDetailVo extends TypeDetailVo {
    private Blog blog;
    private User user;
    private List<Tag> tags;
    private Type type;

}
