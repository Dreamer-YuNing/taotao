package com.taotaoomanage.service;

import com.taotao.common.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ning_ on 2020/7/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void set() throws Exception {
        redisService.set("age","27");
    }

    @Test
    public void get() throws Exception {
        System.out.println(redisService.get("REDIS_ITEM_CAT_ALL_KEY"));
    }

    @Test
    public void expire() throws Exception {
    }

    @Test
    public void set1() throws Exception {
    }

    @Test
    public void del() throws Exception {
    }

}