package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  22:03
 */
public class BlogKey extends BaseKey{
    public BlogKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static BlogKey getRecentBlog=new BlogKey(60,"recentBlog");
}
