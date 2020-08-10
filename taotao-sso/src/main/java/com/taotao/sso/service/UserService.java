package com.taotao.sso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.pojo.User;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * Created by ning_ on 2020/7/15.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 检验数据是否有效(可用性)
     *
     * @param param 数据
     * @param type  数据类型
     * @return true 数据合格 false 数据不合格
     */
    public Boolean checkData(String param, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            case 3:
                user.setEmail(param);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(user) == 0;
    }

    public Boolean register(User user) {
        //初始化用户数据
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());

        //对秘密进行加密,这里使用了apache的加密工具
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        int count = this.userMapper.insert(user);
        return count == 1;
    }

    public String login(String username, String password) throws JsonProcessingException {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if (user == null) {
            //用户名不存在,返回null,登陆失败,显示用户名或者密码错误
            return null;
        }
        if (!StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(password))) {
            //用户名存在密码错误,返回null,登陆失败,显示用户名或者密码错误
            return null;
        }
        //登陆成功了需要两件事
        //1)生成token
        String token = DigestUtils.md5Hex(username + System.currentTimeMillis());
        //2)将token放入redis
        this.redisService.set("TOKEN_"+token,MAPPER.writeValueAsString(user),1800);
        return token;
    }

    public User queryByToken(String token) {
        String key = "TOKEN_"+token;
        String jsonData = this.redisService.get(key);
        if(StringUtils.isEmpty(jsonData)){
            return null;
        }
        try {
            //刷新缓存存活时间
            redisService.expire(key,1800);
            return MAPPER.readValue(jsonData,User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
