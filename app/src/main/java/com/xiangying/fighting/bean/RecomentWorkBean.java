/*
 * RecomentWorkBean     2016/12/27 11:39
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/27 11:39
 */
public class RecomentWorkBean {

  /**
   * code : 1
   * message : 操作成功
   * data : [{"name":"热门职位","list":[{"id":"9","name":"设计师","tid":"1"},{"id":"10","name":"销售","tid":"1"},{"id":"11","name":"客服","tid":"1"}]},{"name":"广告出版","list":[{"id":"12","name":"广告策划","tid":"2"},{"id":"13","name":"媒介管理","tid":"2"},{"id":"14","name":"门牌设计","tid":"2"}]},{"name":"IT/通信","list":null}]
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
     * name : 热门职位
     * list : [{"id":"9","name":"设计师","tid":"1"},{"id":"10","name":"销售","tid":"1"},{"id":"11","name":"客服","tid":"1"}]
     */
    private String name;
    private List<ListBean> list;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public List<ListBean> getList() { return list;}

    public void setList(List<ListBean> list) { this.list = list;}

    public static class ListBean {
      /**
       * id : 9
       * name : 设计师
       * tid : 1
       */

      private String id;
      private String name;
      private String tid;

      public String getId() { return id;}

      public void setId(String id) { this.id = id;}

      public String getName() { return name;}

      public void setName(String name) { this.name = name;}

      public String getTid() { return tid;}

      public void setTid(String tid) { this.tid = tid;}
    }
  }
}
