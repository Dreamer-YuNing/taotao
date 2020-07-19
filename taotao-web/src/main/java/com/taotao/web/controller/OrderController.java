package com.taotao.web.controller;

import com.taotao.web.service.ItemService;
import com.taotao.web.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ning_ on 2020/7/19.
 */
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private ItemService itemService;

    /**
     * 跳转订单 填充信息
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId")Long itemId){
        ItemVO itemVO = this.itemService.queryItemVOById(itemId);
        ModelAndView mv = new ModelAndView("order");
        mv.addObject("item",itemVO);
        return mv;
    }
}
