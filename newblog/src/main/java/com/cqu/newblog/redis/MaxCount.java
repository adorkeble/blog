package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  21:58
 */
public class MaxCount extends BaseKey {
    public MaxCount(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static MaxCount getMaxBlogId=new MaxCount(120,"MaxBlogId");
    public static MaxCount getTagNum=new MaxCount(120,"tagNum");


}
