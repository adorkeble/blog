package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/28  16:31
 */
public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();

}
