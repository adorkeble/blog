package com.cqu.newblog.service;

import com.cqu.newblog.dao.UserDao;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.redis.RedisService;
import com.cqu.newblog.redis.UserKey;
import com.cqu.newblog.utils.UUIDUtil;
import com.cqu.newblog.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/5/29  20:03
 */
@Service
public class UserService {
    public static final String COOKI_NAME_TOKEN="token";
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;

    public User getUserByUserId(int userId) {
        User user = redisService.get(UserKey.getUser, "" + userId, User.class);
        if (user == null) {
            user = userDao.getUserByUserId(userId);
            redisService.set(UserKey.getUser, "" + userId, user);
        }
        return user;

    }

    public boolean login(HttpServletResponse httpServletResponse, String username, String password) {

        User user = userDao.getUserByusername(username);
        if(user==null){
            return false;
        }
        String realPassword = user.getPassword();
        String curPassword = Utils.getDBPassword(password, Utils.salt);
        if (!curPassword.equals(realPassword)) {
            return false;
        }
        String tooken = UUIDUtil.uuid();
        addCookie(user,tooken,httpServletResponse);
        return true;
    }

    private void addCookie(User user, String tooken, HttpServletResponse httpServletResponse) {
        redisService.set(UserKey.tooken,tooken,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, tooken);

        cookie.setMaxAge(UserKey.tooken.expireSeconds());
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

    }

    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(UserKey.tooken, token, User.class);
        if(user!=null){
            addCookie(user,token,response);
        }
        return user;
    }

    public boolean createUser(User user) {
        String avatar = user.getAvatar();
        user.setHeadportrait(avatar);
        int returnLine = userDao.createUser(user);
        return returnLine>0;
    }
}
