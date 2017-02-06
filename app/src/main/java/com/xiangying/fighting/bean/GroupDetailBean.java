/*
 * GroupDetailBean     2016/12/23 17:20
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/23 17:20
 */
public class GroupDetailBean {

  /**
   * code : 1
   * message : 操作成功
   * data : {"action":"get","uri":"http://a1.easemob.com/1165161212178803/atest/chatgroups/277677870128562716","entities":[],"data":[{"membersonly":true,"allowinvites":false,"public":true,"name":"群组3","description":"this is a test","affiliations":[{"owner":"13240094301"},{"member":"13240094302"}],"id":"277677870128562716","maxusers":300,"affiliations_count":2}],"timestamp":1482218286500,"duration":7,"count":1}
   */
  private int code;
  private String message;
  private DataX data;

  public int getCode() { return code;}

  public void setCode(int code) { this.code = code;}

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}

  public DataX getData() { return data;}

  public void setData(DataX data) { this.data = data;}

  public static class DataX {
    /**
     * action : get
     * uri : http://a1.easemob.com/1165161212178803/atest/chatgroups/277677870128562716
     * entities : []
     * data : [{"membersonly":true,"allowinvites":false,"public":true,"name":"群组3","description":"this is a test","affiliations":[{"owner":"13240094301"},{"member":"13240094302"}],"id":"277677870128562716","maxusers":300,"affiliations_count":2}]
     * timestamp : 1482218286500
     * duration : 7
     * count : 1
     */

    private String action;
    private String uri;
    private long timestamp;
    private int duration;
    private int count;
    private List<?> entities;
    private Data data;

    public String getAction() { return action;}

    public void setAction(String action) { this.action = action;}

    public String getUri() { return uri;}

    public void setUri(String uri) { this.uri = uri;}

    public long getTimestamp() { return timestamp;}

    public void setTimestamp(long timestamp) { this.timestamp = timestamp;}

    public int getDuration() { return duration;}

    public void setDuration(int duration) { this.duration = duration;}

    public int getCount() { return count;}

    public void setCount(int count) { this.count = count;}

    public List<?> getEntities() { return entities;}

    public void setEntities(List<?> entities) { this.entities = entities;}

    public Data getData() { return data;}

    public void setData(Data data) { this.data = data;}

    public static class Data {
      /**
       * membersonly : true
       * allowinvites : false
       * public : true
       * name : 群组3
       * description : this is a test
       * affiliations : [{"owner":"13240094301"},{"member":"13240094302"}]
       * id : 277677870128562716
       * maxusers : 300
       * affiliations_count : 2
       */

      private boolean membersonly;
      private boolean allowinvites;
      @SerializedName("public")
      private boolean publicX;
      private String name;
      private String description;
      private String id;
      private int maxusers;
      private int affiliations_count;
      private List<Affiliations> affiliations;

      public boolean isMembersonly() { return membersonly;}

      public void setMembersonly(boolean membersonly) { this.membersonly = membersonly;}

      public boolean isAllowinvites() { return allowinvites;}

      public void setAllowinvites(boolean allowinvites) { this.allowinvites = allowinvites;}

      public boolean isPublicX() { return publicX;}

      public void setPublicX(boolean publicX) { this.publicX = publicX;}

      public String getName() { return name;}

      public void setName(String name) { this.name = name;}

      public String getDescription() { return description;}

      public void setDescription(String description) { this.description = description;}

      public String getId() { return id;}

      public void setId(String id) { this.id = id;}

      public int getMaxusers() { return maxusers;}

      public void setMaxusers(int maxusers) { this.maxusers = maxusers;}

      public int getAffiliations_count() { return affiliations_count;}

      public void setAffiliations_count(int affiliations_count) { this.affiliations_count = affiliations_count;}

      public List<Affiliations> getAffiliations() { return affiliations;}

      public void setAffiliations(List<Affiliations> affiliations) { this.affiliations = affiliations;}

      public static class Affiliations {
        /**
         * owner : 13240094301
         * member : 13240094302
         */

        private String owner;
        private String member;

        public String getOwner() { return owner;}

        public void setOwner(String owner) { this.owner = owner;}

        public String getMember() { return member;}

        public void setMember(String member) { this.member = member;}
      }
    }
  }
}
