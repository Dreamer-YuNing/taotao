package com.taotao.web.vo;

import com.taotao.manage.pojo.Item;
import org.apache.commons.lang3.StringUtils;

/**
 * 为了适应前台商品详情页的商品数据格式,继承后台的Item,并且添加新的getImages方法.对应jsp页面的
 * Created by ning_ on 2020/7/10.
 */
public class ItemVO extends Item {
    //获取图片的方法
    public String[] getImages(){
        return StringUtils.split(getImage(),",");
    }
}
