package com.taotao.manage.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * taotao-web项目主页的商品类目controller
 * Created by ning_ on 2020/6/26.
 */
@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 查询所有itemCat,并且分层封装成json格式的tree结构返回
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCat() {
        try {
            ItemCatResult itemCatResult = this.itemCatService.queryItemCatAllToTree();
            if (itemCatResult == null) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(itemCatResult);

        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
}
