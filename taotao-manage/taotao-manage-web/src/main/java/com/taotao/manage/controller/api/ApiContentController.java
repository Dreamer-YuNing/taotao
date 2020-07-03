package com.taotao.manage.controller.api;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * manage工程的对外应用接口
 * Created by ning_ on 2020/7/2.
 */
@Controller
@RequestMapping("api/content")
public class ApiContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 根据categoryId查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Content>> queryContentByCategoryId(@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
                                                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                  @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            Content record = new Content();
            record.setCategoryId(categoryId);
            PageInfo<Content> pageInfo = this.contentService.queryPageListByWhere(page, rows, record);
            if (pageInfo != null && pageInfo.getSize() != 0) {
                //200
                return ResponseEntity.ok(pageInfo.getList());
            }
            //204
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
