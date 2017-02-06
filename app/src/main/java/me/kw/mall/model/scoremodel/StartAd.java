package me.kw.mall.model.scoremodel;

import service.api.BaseEntity;

public class StartAd extends BaseEntity {

    public Data data;

    public static class Data {
        public String title;
        public String content;
        public String logo;
        public int status;
    }
}
