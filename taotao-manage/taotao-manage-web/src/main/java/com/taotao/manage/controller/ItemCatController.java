package com.taotao.manage.controller;

import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ning_ on 2020/6/17.
 */
@Controller
@RequestMapping("item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 根据父节点查询类目列表
     *
     * @param parentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCat(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        List<ItemCat> itemCats = this.itemCatService.queryListByWhere(itemCat);
        try {
            if (itemCats == null || itemCats.isEmpty()) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemCats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据id查询ItemCat
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseEntity<ItemCat> queryItemById(@PathVariable("id")Long id){
        try {
            ItemCat itemCat = this.itemCatService.queryById(id);
            if (itemCat == null) {
                //返回404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //返回200
            return ResponseEntity.ok(itemCat);
        } catch (Exception e) {
            e.printStackTrace();
            //返回500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
