package com.xiangying.fighting.ui.two.findjob.bean;

import java.util.ArrayList;

/**
 * Created by xiaoniao on 2016/9/13.
 */
public class JobListBean {

  /**
   * code : 1
   * message : 操作成功
   * data : [{"id":"29","cid":"107","work":"明","company":"攻击力","time":"今天","treatment":"8000元以上","workplace":"北京北京"}]
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
     * id : 29
     * cid : 107
     * work : 明
     * company : 攻击力
     * time : 今天
     * treatment : 8000元以上
     * workplace : 北京北京
     */

    private String id;
    private String cid;
    private String work;
    private String company;
    private String time;
    private String treatment;
    private String workplace;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getCid() { return cid;}

    public void setCid(String cid) { this.cid = cid;}

    public String getWork() { return work;}

    public void setWork(String work) { this.work = work;}

    public String getCompany() { return company;}

    public void setCompany(String company) { this.company = company;}

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public String getTreatment() { return treatment;}

    public void setTreatment(String treatment) { this.treatment = treatment;}

    public String getWorkplace() { return workplace;}

    public void setWorkplace(String workplace) { this.workplace = workplace;}
  }
}
