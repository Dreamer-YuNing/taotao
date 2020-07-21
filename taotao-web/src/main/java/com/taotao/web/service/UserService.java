package com.taotao.web.service;

import com.taotao.common.httpclient.SimpleResponseHandler;
import com.taotao.common.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pojo.User;

import java.io.IOException;

/**
 * Created by ning_ on 2020/7/20.
 */
@Service
public class UserService {
    @Autowired
    private ApiService apiService;

    @Value("${SSO_TAOTAO_BASE_URL}")
    public String SSO_TAOTAO_BASE_URL;

    public User queryUserByToken(String token) {
        String uri = SSO_TAOTAO_BASE_URL + "/user/" + token + ".html";
        try {
            User user = this.apiService.doGet(uri, new SimpleResponseHandler<>(User.class));
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
