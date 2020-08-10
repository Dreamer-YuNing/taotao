package com.taotao.cart.controller;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.User;
import com.taotao.common.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning_ on 2020/8/7.
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 加入购物车功能
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId") Long itemId) {
        //获取登陆用户
        User user = UserThreadLocal.get();
        if (user == null) {
            //未登录

        } else {
            //已登陆
            this.cartService.addItemToCart(itemId, user.getId());
        }
        //重定向到查询购物车列表的controller(加入购物车后直接跳转到购物车)
        return "redirect:/cart/list.html";
    }

    /**
     * 根绝userId返回购物车列表
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView queryCartList() {
        ModelAndView mv = new ModelAndView("cart");
        List<Cart> cartList = new ArrayList<Cart>();
        //获取用户登陆
        User user = UserThreadLocal.get();
        if (user == null) {
            //未登录
        } else {
            //登陆
            cartList = this.cartService.queryCartList(user.getId());
        }
        mv.addObject("cartList",cartList);
        return mv;
    }
}
