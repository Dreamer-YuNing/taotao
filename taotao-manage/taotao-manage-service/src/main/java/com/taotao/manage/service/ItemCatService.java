package com.taotao.manage.service;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by ning_ on 2020/6/17.
 */

@Service
public class ItemCatService extends BaseService<ItemCat>{

    @Autowired
    private ItemCatMapper itemCatMapper;

    /*public List<ItemCat> queryItemCat(Long parentId) {
        ItemCat record = new ItemCat();
        record.setParentId(parentId);
        List<ItemCat> list = this.itemCatMapper.select(record);
        System.out.println(list);
        return list;
    }*/
}
