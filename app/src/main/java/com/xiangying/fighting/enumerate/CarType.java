/*
 * MarryType     2016/11/15 17:55
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.enumerate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koterwong on 2016/11/15 17:55
 */
public enum CarType {
  ALL("不限", "0"),
  HAVA("有车", "1"),
  UNHAVE("没有", "2");

  private String name;
  private String value;

  CarType(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public static List<CarType> toList() {
    CarType[] marryTypes = CarType.values();
    List<CarType> listType = new ArrayList<>();
    for (int i = 0; i < marryTypes.length; i++) {
      listType.add(marryTypes[i]);
    }
    return listType;
  }

  public static String getNameById(String value) {
    List<CarType> list = toList();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getValue().equals(value)) {
        return list.get(i).getName();
      }
    }
    return "";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
