package com.taotao.manage.pojo;

/**
 * Created by ning_ on 2020/6/21.
 */
public class PicUploadResult {
    //状态码,0成功,1失败
    private Integer error;
    //图片地址
    private String url;
    //宽
    private String width;
    //高
    private String height;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
