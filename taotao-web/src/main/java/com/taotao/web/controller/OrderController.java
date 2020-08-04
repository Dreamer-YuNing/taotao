package com.taotao.web.controller;

import com.taotao.common.bean.TaotaoResult;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadLocal.UserThreadLocal;
import com.taotao.web.vo.ItemVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.taotao.web.pojo.Order;
import com.taotao.web.pojo.User;


/**
 * Created by ning_ on 2020/7/19.
 */
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    /**
     * 跳转到订单确认页面 填充信息
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

    /**
     * 提交订单,返回成功或者失败页面
     * @param order
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public TaotaoResult submit(Order order){
        User user = UserThreadLocal.get();
        //order设置用户id和昵称
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        return this.orderService.sumbit(order);
    }

    /**
     * 下单成功返回的页面
     * @param orderId
     * @return
     */
    @RequestMapping(value = "success",method = RequestMethod.GET)
    public ModelAndView success(@RequestParam(value = "id")String orderId){
        Order order = this.orderService.queryOrderById(orderId);
        ModelAndView mv = new ModelAndView("success");
        mv.addObject("order",order);
        mv.addObject("date",new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }


}
