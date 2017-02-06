/*
 * UserMovieBean     2016/12/20 09:17
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/20 09:17
 */
public class UserMovieBean {
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
    private String uid;
    private String content;
    private String movie_id;
    private long create_time;
    private String moviePath;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getUid() { return uid;}

    public void setUid(String uid) { this.uid = uid;}

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content;}

    public String getMovie_id() { return movie_id;}

    public void setMovie_id(String movie_id) { this.movie_id = movie_id;}

    public long getCreate_time() { return create_time;}

    public void setCreate_time(long create_time) { this.create_time = create_time;}

    public String getMoviePath() { return moviePath;}

    public void setMoviePath(String moviePath) { this.moviePath = moviePath;}
  }
}
