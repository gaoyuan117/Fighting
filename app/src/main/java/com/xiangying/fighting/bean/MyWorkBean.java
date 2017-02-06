/*
 * MyWorkBean     2016/12/29 18:38
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.ArrayList;

/**
 * Created by Koterwong on 2016/12/29 18:38
 */
public class MyWorkBean {

  /**
   * code : 1
   * message : 操作成功
   * data : [{"work":"明","treatment":"8000元以上","obligation":"94946466464664","criteria":"我你你你","workplace":"北京北京","address":"破哦你","tel":"1594946","worktype":"UI设计师","company":"攻击力"}]
   */

  private int code;
  private String message;
  private ArrayList<Data> data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public ArrayList<Data> getData() { return data;}

  public void setData(ArrayList<Data> data) { this.data = data;}

  public static class Data {
    /**
     * work : 明
     * treatment : 8000元以上
     * obligation : 94946466464664
     * criteria : 我你你你
     * workplace : 北京北京
     * address : 破哦你
     * tel : 1594946
     * worktype : UI设计师
     * company : 攻击力
     */
    private String id;
    private String cid;
    private String work;
    private String treatment;
    private String obligation;
    private String criteria;
    private String workplace;
    private String address;
    private String tel;
    private String worktype;
    private String company;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getCid() {
      return cid;
    }

    public void setCid(String cid) {
      this.cid = cid;
    }

    public String getWork() { return work;}

    public void setWork(String work) { this.work = work;}

    public String getTreatment() { return treatment;}

    public void setTreatment(String treatment) { this.treatment = treatment;}

    public String getObligation() { return obligation;}

    public void setObligation(String obligation) { this.obligation = obligation;}

    public String getCriteria() { return criteria;}

    public void setCriteria(String criteria) { this.criteria = criteria;}

    public String getWorkplace() { return workplace;}

    public void setWorkplace(String workplace) { this.workplace = workplace;}

    public String getAddress() { return address;}

    public void setAddress(String address) { this.address = address;}

    public String getTel() { return tel;}

    public void setTel(String tel) { this.tel = tel;}

    public String getWorktype() { return worktype;}

    public void setWorktype(String worktype) { this.worktype = worktype;}

    public String getCompany() { return company;}

    public void setCompany(String company) { this.company = company;}
  }
}
