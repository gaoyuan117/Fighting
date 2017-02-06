/*
 * NewsTitle     2016/12/12 10:41
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/12 10:41
 */
public class NewsTitleBean {

  /**
   * code : 1
   * message : 操作成功
   * data : [{"id":"2","title":"娱乐"},{"id":"3","title":"体育"}]
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
     * id : 2
     * title : 娱乐
     */

    private String id;
    private String title;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}
  }
}
