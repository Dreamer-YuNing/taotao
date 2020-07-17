package com.taotao.sso.pojo;

/**
 * 通用的页面结果对象
 * Created by ning_ on 2020/7/15.
 */
public class TaotaoResult {
    //封装状态码,200代表成功
    private Integer status;
    //返回的消息
    private String msg;
    //可能携带的数据
    private Object data;

    // 构造函数
    public TaotaoResult(Integer status) {
        super();
        this.status = status;
    }

    public TaotaoResult(Integer status, String msg) {
        this(status);
        this.msg = msg;
    }

    public TaotaoResult(Integer status, String msg, Object data) {
        this(status,msg);
        this.data = data;
    }

    // 快捷获取200状态
    public static TaotaoResult ok(){
        return new TaotaoResult(200);
    }
    // 快捷获取PageResult对象
    public static TaotaoResult build(Integer status,String msg){
        return new TaotaoResult(status,msg);
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
