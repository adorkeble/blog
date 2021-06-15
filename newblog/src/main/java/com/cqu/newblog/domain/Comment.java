package com.cqu.newblog.domain;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  13:41
 */
@Data
public class Comment {
  private int id;
  private String avatar;
  private String content;
  private  Date creationTime;
  private String email;
  private String nickname;
  private int blogId;
  private int parentCommentId;
}
