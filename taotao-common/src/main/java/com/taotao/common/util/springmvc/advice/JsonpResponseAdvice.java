package com.taotao.common.util.springmvc.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * 统一处理ajax的异步jsonp请求
 * 相当于一个拦截器,拦截所有的Controller,判断所有request的参数是否有callback,如果有就拦截 使对应的方法的返回值是带有回调函数名的json
 * Created by ning_ on 2020/6/28.
 */
@ControllerAdvice(annotations = Controller.class)
public class JsonpResponseAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpResponseAdvice() {
        super("callback","jsonp");
    }
}
