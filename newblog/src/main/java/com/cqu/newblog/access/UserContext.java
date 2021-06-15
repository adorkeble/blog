package com.cqu.newblog.access;

import com.cqu.newblog.domain.User;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/8  21:34
 */
public class UserContext {
    private static ThreadLocal<User> userHolder=new ThreadLocal<>();
    public static void setUser(User user){
        userHolder.set(user);
    }
    public static User getUser(){
        return userHolder.get();
    }
}
