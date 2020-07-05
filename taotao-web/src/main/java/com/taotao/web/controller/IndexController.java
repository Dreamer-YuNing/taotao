package com.taotao.web.controller;

import com.taotao.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ning_ on 2020/6/25.
 */
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        //查询大广告数据
        String indexAd1 = this.indexService.queryIndexAd1();
        String indexAd2 = this.indexService.queryIndexAd2();
        //将大广告数据放到Model中
        mv.addObject("indexAd1", indexAd1);
        mv.addObject("indexAd2", indexAd2);
        return mv;
    }
}
