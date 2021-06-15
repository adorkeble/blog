package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  21:06
 */
public class BaseKey implements KeyPrefix {
    private int expireSeconds;
    private String getPrefix;

    public BaseKey(int expireSeconds, String getPrefix) {
        this.expireSeconds = expireSeconds;
        this.getPrefix = getPrefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String name = getClass().getSimpleName();
        return name+":"+getPrefix;
    }
}
