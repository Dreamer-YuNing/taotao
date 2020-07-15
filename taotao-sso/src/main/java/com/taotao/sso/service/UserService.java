package com.taotao.sso.service;

import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ning_ on 2020/7/15.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

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
}
