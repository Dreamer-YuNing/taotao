package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ning_ on 2020/6/21.
 */
@Service
public class PropertiesService {
    //图片上传到的 实际服务器路径
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
    //客户端访问图片的域名配置
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;

    //redis缓存的查询所有内容分类Itemcat的key
    @Value("${REDIS_ITEM_CAT_ALL_KEY}")
    public String REDIS_ITEM_CAT_ALL_KEY;

    //redis缓存中内容分类Itemcats的生存时间
    @Value("${REDIS_ITEM_CAT_AKK_TIME}")
    public Integer REDIS_ITEM_CAT_AKK_TIME;
}
