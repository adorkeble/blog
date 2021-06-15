package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/1  10:08
 */
public class NameKey extends BaseKey {
    public NameKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static NameKey testName=new NameKey(30,"name");
}
