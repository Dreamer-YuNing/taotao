package com.taotao.manage.controller.api;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ning_ on 2020/7/10.
 */
@Controller
@RequestMapping("api/item/desc")
public class ApiItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescById(@PathVariable("itemId")Long itemId){
        try {
            ItemDesc itemDesc = this.itemDescService.queryById(itemId);
            if (itemDesc == null) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}