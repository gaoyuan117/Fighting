package com.xiangying.fighting.bean;

/**
 * Created by admin on 2017/1/20.
 */

public class HasGroupBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"is_exist":0}
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
         * is_exist : 0
         */

        private int is_exist;

        public int getIs_exist() {
            return is_exist;
        }

        public void setIs_exist(int is_exist) {
            this.is_exist = is_exist;
        }
    }
}
