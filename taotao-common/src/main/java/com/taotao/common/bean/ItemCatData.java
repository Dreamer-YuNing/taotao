package com.taotao.common.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 封装一级类目(一级类目have二级类目)
 */
public class ItemCatData {
	// @JsonProperty("u")将对象转json时，使用注解声明的key
	@JsonProperty("u")
	private String url;
	
	@JsonProperty("n")
	private String name;
	//二级类目的集合
	@JsonProperty("i")
	private List<ItemCatData2> itemCatDatas;

	
	public ItemCatData() {
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

	public List<ItemCatData2> getItemCatDatas() {
		return itemCatDatas;
	}

	public void setItemCatDatas(List<ItemCatData2> itemCatDatas) {
		this.itemCatDatas = itemCatDatas;
	}
	
	
	
}
