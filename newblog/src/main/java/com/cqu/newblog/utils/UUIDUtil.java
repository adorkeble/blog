package com.cqu.newblog.utils;

import java.util.UUID;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/8  15:47
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
