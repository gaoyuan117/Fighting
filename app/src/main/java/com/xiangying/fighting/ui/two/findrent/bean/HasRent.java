package com.xiangying.fighting.ui.two.findrent.bean;

/**
 * Created by admin on 2017/1/19.
 */

public class HasRent {

    /**
     * code : 1
     * message : 操作成功
     * data : {"hasRent":1}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hasRent : 1
         */

        private int hasRent;

        public int getHasRent() {
            return hasRent;
        }

        public void setHasRent(int hasRent) {
            this.hasRent = hasRent;
        }
    }
}
