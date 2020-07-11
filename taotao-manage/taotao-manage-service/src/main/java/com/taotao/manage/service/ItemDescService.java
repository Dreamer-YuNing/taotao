package com.taotao.manage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ning_ on 2020/6/20.
 */
@Service
public class ItemDescService extends BaseService<ItemDesc> {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PropertiesService propertiesService;
    //jackson的ObjectMapper
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public ItemDesc queryById(Long itemId){
        //查询redis
        String redisKey = propertiesService.REDIS_ITEM_DESC_KEY.replace("{itemId}",itemId.toString());
        String json = redisService.get("redisKey");
        //判断是否命中
        if (StringUtils.isNotEmpty(json)) {
            try {
                //命中,返回redis中查询到的数据
                ItemDesc itemDesc = MAPPER.readValue(json, ItemDesc.class);
                return itemDesc;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //没命中,去数据库中查询
        ItemDesc itemDesc = super.queryById(itemId);
        try {
            //将数据库中查询到的数据放入redis
            redisService.set(redisKey, MAPPER.writeValueAsString(itemDesc),propertiesService.REDIS_ITEM_DESC_AKK_TIME);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //返回数据库中查询到的数据
        return itemDesc;
    }
}
