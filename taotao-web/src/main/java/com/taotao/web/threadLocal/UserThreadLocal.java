package com.taotao.web.threadLocal;

import pojo.User;

/**
 * 保存当前线程的用户信息
 * Created by ning_ on 2020/7/26.
 */
public abstract class UserThreadLocal {
    private static final ThreadLocal<User> TL = new ThreadLocal<>();

    public static void set(User user) {
        TL.set(user);
    }

    public static User get() {
        return TL.get();
    }

    public static void remove() {
        TL.remove();
    }

}
