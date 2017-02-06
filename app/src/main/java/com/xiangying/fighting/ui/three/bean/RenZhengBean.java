package com.xiangying.fighting.ui.three.bean;

import java.util.List;

/**
 * Created by admin on 2017/1/22.
 */

public class RenZhengBean {

    /**
     * code : 1
     * message : 操作成功
     * data : []
     */

    private int code;
    private String message;
    private List<?> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
