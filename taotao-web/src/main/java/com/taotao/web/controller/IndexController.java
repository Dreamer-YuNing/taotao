package com.taotao.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ning_ on 2020/6/25.
 */
@Controller
public class IndexController {
    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
