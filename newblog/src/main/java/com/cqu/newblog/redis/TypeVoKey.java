package com.cqu.newblog.redis;



/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  21:27
 */

public class TypeVoKey extends BaseKey {

    public TypeVoKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }
    public static TypeVoKey getTypeVoList=new TypeVoKey(60,"gTVL");
}
