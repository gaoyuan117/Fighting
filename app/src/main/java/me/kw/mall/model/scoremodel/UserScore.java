package me.kw.mall.model.scoremodel;

import service.api.BaseEntity;

public class UserScore extends BaseEntity {

    public Data data;

    public static class Data {
        public String score;
    }

}
