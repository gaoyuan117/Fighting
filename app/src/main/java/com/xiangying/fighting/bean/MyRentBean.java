/*
 * MyRentBean     2017/1/3 11:01
 * Copyright (c) 2017 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Koterwong on 2017/1/3 11:01
 */
public class MyRentBean implements Serializable{

  /**
   * code : 1
   * message : 操作成功
   * data : [{"id":"51","u_id":"2","litpic":"1091,1094","title":"和风花园","price":"100.00","hall_room":"","area":"100","floor":"4","height":"4","qu":"5446","qu_name":"87849","qu_site":"5446","description":"就这样咯了","type":"0","time":"1483412310","imgPath":["/Uploads/Picture/2016-12-30/58661438ccefa.jpg","/Uploads/Picture/2017-01-03/586b1356ba709.jpg"]}]
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

  public static class Data implements Serializable{
    /**
     * id : 51
     * u_id : 2
     * litpic : 1091,1094
     * title : 和风花园
     * price : 100.00
     * hall_room :
     * area : 100
     * floor : 4
     * height : 4
     * qu : 5446
     * qu_name : 87849
     * qu_site : 5446
     * description : 就这样咯了
     * type : 0
     * time : 1483412310
     * imgPath : ["/Uploads/Picture/2016-12-30/58661438ccefa.jpg","/Uploads/Picture/2017-01-03/586b1356ba709.jpg"]
     */

    private String id;
    private String u_id;
    private String litpic;
    private String title;
    private String price;
    private String hall_room;
    private String area;
    private String floor;
    private String height;
    private String qu;
    private String qu_name;
    private String qu_site;
    private String description;
    private String type;
    private String time;
    private List<String> imgPath;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getU_id() { return u_id;}

    public void setU_id(String u_id) { this.u_id = u_id;}

    public String getLitpic() { return litpic;}

    public void setLitpic(String litpic) { this.litpic = litpic;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getPrice() { return price;}

    public void setPrice(String price) { this.price = price;}

    public String getHall_room() { return hall_room;}

    public void setHall_room(String hall_room) { this.hall_room = hall_room;}

    public String getArea() { return area;}

    public void setArea(String area) { this.area = area;}

    public String getFloor() { return floor;}

    public void setFloor(String floor) { this.floor = floor;}

    public String getHeight() { return height;}

    public void setHeight(String height) { this.height = height;}

    public String getQu() { return qu;}

    public void setQu(String qu) { this.qu = qu;}

    public String getQu_name() { return qu_name;}

    public void setQu_name(String qu_name) { this.qu_name = qu_name;}

    public String getQu_site() { return qu_site;}

    public void setQu_site(String qu_site) { this.qu_site = qu_site;}

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public List<String> getImgPath() { return imgPath;}

    public void setImgPath(List<String> imgPath) { this.imgPath = imgPath;}
  }
}
