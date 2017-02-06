/*
 * UploadVedioBean     2016/12/15 15:47
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

/**
 * Created by Koterwong on 2016/12/15 15:47
 */
public class UploadVedioBean {
  private int code;
  private String message;
  private Data data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public Data getData() { return data;}

  public void setData(Data data) { this.data = data;}

  public static class Data {
    private Video video;

    public Video getVideo() { return video;}

    public void setVideo(Video video) { this.video = video;}

    public static class Video {
      private String name;
      private String type;
      private int size;
      private String key;
      private String ext;
      private String md5;
      private String sha1;
      private String savename;
      private String savepath;
      private int id;

      public String getName() { return name;}

      public void setName(String name) { this.name = name;}

      public String getType() { return type;}

      public void setType(String type) { this.type = type;}

      public int getSize() { return size;}

      public void setSize(int size) { this.size = size;}

      public String getKey() { return key;}

      public void setKey(String key) { this.key = key;}

      public String getExt() { return ext;}

      public void setExt(String ext) { this.ext = ext;}

      public String getMd5() { return md5;}

      public void setMd5(String md5) { this.md5 = md5;}

      public String getSha1() { return sha1;}

      public void setSha1(String sha1) { this.sha1 = sha1;}

      public String getSavename() { return savename;}

      public void setSavename(String savename) { this.savename = savename;}

      public String getSavepath() { return savepath;}

      public void setSavepath(String savepath) { this.savepath = savepath;}

      public int getId() { return id;}

      public void setId(int id) { this.id = id;}
    }
  }
}
