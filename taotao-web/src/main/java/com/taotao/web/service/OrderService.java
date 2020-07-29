package com.taotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.TaotaoResult;
import com.taotao.common.httpclient.SimpleResponseHandler;
import com.taotao.common.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pojo.Order;

import java.io.IOException;


/**
 * Created by ning_ on 2020/7/21.
 */
@Service
public class OrderService {
    @Autowired
    private ApiService apiService;

    @Value("${ORDER_TAOTAO_BASE_URL}")
    private String ORDER_TAOTAO_BASE_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public TaotaoResult sumbit(Order order) {
        String uri = ORDER_TAOTAO_BASE_URL + "/order/create";
        try {
            String json = MAPPER.writeValueAsString(order);
            return this.apiService.doPostJson(uri, json, new SimpleResponseHandler<>(TaotaoResult.class));
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "订单提交失败");
        }
    }

    public Order queryOrderById(String orderId) {
        String uri = ORDER_TAOTAO_BASE_URL + "/order/query/" + orderId;
        try {
            return this.apiService.doGet(uri, new SimpleResponseHandler<>(Order.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
