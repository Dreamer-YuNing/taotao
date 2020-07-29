package com.taotao.web.controller;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.service.ItemService;
import com.taotao.web.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ning_ on 2020/7/10.
 */
@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据id查询商品
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ModelAndView queryItemById(@PathVariable("itemId") Long itemId) {
        ItemVO itemVO = this.itemService.queryItemVOById(itemId);
        //未查询到404
        if (itemVO == null) {
            ModelAndView mv = new ModelAndView("my-exception");
            mv.addObject("error","404 商品不存在");
            return mv;
        }
        ItemDesc itemDesc = this.itemService.queryItemDescById(itemId);
        ModelAndView mv = new ModelAndView("item");
        mv.addObject("item", itemVO);
        mv.addObject("itemDesc", itemDesc);
        return mv;
    }
}
