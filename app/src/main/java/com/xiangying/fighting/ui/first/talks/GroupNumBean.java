package com.xiangying.fighting.ui.first.talks;

import java.util.List;

/**
 * Created by admin on 2017/1/24.
 */

public class GroupNumBean {

    /**
     * code : 1
     * message : 操作成功
     * data : [90658,62130,50465,37603,12337]
     */

    private int code;
    private String message;
    private List<Integer> data;

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

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
