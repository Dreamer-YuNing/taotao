package com.taotao.common.httpclient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.bean.EasyUIResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by ning_ on 2020/7/5.
 *  自定义响应处理器，把响应结果处理为EasyUIResult对象
 * @param <T> EasyUIResult中的List元素类型
 */
public class EasyUIResultResponseHandler<T> implements ResponseHandler<EasyUIResult> {

    private static ObjectMapper mapper = new ObjectMapper();

    // 记录响应EasyUIResult中的rows集合的元素类型
    private Class<T> clazz;

    public EasyUIResultResponseHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public EasyUIResult handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 解析响应，获取数据
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            // 解析Json数据
            JsonNode jsonNode = mapper.readTree(content);
            // 获取所有行数据
            ArrayNode data = (ArrayNode)jsonNode;
            // 准备集合，用来封装所有行数据
            List<T> list = null;
            if (data.isArray() && data.size() > 0) {
                // 将Json数据转为集合数据
                list = mapper.readValue(data.traverse(),
                        mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            }

            return new EasyUIResult(new Long(list.size()), list);
        }

        return null;

    }
}
