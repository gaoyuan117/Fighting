/*
 * WelfareListResult     2016/11/18 17:37
 * Copyright (c) 2016 Koterwong All right reserved
 */
package service.entity;

import java.util.List;

import service.api.BaseEntity;

/**
 * Created by Koterwong on 2016/11/18 17:37
 */
public class WelfareResult extends BaseEntity {
  public List<Data> data;

  public static class Data {
    public String id;
    public String title;
    public String price;
    public String time;
    public String hasmoney;
  }
}
