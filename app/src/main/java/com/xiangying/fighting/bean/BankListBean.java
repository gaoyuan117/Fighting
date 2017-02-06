/*
 * BankListBean     2016/12/16 09:20
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/16 09:20
 */
public class BankListBean{

    /**
     * code : 1
     * message : 操作成功
     * data : {"total":"5","result":[{"id":"1","card_no":"6228481326505451060","true_name":"高原","is_default":"1","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"2","card_no":"6228481326505451063","true_name":"高高","is_default":"0","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"3","card_no":"6228481326505451069","true_name":"哈哈","is_default":"0","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"4","card_no":"6228481326505451096","true_name":"哈哈","is_default":"0","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"5","card_no":"6228463494379849463","true_name":"呃呃呃","is_default":"0","bank_title":"农业银行-金穗通宝卡-借记卡"}]}
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
         * total : 5
         * result : [{"id":"1","card_no":"6228481326505451060","true_name":"高原","is_default":"1","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"2","card_no":"6228481326505451063","true_name":"高高","is_default":"0","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"3","card_no":"6228481326505451069","true_name":"哈哈","is_default":"0","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"4","card_no":"6228481326505451096","true_name":"哈哈","is_default":"0","bank_title":"农业银行-金穗通宝卡(银联卡)-借记卡"},{"id":"5","card_no":"6228463494379849463","true_name":"呃呃呃","is_default":"0","bank_title":"农业银行-金穗通宝卡-借记卡"}]
         */

        private String total;
        private List<ResultBean> result;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * id : 1
             * card_no : 6228481326505451060
             * true_name : 高原
             * is_default : 1
             * bank_title : 农业银行-金穗通宝卡(银联卡)-借记卡
             */

            private String id;
            private String card_no;
            private String true_name;
            private String is_default;
            private String bank_title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCard_no() {
                return card_no;
            }

            public void setCard_no(String card_no) {
                this.card_no = card_no;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getBank_title() {
                return bank_title;
            }

            public void setBank_title(String bank_title) {
                this.bank_title = bank_title;
            }
        }
    }
}
