package com.taotao.web.service;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装httpClient,完成对后台的跨域访问
 * Created by ning_ on 2020/7/3.
 */
@Service
@SuppressWarnings("unchecked")
public class ApiService {
    // 注入在XML中已经配置好的HttpClient对象
    @Autowired(required = false)
    private CloseableHttpClient httpClient;

    // 默认的响应处理器
    private ResponseHandler<String> defaultHandler = new BasicResponseHandler();

    /**
     * 无参的get请求
     */
    public <T> T doGet(String uri, ResponseHandler<T> handler) throws IOException {
        // 判断是否有响应处理器，如果没有，用默认的
        handler = handler == null ? (ResponseHandler<T>) defaultHandler : handler;
        // 创建HttpGet请求，相当于在浏览器输入地址
        HttpGet httpGet = new HttpGet(uri);
        // 执行请求，相当于敲完地址后按下回车。获取响应
        return httpClient.execute(httpGet, handler);
    }

    /**
     * 有参get请求
     */
    public <T> T doGet(String uri, Map<String, String> params, ResponseHandler<T> handler) throws Exception {
        // 创建地址构建器
        URIBuilder builder = new URIBuilder(uri);
        // 拼接参数
        for (Map.Entry<String, String> me : params.entrySet()) {
            builder.addParameter(me.getKey(), me.getValue());
        }
        return doGet(builder.build().toString(), handler);
    }

    /**
     * 有参POST请求
     */
    public <T> T doPost(String uri, Map<String, String> params, ResponseHandler<T> handler)
            throws ParseException, IOException {
        // 判断是否有响应处理器，如果没有，用默认的
        handler = handler == null ? (ResponseHandler<T>) defaultHandler : handler;
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(uri);
        // 模拟浏览器
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
        if (params != null) {
            // 设置参数
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> me : params.entrySet()) {
                parameters.add(new BasicNameValuePair(me.getKey(), me.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }
        return httpClient.execute(httpPost, handler);
    }

    /**
     * 无参POST请求
     */
    public <T> T doPost(String uri, ResponseHandler<T> handler) throws ParseException, IOException {
        return doPost(uri, null, handler);
    }
}

