package com.xiangying.fighting.ui.two.findrent.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoniao on 2016/9/18.
 */
public class RentList {

  private int code;
  private String message;

  public List<RentList.Data> data;

  public static class Data {
    public String id;
    public String u_id;
    public String litpic;
    public String title;
    public String price;
    public String hall_room;
    public String area;
    public String floor;
    public String height;
    public String qu;
    public String qu_name;
    public String qu_site;
    public String description;
    public String type;
    public String time;
    public ArrayList<String> imgPath;
  }
}
