package com.taotao.manage.controller.api;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 前端访问后台商品Item的接口
 * Created by ning_ on 2020/7/10.
 */
@Controller
@RequestMapping("api/item")
public class ApiItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<Item> queryItemById(@PathVariable("itemId") Long itemId) {
        try {
            Item item = this.itemService.queryById(itemId);
            if (item != null) {
                //200
                return ResponseEntity.ok(item);
            }
            //404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
