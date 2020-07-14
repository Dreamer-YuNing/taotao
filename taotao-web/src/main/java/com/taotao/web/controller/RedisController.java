package com.taotao.web.controller;

import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ning_ on 2020/7/13.
 */
@Controller
@RequestMapping("cache/item/")
public class RedisController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ItemService itemService;
    @RequestMapping(value = "{itemId}",method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCache(@PathVariable("itemId")Long itemId){
        try {
            //删除redis中的缓存
            this.redisService.del(itemService.REDIS_ITEM_KEY+itemId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
