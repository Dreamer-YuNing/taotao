package com.taotao.manage.service;

import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning_ on 2020/6/28.
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {
    @Autowired
    private ContentCategoryMapper mapper;

    @Autowired
    private ContentService contentService;
    /**
     * 添加新的节点,并更新父节点的状态
     *
     * @param record
     */
    public void saveChildrenAndUpdateParent(ContentCategory record) {
        //保存新的内容分类节点
        super.save(record);
        //更新父节点的状态,更新isParent属性
        ContentCategory parent = super.queryById(record.getParentId());
        if (parent != null && !parent.getIsParent()) {
            parent.setIsParent(true);
            super.updateByPrimaryKey(parent);
        }
    }

    /**
     * 删除节点(及其所有子节点,并且判断父节点是否还为父节点),doto:删除所有节点对应的Content
     *
     * @param parentId
     * @param id
     */
    public void deleteContentCategory(Long parentId, Long id) {
        ContentCategory record = new ContentCategory();
        record.setParentId(parentId);
        List<ContentCategory> contentCategoryList = super.queryListByWhere(record);
        //删除当前节点,及其所有子节点(递归)
        deleteSubs(id);
        //删除对应的所有的内容(Content)
        List ids = new ArrayList<Long>();
        getSubIds(ids,id);
        contentService.deleteByIds(Content.class,"categoryId",ids);
        //当前节点只有一个兄弟节点
        if (contentCategoryList.size() == 1) {
            //改变父节点状态
            ContentCategory fatherRecord = new ContentCategory();
            fatherRecord.setId(parentId);
            fatherRecord.setIsParent(false);
            super.updateByPrimaryKey(fatherRecord);
        }
    }

    /**
     * 根据id删除当前节点和所有的子节点
     *
     * @param id
     */
    private void deleteSubs(Long id) {
        //删除当前节点
        super.deleteByPrimaryKey(id);
        //递归删除所有子节点
        ContentCategory record = new ContentCategory();
        record.setParentId(id);
        List<ContentCategory> childRecords = super.queryListByWhere(record);
        if (!CollectionUtils.isEmpty(childRecords)) {
            for (ContentCategory contentCategory : childRecords) {
                deleteSubs(contentCategory.getId());
            }
        }
    }

    /**
     * 根据当前节点id查询所有子节点的id集合ids(包含当前节点id)
     *
     * @param ids 空的集合,用来存放递归结果并返回
     * @param id
     * @return
     */
    private List<Long> getSubIds(List<Long> ids, Long id) {
        //递归添加所有子节点
        ContentCategory record = new ContentCategory();
        record.setParentId(id);
        List<ContentCategory> childRecords = super.queryListByWhere(record);
        if (!CollectionUtils.isEmpty(childRecords)) {
            for (ContentCategory contentCategory : childRecords) {
                ids.add(contentCategory.getId());
                getSubIds(ids, contentCategory.getId());
            }
        }
        //当前节点id也加进去
        ids.add(id);
        //返回
        return ids;
    }
}
