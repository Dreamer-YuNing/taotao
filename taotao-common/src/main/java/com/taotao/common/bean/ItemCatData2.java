package com.taotao.common.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 封装二级类目(二级类目hava三级类目)
 */
public class ItemCatData2 {

    @JsonProperty("u")
    private String url;
    @JsonProperty("n")
    private String name;
    //三级类目的集合
    @JsonProperty("i")
    private List<String> itemCatStr;


    public ItemCatData2() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItemCatStr() {
        return itemCatStr;
    }

    public void setItemCatStr(List<String> itemCatStr) {
        this.itemCatStr = itemCatStr;
    }


}
