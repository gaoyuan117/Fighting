/*
 * IndexBanner     2016/9/7-09-07
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.kw.mall.model.scoremodel;
import service.api.BaseEntity;

public class IndexBanner extends BaseEntity {

    public Data[] data;

    public static class Data {
        public String id;
        public String image;
    }
}
