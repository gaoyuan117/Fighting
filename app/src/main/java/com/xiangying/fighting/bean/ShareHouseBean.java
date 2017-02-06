/*
 * ShareHouseBean     2016/12/16 14:14
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koterwong on 2016/12/16 14:14
 */
public class ShareHouseBean implements Serializable{
  private int code;
  private String message;
  private List<Data> data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public List<Data> getData() { return data;}

  public void setData(List<Data> data) { this.data = data;}

  public static class Data implements Serializable{
    private String id;
    private String uid;
    private String content;
    private String cover_ids;
    private String movie_id;
    private String create_time;
    private String is_energy;
    private String times;
    private String nickname;
    private String avatar;
    private String headimg;
    private String moviePath;
    private String countComment;
    private String praise;
    private ArrayList<Comment> comment;
    private List<String> imgPath;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getUid() { return uid;}

    public void setUid(String uid) { this.uid = uid;}

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content;}

    public String getCover_ids() { return cover_ids;}

    public void setCover_ids(String cover_ids) { this.cover_ids = cover_ids;}

    public String getMovie_id() { return movie_id;}

    public void setMovie_id(String movie_id) { this.movie_id = movie_id;}

    public String getCreate_time() { return create_time;}

    public void setCreate_time(String create_time) { this.create_time = create_time;}

    public String getIs_energy() { return is_energy;}

    public void setIs_energy(String is_energy) { this.is_energy = is_energy;}

    public String getTimes() { return times;}

    public void setTimes(String times) { this.times = times;}

    public String getNickname() { return nickname;}

    public void setNickname(String nickname) { this.nickname = nickname;}

    public String getAvatar() { return avatar;}

    public void setAvatar(String avatar) { this.avatar = avatar;}

    public String getHeadimg() { return headimg;}

    public void setHeadimg(String headimg) { this.headimg = headimg;}

    public String getMoviePath() {
      if (moviePath == null) {
        return "";
      }
      return
        moviePath;}

    public void setMoviePath(String moviePath) { this.moviePath = moviePath;}

    public String getCountComment() { return countComment;}

    public void setCountComment(String countComment) { this.countComment = countComment;}

    public String getPraise() { return praise;}

    public void setPraise(String praise) { this.praise = praise;}

    public ArrayList<Comment> getComment() {
      return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
      this.comment = comment;
    }

    public List<String> getImgPath() { return imgPath;}

    public void setImgPath(List<String> imgPath) { this.imgPath = imgPath;}
  }

  public static class Comment implements Serializable{
    private String id;
    private String e_id;
    private String z_id;
    private String is_read;
    private String z_time;
    private String text;
    private String nickname;
    private Object headimg;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getE_id() { return e_id;}

    public void setE_id(String e_id) { this.e_id = e_id;}

    public String getZ_id() { return z_id;}

    public void setZ_id(String z_id) { this.z_id = z_id;}

    public String getIs_read() { return is_read;}

    public void setIs_read(String is_read) { this.is_read = is_read;}

    public String getZ_time() { return z_time;}

    public void setZ_time(String z_time) { this.z_time = z_time;}

    public String getText() { return text;}

    public void setText(String text) { this.text = text;}

    public String getNickname() { return nickname;}

    public void setNickname(String nickname) { this.nickname = nickname;}

    public Object getHeadimg() { return headimg;}

    public void setHeadimg(Object headimg) { this.headimg = headimg;}
  }
}
