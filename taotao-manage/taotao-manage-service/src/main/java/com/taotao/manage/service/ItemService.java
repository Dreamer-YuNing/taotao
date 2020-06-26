package com.taotao.manage.service;

import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ning_ on 2020/6/20.
 */
@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescService itemDescService;

    /**
     * 新增商品和商品信息描述
     * @param item
     * @param desc
     */
    public void saveItem(Item item,String desc){
        item.setStatus(1);
        item.setId(null);
        super.save(item);
        //创建itemDesc对象 封装desc和来自item中的id到该对象,再通过service的save存储
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.save(itemDesc);
    }

    /**
     * 编辑商品
     * @param item
     * @param desc
     */
    public void updateItem(Item item, String desc) {

        super.updateByPrimaryKey(item);
        //创建itemDesc对象 封装desc和来自item中的id到该对象,再通过service的save存储
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateByPrimaryKey(itemDesc);
    }
}
