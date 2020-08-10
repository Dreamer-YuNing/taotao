package com.taotao.cart.service;

import com.taotao.cart.pojo.Item;
import com.taotao.common.httpclient.SimpleResponseHandler;
import com.taotao.common.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ning_ on 2020/8/10.
 */
@Service
public class ItemService {
    @Autowired
    private ApiService apiService;
    @Value("${MANAGE_TAOTAO_BASE_URL}")
    private String MANAGE_TAOTAO_BASE_URL;

    public Item queryItemById(Long itemId) {
        String uri = MANAGE_TAOTAO_BASE_URL + "/rest/api/item/" + itemId;
        try {
            Item item = this.apiService.doGet(uri, new SimpleResponseHandler<>(Item.class));
            return item;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
