package com.taotao.manage.service;

import com.taotao.common.service.ApiService;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.apache.http.impl.client.BasicResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ning_ on 2020/6/20.
 */
@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ApiService apiService;
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
     * 编辑更新商品
     * @param item
     * @param desc
     */
    public void updateItem(Item item, String desc){

        super.updateByPrimaryKey(item);
        //创建itemDesc对象 封装desc和来自item中的id到该对象,再通过service的save存储
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateByPrimaryKey(itemDesc);
        //调用前台接口,删除商品在前端的缓存
        String uri = "http://www.taotao.com/cache/item/"+item.getId()+".html";
        try {
            //调用httpclient发起请求
            this.apiService.doPost(uri,null);
        } catch (IOException e) {
            e.printStackTrace();
    }
    }
}
