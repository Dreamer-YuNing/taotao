package com.taotao.web.controller.interceptor;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.service.UserService;
import com.taotao.web.threadLocal.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ning_ on 2020/7/19.
 */
public class LoginInterceptor implements HandlerInterceptor {

    public static final String COOKIE_NAME = "TT_TOKEN";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        if (token == null) {
            //response中没有cookie,重定向到登录界面
            response.sendRedirect(userService.SSO_TAOTAO_BASE_URL + "/user/login.html");
            return false;
        }
        User user = this.userService.queryUserByToken(token);
        if (user==null){
            response.sendRedirect(userService.SSO_TAOTAO_BASE_URL + "/user/login.html");
            return false;
        }
        //将已经登陆的用户信息保存在当前线程,后面需要user信息可以直接取,不需要再次查询
        UserThreadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //渲染之后,移除ThreadLocal中的User信息,因为Tomcat是线程池技术,一个线程会多次使用,所以必须清除
        UserThreadLocal.remove();
    }
}
