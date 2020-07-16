package com.taotao.sso.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.TaotaoResult;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning_ on 2020/7/14.
 */
@Controller
@RequestMapping("user")
public class UserController {

    //注入UserService
    @Autowired
    private UserService userService;

    private static final String COOKIE_NAME="TT_TOKEN";
    //注册页面跳转
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }

    //登录页面跳转
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }
    /**
     * 检验数据是否有效(可用性)
     *
     * @param param 数据
     * @param type  数据类型
     * @return true 数据合格 false 数据不合格
     */
    @RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkData(@PathVariable("param") String param, @PathVariable("type") Integer type) {
        try {
            Boolean boo = this.userService.checkData(param, type);
            if (boo == null) {
                //没有返回值,参数错误,返回400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(boo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 注册
     * @Valid 注解是开启对后面的对象的数据校验,如有异常字段,则存储到BindingResult中
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    public ResponseEntity<TaotaoResult> doRegister(@Valid User user, BindingResult result) {
        //校验数据(数据的约束为javabean的相关注解)
        if (result.hasErrors()){
            //准备收集错误信息
            ArrayList<Object> msgs = new ArrayList<>();
            //获取错误信息
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError error:allErrors) {
                msgs.add(error.getDefaultMessage());
            }
            //参数有问题,返回400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaotaoResult.build(400,StringUtils.join(msgs,"|")));
        }
        try {
            Boolean boo = this.userService.register(user);
            if (boo) {
                return ResponseEntity.ok(TaotaoResult.ok());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaotaoResult.build(400, "请核对信息后重试"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TaotaoResult.build(500, "服务器故障"));
        }

    }

    /**
     * 登录
     * @param username
     * @param password
     * @param httpRequest
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public ResponseEntity<TaotaoResult> doLogin(@RequestParam("username") String username,
                                                @RequestParam("password") String password,
                                                HttpServletRequest httpRequest,
                                                HttpServletResponse httpResponse) {
        try {
            //尝试登陆,并获取token值
            String token = this.userService.login(username,password);
            if (StringUtils.isNotEmpty(token)) {
                //token不为空,代表登录成功,并将token放入
                CookieUtils.setCookie(httpRequest,httpResponse,COOKIE_NAME,token);
                //返回http状态码200,业务状态码也为200
                return ResponseEntity.ok(TaotaoResult.ok());
            }
            //如果为空,代表账号或者密码错误,返回业务状态码400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TaotaoResult.build(400,"用户名或密码错误"));
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常,不能返回,需要处理异常.并且记录数据
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TaotaoResult.build(500,"服务器错误"));
        }
    }
}
