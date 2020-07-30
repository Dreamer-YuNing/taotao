package com.taotao.search.bean;

import java.util.List;

/**
 * Created by ning_ on 2020/7/29.
 */
public class PageResult<T> {
    //总条数
    private Long total;
    //数据集合
    private List<T> data;


    public PageResult() {
    }

    public PageResult(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
