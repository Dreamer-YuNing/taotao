package com.taotao.manage.controller;


import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ning_ on 2020/6/20.
 */

@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 新增item
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc) {
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                //400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            this.itemService.saveItem(item, desc);
            //201
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 按item先后顺序,分页查询item
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "rows", defaultValue = "30") Integer pageSize) {
        Item record = new Item();
        record.setStatus(1);
        try {
            PageInfo<Item> pageInfo = this.itemService.queryPageListByWhere("updated desc", pageNum, pageSize, record);
            if(pageInfo==null||pageInfo.getSize()==0){
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            EasyUIResult result = new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
            //200
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc) {
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                //400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            this.itemService.updateItem(item, desc);
            //修改成功,204
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
