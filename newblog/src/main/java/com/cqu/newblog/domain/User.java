package com.cqu.newblog.domain;

import lombok.Data;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  13:45
 */
@Data
public class User {
   private int id;
   private String headportrait;
   private String mailbox;
   private String nickname;
   private String password;
   private String username;
   private String avatar;
}
