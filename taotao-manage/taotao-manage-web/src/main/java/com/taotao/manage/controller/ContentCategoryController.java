package com.taotao.manage.controller;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 内容分类 类
 * Created by ning_ on 2020/6/28.
 */
@Controller
@RequestMapping("content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 根据父id查询所有的内容分类
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategory(@RequestParam(value = "id", defaultValue = "0") Long id) {
        ContentCategory record = new ContentCategory();
        record.setParentId(id);
        try {
            List<ContentCategory> contentCategories = this.contentCategoryService.queryListByWhere(record);
            if (contentCategories == null || CollectionUtils.isEmpty(contentCategories)) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(contentCategories);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     * 新增新节点
     *
     * @param record
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> insertContentCategory(ContentCategory record) {
        record.setId(null);
        record.setIsParent(false);
        record.setStatus(1);
        record.setSortOrder(1);
        try {
            this.contentCategoryService.saveChildrenAndUpdateParent(record);
            //201
            return ResponseEntity.status(HttpStatus.CREATED).body(record);
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 节点重命名
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(@RequestParam("id") Long id, @RequestParam("name") String name) {
        try {
            ContentCategory record = new ContentCategory();
            record.setId(id);
            record.setName(name);
            this.contentCategoryService.updateByPrimaryKey(record);
            //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 删除节点(及其所有子节点,并且判断父节点是否还为父节点改变其isPareny值)
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategory(@RequestParam("parentId")Long parentId,@RequestParam("id")Long id){
        try {
            this.contentCategoryService.deleteContentCategory(parentId,id);
            //204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
