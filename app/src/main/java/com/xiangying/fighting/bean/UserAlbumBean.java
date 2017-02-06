/*
 * UserAlbumBean     2016/12/16 20:03
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/16 20:03
 */
public class UserAlbumBean {
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
    private String content;
    private String cover_ids;
    private String uid;
    private long create_time;
    private List<String> imgpath;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content;}

    public String getCover_ids() { return cover_ids;}

    public void setCover_ids(String cover_ids) { this.cover_ids = cover_ids;}

    public String getUid() { return uid;}

    public void setUid(String uid) { this.uid = uid;}

    public long getCreate_time() { return create_time;}

    public void setCreate_time(long create_time) { this.create_time = create_time;}

    public List<String> getImgpath() { return imgpath;}

    public void setImgpath(List<String> imgpath) { this.imgpath = imgpath;}
  }
}
