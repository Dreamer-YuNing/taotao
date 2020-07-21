package com.taotao.common.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by ning_ on 2020/7/20.
 */
public class SimpleResponseHandler<T> implements ResponseHandler<T> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //记录响应结果类型
    private Class<T> clazz;

    public SimpleResponseHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        //判断响应状态是否小于300
        if (response.getStatusLine().getStatusCode() < 300) {
            //解析数据
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            T t = MAPPER.readValue(content, clazz);
            return t;
        }
        return null;
    }
}
