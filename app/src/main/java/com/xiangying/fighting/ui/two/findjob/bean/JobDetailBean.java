package com.xiangying.fighting.ui.two.findjob.bean;

/**
 * Created by xiaoniao on 2016/9/14.
 */
public class JobDetailBean {


  /**
   * code : 1
   * message : 操作成功
   * data : {"id":"29","cid":"107","work":"明","treatment":"8000元以上","obligation":"94946466464664","criteria":"我你你你","time":"今天","workplace":"北京北京","address":"破哦你","tel":"1594946","worktype":"UI设计师"}
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

  public static class Data {
    /**
     * id : 29
     * cid : 107
     * work : 明
     * treatment : 8000元以上
     * obligation : 94946466464664
     * criteria : 我你你你
     * time : 今天
     * workplace : 北京北京
     * address : 破哦你
     * tel : 1594946
     * worktype : UI设计师
     */

    private String id;
    private String cid;
    private String work;
    private String treatment;
    private String obligation;
    private String criteria;
    private String time;
    private String workplace;
    private String address;
    private String tel;
    private String company;
    private String ctel;
    private String phone;
    private String name;
    private String site ;
    private String info;
    private String worktype;

    public String getCompany() {
      return company;
    }

    public void setCompany(String company) {
      this.company = company;
    }

    public String getCtel() {
      return ctel;
    }

    public void setCtel(String ctel) {
      this.ctel = ctel;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSite() {
      return site;
    }

    public void setSite(String site) {
      this.site = site;
    }

    public String getInfo() {
      return info;
    }

    public void setInfo(String info) {
      this.info = info;
    }

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getCid() { return cid;}

    public void setCid(String cid) { this.cid = cid;}

    public String getWork() { return work;}

    public void setWork(String work) { this.work = work;}

    public String getTreatment() { return treatment;}

    public void setTreatment(String treatment) { this.treatment = treatment;}

    public String getObligation() { return obligation;}

    public void setObligation(String obligation) { this.obligation = obligation;}

    public String getCriteria() { return criteria;}

    public void setCriteria(String criteria) { this.criteria = criteria;}

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public String getWorkplace() { return workplace;}

    public void setWorkplace(String workplace) { this.workplace = workplace;}

    public String getAddress() { return address;}

    public void setAddress(String address) { this.address = address;}

    public String getTel() { return tel;}

    public void setTel(String tel) { this.tel = tel;}

    public String getWorktype() { return worktype;}

    public void setWorktype(String worktype) { this.worktype = worktype;}
  }
}
