package me.kw.mall.model.scoremodel;

import service.api.BaseEntity;

public class OrderMergeReturn extends BaseEntity {

    public Data data;
    public static class Data{
        public String order_no;
        public String price;
        public String name;
        public String order_ids;
        public String time;
        public String pay_status;
    }
}
