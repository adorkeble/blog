package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/5  14:07
 */
public class BlogDetailVoKey extends BaseKey {
    public BlogDetailVoKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static BlogDetailVoKey getBlogDetailVo=new BlogDetailVoKey(3600,"getBlogDetailVo");
}
