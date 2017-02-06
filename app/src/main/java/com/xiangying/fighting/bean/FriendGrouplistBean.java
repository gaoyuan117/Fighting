/*
 * FriendGrouplistBean     2016/12/22 13:47
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/22 13:47
 */
public class FriendGrouplistBean {

  /**
   * code : 1
   * message : 操作成功
   * data : [{"id":"4","name":"战友","uid":"2"},{"id":"5","name":"战友","uid":"2"},{"id":"12","name":"分组1","uid":"2"},{"id":"13","name":"分组2","uid":"2"},{"id":"14","name":"分组3","uid":"2"},{"id":"15","name":"分组4","uid":"2"},{"id":"16","name":"分组5","uid":"2"}]
   */

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
    /**
     * id : 4
     * name : 战友
     * uid : 2
     */

    private String id;
    private String name;
    private String uid;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getUid() { return uid;}

    public void setUid(String uid) { this.uid = uid;}
  }
}
