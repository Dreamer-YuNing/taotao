package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ning_ on 2020/7/14.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }
}
