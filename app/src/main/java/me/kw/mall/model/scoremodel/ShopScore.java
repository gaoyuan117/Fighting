package me.kw.mall.model.scoremodel;

import service.api.BaseEntity;

public class ShopScore extends BaseEntity {

    public Data data;

    public static class Data {
        public String give_score;
        public String recover_score;
        public String uid;
        public String id;
        public String score;
    }
}
