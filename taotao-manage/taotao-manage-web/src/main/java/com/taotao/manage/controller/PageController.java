package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ning_ on 2020/6/11.
 * 通用页面跳转
 */
@Controller
@RequestMapping("page")
public class PageController {
    @RequestMapping(value="{pageName}",method= RequestMethod.GET)
    public String toPage(@PathVariable("pageName")String pageName){
        return pageName;
    }
}