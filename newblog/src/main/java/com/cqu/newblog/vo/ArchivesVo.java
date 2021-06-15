package com.cqu.newblog.vo;

import com.cqu.newblog.domain.Blog;
import lombok.Data;

import java.util.List;


/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  18:27
 */
@Data
public class ArchivesVo {
    private List<Blog> blogList;
    private int year;
}
