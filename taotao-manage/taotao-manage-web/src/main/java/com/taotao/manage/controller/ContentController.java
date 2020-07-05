package com.taotao.manage.controller;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ning_ on 2020/6/28.
 */
@Controller
@RequestMapping("content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 新增内容
     *
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content) {
        try {
            this.contentService.save(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 编辑更新内容
     *
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> editContent(Content content) {
        try {
            this.contentService.updateByPrimaryKey(content);
            //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
     * 根据categoryId分页查询content
     *
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryContentByCategoryId(@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
                                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "rows", defaultValue = "0") Integer rows) {
        try {
            Content record = new Content();
            record.setCategoryId(categoryId);
            PageInfo<Content> pageInfo = this.contentService.queryPageListByWhere(page, rows, record);
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            //200
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
