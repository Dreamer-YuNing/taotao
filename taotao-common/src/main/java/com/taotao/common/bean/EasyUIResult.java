package com.taotao.common.bean;

import java.util.List;

/**
 * Created by ning_ on 2020/6/23.
 * 没有对应的实体类,是为了封装前端的分页查询结果集
 */
public class EasyUIResult {
    private Long total;
    private List<?> rows;

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
