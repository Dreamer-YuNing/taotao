package com.taotao.sso.controller;

import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ning_ on 2020/7/14.
 */
@Controller
@RequestMapping("user")
public class UserController {

    //注入UserService
    @Autowired
    private UserService userService;

    //注册页面跳转
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
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
            //删除redis中的缓存
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
}
