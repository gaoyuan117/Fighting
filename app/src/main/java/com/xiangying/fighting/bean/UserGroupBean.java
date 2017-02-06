/*
 * UserGroupBean     2016/12/24 14:43
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/24 14:43
 */
public class UserGroupBean {

  private int code;
  private String message;
  private DataX data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public DataX getData() { return data;}

  public void setData(DataX data) { this.data = data;}

  public static class DataX {
    private String action;
    private String uri;
    private long timestamp;
    private int duration;
    private int count;
    private List<?> entities;
    private List<Data> data;

    public String getAction() { return action;}

    public void setAction(String action) { this.action = action;}

    public String getUri() { return uri;}

    public void setUri(String uri) { this.uri = uri;}

    public long getTimestamp() { return timestamp;}

    public void setTimestamp(long timestamp) { this.timestamp = timestamp;}

    public int getDuration() { return duration;}

    public void setDuration(int duration) { this.duration = duration;}

    public int getCount() { return count;}

    public void setCount(int count) { this.count = count;}

    public List<?> getEntities() { return entities;}

    public void setEntities(List<?> entities) { this.entities = entities;}

    public List<Data> getData() { return data;}

    public void setData(List<Data> data) { this.data = data;}

    public static class Data {
      private String groupid;
      private String groupname;

      public String getGroupid() { return groupid;}

      public void setGroupid(String groupid) { this.groupid = groupid;}

      public String getGroupname() { return groupname;}

      public void setGroupname(String groupname) { this.groupname = groupname;}
    }
  }
}
