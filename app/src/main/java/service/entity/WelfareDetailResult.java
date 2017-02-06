/*
 * WelfareDetailResult     2016/11/19 09:30
 * Copyright (c) 2016 Koterwong All right reserved
 */
package service.entity;

import service.api.BaseEntity;

/**
 * Created by Koterwong on 2016/11/19 09:30
 */
public class WelfareDetailResult extends BaseEntity{
  public Data data;

  public static class Data {
    public String id;
    public String title;
    public String litpic;
    public String price;
    public String info;
    public String description;
    public String time;
    public String count;
    public String w_price;
  }
}
