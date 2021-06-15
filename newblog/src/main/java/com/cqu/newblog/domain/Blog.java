package com.cqu.newblog.domain;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  13:37
 */
@Data
public class Blog {
    private int id;
    private boolean appreciation;
    private boolean commentsOpen;
    private String content;//内容
    private boolean copyRight;
    private Date creationTime;
    private String description;
    private String firstPicture;
    private String mark;
    private boolean published;
    private boolean recommend;
    private String title;
    private Date updateTime;
    private int views;
    private  int typeId;
    private int userId;

}
