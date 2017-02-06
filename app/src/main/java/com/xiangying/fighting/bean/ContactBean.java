/*
 * ContactBean     2016/12/21 11:16
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/21 11:16
 */
public class ContactBean {
  private int code;
  private String message;
  private List<Data> data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public List<Data> getData() { return data;}

  public void setData(List<Data> data) { this.data = data;}

  public static class Data {

    public Data(String groupName) {
      this.groupName = groupName;
    }

    private String groupName;
    private List<EaseUser> users;

    public String getGroupName() { return groupName;}

    public void setGroupName(String groupName) { this.groupName = groupName;}

    public List<EaseUser> getUsers() { return users;}
    public void setUsers(List<EaseUser> users) { this.users = users;}

  }
}
