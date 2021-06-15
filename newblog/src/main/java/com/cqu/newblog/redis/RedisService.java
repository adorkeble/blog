package com.cqu.newblog.redis;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/31  20:41
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    //set
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            String str = beanToString(value);
            if (str.length() <= 0 || str == null) {
                return false;
            }
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    //get
    public <T> T get(KeyPrefix prefix, String key, Class<T> tClass) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String value = jedis.get(realKey);
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            T t = stringToBean(value,tClass);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }










    private <T> T stringToBean(String value, Class<T> tClass) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        if(tClass==int.class||tClass==Integer.class){
            return (T)Integer.valueOf(value);
        }else if(tClass==long.class||tClass==Long.class){
            return(T) Long.valueOf(value);
        }else if(tClass==String.class){
            return (T)value;
        }else return (T) JSON.toJavaObject( JSON.parseObject(value),tClass);
    }

    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> valueClass = value.getClass();
        if (valueClass == int.class || valueClass == Integer.class ||
                valueClass == Long.class || valueClass == long.class) {
            return "" + value;
        } else if (valueClass == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
