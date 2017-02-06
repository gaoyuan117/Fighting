/*
 * MerryIndex     2016/10/26 09:21
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Koterwong on 2016/10/26 09:21
 */
public class MerryIndex implements Serializable{

  public int code;
  public String message;
  public ArrayList<Data> data;

  public static class Data implements Serializable{
    public String id;
    public String name;
    public String head;
    public String litpic;
    public String imgPath;
    public String age;
    public String sex;
    public String region;
    public String birthday;
    public String stature;
    public String weight;
    public String education;
    public String income;
    public String married;
    public String room;
    public String car;
    public String info;
    public String zo_region;
    public String zo_age;
    public String zo_stature;
    public String zo_education;
    public String zo_income;
    public String zo_married;
    public String zo_room;
    public String zo_car;
    public String type;
    public String genre;
    public String roomlitpic;
    public String carlitpic;
    public String username;
  }
}
