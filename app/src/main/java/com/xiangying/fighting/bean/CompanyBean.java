/*
 * CompanyListBen     2016/12/27 10:49
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.io.Serializable;

/**
 * Created by Koterwong on 2016/12/27 10:49
 */
public class CompanyBean {

  /**
   * code : 1
   * message : 操作成功
   * data : {"id":"94","uid":"15","company":"哈哈1","tel":"900800700","phone":"18954066795","name":"王大雷","region":"","site":"山东济宁","info":"噢噢噢哦哦摸摸摸噢噢噢哦哦共鸣明细休息晚自习嘻嘻","license":"","identity_card":"","type":"1"}
   */

  private int code;
  private String message;
  private Data data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public Data getData() { return data;}

  public void setData(Data data) { this.data = data;}

  public static class Data implements Serializable{
    /**
     * id : 94
     * uid : 15
     * company : 哈哈1
     * tel : 900800700
     * phone : 18954066795
     * name : 王大雷
     * region :
     * site : 山东济宁
     * info : 噢噢噢哦哦摸摸摸噢噢噢哦哦共鸣明细休息晚自习嘻嘻
     * license :
     * identity_card :
     * type : 1
     */

    private String id;
    private String uid;
    private String company;
    private String tel;
    private String phone;
    private String name;
    private String region;
    private String site;
    private String info;
    private String license;
    private String identity_card;
    private String type;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getUid() { return uid;}

    public void setUid(String uid) { this.uid = uid;}

    public String getCompany() { return company;}

    public void setCompany(String company) { this.company = company;}

    public String getTel() { return tel;}

    public void setTel(String tel) { this.tel = tel;}

    public String getPhone() { return phone;}

    public void setPhone(String phone) { this.phone = phone;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getRegion() { return region;}

    public void setRegion(String region) { this.region = region;}

    public String getSite() { return site;}

    public void setSite(String site) { this.site = site;}

    public String getInfo() { return info;}

    public void setInfo(String info) { this.info = info;}

    public String getLicense() { return license;}

    public void setLicense(String license) { this.license = license;}

    public String getIdentity_card() { return identity_card;}

    public void setIdentity_card(String identity_card) { this.identity_card = identity_card;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}
  }
}
