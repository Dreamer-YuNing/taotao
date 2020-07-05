package com.taotao.web.service;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.web.httpclient.EasyUIResultResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ning_ on 2020/7/3.
 */
@Service
public class IndexService {
    // 从配置文件中读取后台服务器的地址
    @Value("${MANAGE_TAOTAO_BASE_URL}")
    private String MANAGE_TAOTAO_BASE_URL;
    // 从配置文件中读取广告1的资源路径
    @Value("${INDEXAD1_PATH}")
    private String INDEXAD1_PATH;
    // 从配置文件中读取广告2的资源路径
    @Value("${INDEXAD2_PATH}")
    private String INDEXAD2_PATH;

    @Autowired
    private ApiService apiService;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 查询首页大广告数据
     *
     * @return 数据的JSON格式
     */
    public String queryIndexAd1() {
        try {
            // 准备URL地址
            String url = MANAGE_TAOTAO_BASE_URL + INDEXAD1_PATH;
            // 查询后台接口，获取EasyResult对象
            EasyUIResult<Content> easyUIResult = this.apiService.doGet(url, new EasyUIResultResponseHandler<>(Content.class));
            // 准备一个集合，用来封装最后的结果
            List<Map<String, Object>> result = new ArrayList<>();
            // 遍历,取出每一条大广告数据
            for (Content content : easyUIResult.getRows()) {
                Map<String, Object> map = new LinkedHashMap<>();
                // 封装页面所需数据
                map.put("srcB", content.getPic2());
                map.put("height", 240);
                map.put("alt", content.getTitle());
                map.put("width", 670);
                map.put("src", content.getPic());
                map.put("widthB", 550);
                map.put("href", content.getUrl());
                map.put("heightB", 240);
                result.add(map);
            }
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 一旦没有查到数据，或者数据为空，或者抛出异常，给出一个默认的广告数据。
        return null;
    }

    public String queryIndexAd2() {
        try {
            // 准备URL地址
            String url = MANAGE_TAOTAO_BASE_URL + INDEXAD2_PATH;
            // 查询后台接口，获取EasyResult对象
            EasyUIResult<Content> easyUIResult = this.apiService.doGet(url, new EasyUIResultResponseHandler<>(Content.class));
            // 准备一个集合，用来封装最后的结果
            List<Map<String, Object>> result = new ArrayList<>();
            // 遍历,取出每一条大广告数据
            for (Content content : easyUIResult.getRows()) {
                Map<String, Object> map = new LinkedHashMap<>();
                // 封装页面所需数据
                map.put("srcB", content.getPic2());
                map.put("height", 240);
                map.put("alt", content.getTitle());
                map.put("width", 670);
                map.put("src", content.getPic());
                map.put("widthB", 550);
                map.put("href", content.getUrl());
                map.put("heightB", 240);
                result.add(map);
            }
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 一旦没有查到数据，或者数据为空，或者抛出异常，给出一个默认的广告数据。
        return null;
    }
}

