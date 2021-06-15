package com.cqu.newblog.vo;

import com.cqu.newblog.domain.Blog;
import com.cqu.newblog.domain.Type;
import com.cqu.newblog.domain.User;
import lombok.Data;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/30  20:44
 */
@Data
public class TypeDetailVo {
    private Blog blog;
    private User user;
    private Type type;
}
