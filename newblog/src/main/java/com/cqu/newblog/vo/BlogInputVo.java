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
 * @date 2021/6/10  11:20
 */
@Data
public class BlogInputVo {
    private Blog blog;
    private Type type;
    private Tag tag;
    private List<Tag> tags;
    private String tagIds;
}
