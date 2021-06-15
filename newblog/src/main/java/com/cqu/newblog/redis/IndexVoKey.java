package com.cqu.newblog.redis;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  21:02
 */

public class IndexVoKey extends BaseKey {

    public IndexVoKey(int expireSeconds, String getPrefix) {
        super(expireSeconds, getPrefix);
    }

    public static IndexVoKey getIndexVos=new IndexVoKey(60,"gIV");
}
