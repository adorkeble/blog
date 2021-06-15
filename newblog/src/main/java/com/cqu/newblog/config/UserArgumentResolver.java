package com.cqu.newblog.config;

import com.alibaba.druid.util.StringUtils;
import com.cqu.newblog.domain.User;
import com.cqu.newblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/8  17:05
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(UserService.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserService.COOKI_NAME_TOKEN);

        String token= StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        User user=userService.getByToken(response,token);
        return  user;
    }

    private String getCookieValue(HttpServletRequest request, String cookiNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookiNameToken.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;

    }
}
