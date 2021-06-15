package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/1  10:43
 */
public class UserKey extends BaseKey {
    public UserKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static UserKey getUser=new UserKey(600,"user");
    public static UserKey tooken=new UserKey(3600,"tk");
}
