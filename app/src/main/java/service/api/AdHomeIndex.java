/*
 * AdHomeIndex     2016/11/18 09:38
 * Copyright (c) 2016 Koterwong All right reserved
 */
package service.api;

import java.util.List;

/**
 * Created by Koterwong on 2016/11/18 09:38
 */
public class AdHomeIndex extends BaseEntity {

  public List<Data> data;

  public static class Data {
    public String id;
    public String title;
    public String content;
    public String create_time;
    public String image;
  }
}
