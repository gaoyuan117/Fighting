package me.kw.mall.model.scoremodel;

import service.api.BaseEntity;

public class JifenPayOrderReturn extends BaseEntity {

    public Data data;

    public static class Data {
        public String id;
        public String order_no;
    }
}
