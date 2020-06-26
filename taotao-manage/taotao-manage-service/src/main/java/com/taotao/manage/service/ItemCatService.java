package com.taotao.manage.service;

import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatData2;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ning_ on 2020/6/17.
 */

@Service
public class ItemCatService extends BaseService<ItemCat>{

    @Autowired
    private ItemCatMapper itemCatMapper;


    public ItemCatResult queryItemCatAllToTree() {
        ItemCatResult itemCatResult = new ItemCatResult();
        List<ItemCat> itemCats = super.queryAll();
        Map<Long, List<ItemCat>> map = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat:itemCats) {
            if (!map.containsKey(itemCat.getParentId())){
                map.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            map.get(itemCat.getParentId()).add(itemCat);
        }
        ArrayList<ItemCatData> itemCatDatasl = new ArrayList<>();
        itemCatResult.setItemCats(itemCatDatasl);
        List<ItemCat> itemCats1 = map.get(0L);
        for (ItemCat itemCat1:itemCats1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatDatasl.add(itemCatData);
            itemCatData.setUrl("/products"+itemCat1.getName()+".html");
            itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>"+itemCat1.getName()+"<a>");
            ArrayList<ItemCatData2> itemCatData2s = new ArrayList<>();
            itemCatData.setItemCatDatas(itemCatData2s);
            List<ItemCat> itemCats2 = map.get(itemCat1.getId());
            for (ItemCat itemCat2:itemCats2) {
                ItemCatData2 itemCatData2 = new ItemCatData2();
                itemCatData2s.add(itemCatData2);
                itemCatData2.setUrl("/products"+itemCat2.getName()+".html");
                itemCatData2.setName("<a href='"+itemCatData2.getUrl()+"'>"+itemCat2.getName()+"<a>");
                ArrayList<String> strings = new ArrayList<>();
                itemCatData2.setItemCatStr(strings);
                List<ItemCat> itemCats3 = map.get(itemCat2.getId());
                for (ItemCat itemCat3:itemCats3) {
                    strings.add("/products/" +itemCat3.getId()+ ".html|" + itemCat3.getName());
                }
            }

        }
        return itemCatResult;
    }

    /*public List<ItemCat> queryItemCat(Long parentId) {
        ItemCat record = new ItemCat();
        record.setParentId(parentId);
        List<ItemCat> list = this.itemCatMapper.select(record);
        System.out.println(list);
        return list;
    }*/
}
