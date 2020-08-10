package com.taotao.cart.service;

import com.github.abel533.entity.Example;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ning_ on 2020/8/7.
 */
@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId, Long userId) {
        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(userId);
        Cart cart = this.cartMapper.selectOne(record);
        if (null == cart) {
            //该用户不存在该商品,直接新增一个
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItemId(itemId);
            cart.setNum(1);
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());

            //查询商品数据,写入cart
            Item item = this.itemService.queryItemById(itemId);
            cart.setItemImage(item.getImage());
            cart.setItemTitle(item.getTitle());
            cart.setItemPrice(item.getPrice());
            this.cartMapper.insert(cart);
        }else{
            //购物车中已经存在该商品,数量+1
            cart.setNum(cart.getNum()+1);
            cart.setUpdated(new Date());
            //更新
            this.cartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    public List<Cart> queryCartList(Long userId) {
        //准备查询条件
        Example example = new Example(Cart.class);
        //查询条件
        example.createCriteria().andEqualTo("userId",userId);
        //创建时间升序
        example.setOrderByClause("created DESC");
        //返回List<Cart>
        return this.cartMapper.selectByExample(example);
    }
}
