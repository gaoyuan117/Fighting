package com.xiangying.fighting.ui.three.bean;

import java.util.List;

/**
 * 作者：admin on 2017/2/6 10:05
 */

public class ZiJinDetailBean {


    /**
     * code : 1
     * message : 操作成功
     * data : [{"id":"11","cre_time":"1486535477","source":"充值","amount":"+10","uid":"3"},{"id":"12","cre_time":"1486535512","source":"提现","amount":"-20","uid":"3"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * cre_time : 1486535477
         * source : 充值
         * amount : +10
         * uid : 3
         */

        private String id;
        private String cre_time;
        private String source;
        private String amount;
        private String uid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCre_time() {
            return cre_time;
        }

        public void setCre_time(String cre_time) {
            this.cre_time = cre_time;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
