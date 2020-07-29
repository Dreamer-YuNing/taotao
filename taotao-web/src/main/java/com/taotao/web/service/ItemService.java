package com.taotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.vo.ItemVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ning_ on 2020/7/10.
 */
@Service
public class ItemService {
    //注入ApiService
    @Autowired
    private ApiService apiService;

    //注入common模块的redisService
    @Autowired
    private RedisService redisService;

    //读取后台模块域名
    @Value("${MANAGE_TAOTAO_BASE_URL}")
    private String MANAGE_TAOTAO_BASE_URL;

    //获取item接口格式,用于拼接
    @Value("${ITEM_PATH}")
    private String ITEM_PATH;

    //注入itemDesc的后台接口格式,用于拼接
    @Value("${ITEM_DESC_PATH}")
    private String ITEM_DESC_PATH;

    //获取item在redis缓存的key
    @Value("${REDIS_ITEM_KEY}")
    public String REDIS_ITEM_KEY;

    //获取item在redis中的缓存的时间
    @Value("${REDIS_ITEM_TIME}")
    private Integer REDIS_ITEM_TIME;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 根据id查询商品(ps.返回的是 Item的子类ItemVO适应前端页面数据格式需求)
     *
     * @param itemId
     * @return
     */
    public ItemVO queryItemVOById(Long itemId) {
        //查询redis
        String redisKey = REDIS_ITEM_KEY + itemId;
        String jsonResult = redisService.get(redisKey);
        //判断是否命中
        if (StringUtils.isNotEmpty(jsonResult)) {
            try {
                //命中,返回redis中查询到的数据
                ItemVO itemVO = MAPPER.readValue(jsonResult, ItemVO.class);
                //返回给controller
                return itemVO;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //没命中调后台接口
        try {
            String uri = MANAGE_TAOTAO_BASE_URL + ITEM_PATH.replace("{itemId}", itemId.toString());
            String json = apiService.doGet(uri, new BasicResponseHandler());
            if (json == null) {
                return null;
            }
            try {
                //将从后台查询到的数据放入前台redis
                redisService.set(redisKey, json, REDIS_ITEM_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //返回给controller
            return MAPPER.readValue(json, ItemVO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id查询商品详情
     *
     * @param itemId
     * @return
     */
    public ItemDesc queryItemDescById(Long itemId) {
        try {
            String uri = MANAGE_TAOTAO_BASE_URL + ITEM_DESC_PATH.replace("{itemId}", itemId.toString());
            String json = apiService.doGet(uri, new BasicResponseHandler());
            return MAPPER.readValue(json, ItemDesc.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
