package com.taotao.cart.interceptor;

import com.taotao.cart.service.UserService;
import com.taotao.common.pojo.User;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
        //cookie中没有token,返回true
        if (StringUtils.isEmpty(token)) {
            return true;
        }
        //cookie中有token
        User user =  this.userService.queryUserByToken(token);
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
