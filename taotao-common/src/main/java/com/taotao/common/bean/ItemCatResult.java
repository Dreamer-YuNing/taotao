package com.taotao.common.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 最终返回的数据对象(包含所有拥有拥有三级类目的二级类目的一级类目)  json中以 {data:[]}
 * @author Henry
 *
 */
public class ItemCatResult {
	// 一级类目的集合
	@JsonProperty("data")
	private List<ItemCatData> itemCats;

	
	public ItemCatResult() {
		super();
	}

	public List<ItemCatData> getItemCats() {
		return itemCats;
	}

	public void setItemCats(List<ItemCatData> itemCats) {
		this.itemCats = itemCats;
	}
	
	
}


