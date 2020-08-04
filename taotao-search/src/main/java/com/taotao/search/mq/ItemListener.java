package com.taotao.search.mq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ning_ on 2020/8/4.
 */
public class ItemListener {
    @Autowired
    private HttpSolrServer server;
    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void consume(String msg) {
        try {
            // 解析消息
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long itemId = jsonNode.get("itemId").asLong();
            String type = jsonNode.get("type").asText();

            // 判断操作类型
            if (StringUtils.equals("insert", type) || StringUtils.equals("update", type)) {
                // 根据ID查询商品
                Item item = this.itemService.queryItemById(itemId);
                if(item != null){
                    // 新增数据到solr
                    this.server.addBean(item);
                    this.server.commit();
                }
            } else if (StringUtils.equals("delete", type)) {
                // 删除solr数据
                this.server.deleteById(itemId.toString());
                this.server.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
