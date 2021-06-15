package com.cqu.newblog.vo;

import com.cqu.newblog.domain.Tag;
import lombok.Data;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/30  15:36
 */
@Data
public class TagVo {
    private Tag tag;
    private int blogToTagsNumber;
}
