package com.taotao.search.service;

import com.taotao.common.httpclient.SimpleResponseHandler;
import com.taotao.common.service.ApiService;
import com.taotao.search.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ning_ on 2020/8/4.
 */
@Service
public class ItemService {

    @Value("${TAOTAO_MANAGE_BASE_PATH}")
    private String TAOTAO_MANAGE_BASE_PATH;

    @Autowired
    private ApiService apiService;

    public Item queryItemById(Long itemId) {
        String uri = TAOTAO_MANAGE_BASE_PATH+"/rest/api/item/"+itemId;
        try {
            Item item = this.apiService.doGet(uri, new SimpleResponseHandler<>(Item.class));
            System.out.println(item);
            return item;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
