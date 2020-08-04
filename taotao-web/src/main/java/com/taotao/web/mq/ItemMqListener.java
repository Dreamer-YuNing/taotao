package com.taotao.web.mq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.Item;
import com.taotao.web.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by ning_ on 2020/8/4.
 */
public class ItemMqListener {

    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void getMsg(String msg) {
        if (StringUtils.isNotEmpty(msg)){
            try {
                JsonNode jsonNode = MAPPER.readTree(msg);
                //操作类型
                String type = jsonNode.get("type").asText();
                //获取对象id
                Long itemId = jsonNode.get("itemId").asLong();
                if (StringUtils.equals("update",type)||StringUtils.equals("delete",type)){
                    this.itemService.clearRedisCacheByItemId(itemId);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
