package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  21:39
 */
public class TagVoKey  extends BaseKey{

    public TagVoKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static TagVoKey getTagVoList=new TagVoKey(60,"TagVoList");
}
