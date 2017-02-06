package me.kw.mall.model.scoremodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import service.api.BaseEntity;

public class EasyPeopleDetail  extends BaseEntity implements Serializable {

    @SerializedName("data")
    public Data data;

    public static class Data{
        public String id;
        public String title;
        public String thumb;
        public String content;
        public String add_time;
        public String update_time;
    }
}
