/*
 * GroupBean     2016/12/23 09:53
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/23 09:53
 */
public class GroupBean {
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
    private String id;
    private String name;
    private String uid;
    private String is_top;
    private String update_time;
    private String cover_id;
    private String imgPath;
    private String countMember;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getUid() { return uid;}

    public void setUid(String uid) { this.uid = uid;}

    public String getIs_top() { return is_top;}

    public void setIs_top(String is_top) { this.is_top = is_top;}

    public String getUpdate_time() { return update_time;}

    public void setUpdate_time(String update_time) { this.update_time = update_time;}

    public String getCover_id() { return cover_id;}

    public void setCover_id(String cover_id) { this.cover_id = cover_id;}

    public String getImgPath() { return imgPath;}

    public void setImgPath(String imgPath) { this.imgPath = imgPath;}

    public String getCountMember() { return countMember;}

    public void setCountMember(String countMember) { this.countMember = countMember;}
  }
}
