package com.cqu.newblog.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/8  15:12
 */

public class Utils {
    public final static String salt="1a2b3c";
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    public static String getDBPassword(String password,String salt){
        String str=""+salt.charAt(0)+salt.charAt(1)+password+salt.charAt(4)+salt.charAt(3);;
        return md5(str);
    }
}
