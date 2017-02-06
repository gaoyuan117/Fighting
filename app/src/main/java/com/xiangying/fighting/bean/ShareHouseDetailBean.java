/*
 * ShareHouseDetailBean     2016/12/26 16:23
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/26 16:23
 */
public class ShareHouseDetailBean {

  /**
   * code : 1
   * message : 操作成功
   * data : {"energy":{"id":"152","uid":"14","content":"还查查哈还拜拜吃吧还查查啊哈哈彼此才差劲","cover_ids":"","movie_id":"25","create_time":"1482721506","is_energy":"1","times":0,"headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈","comment":[{"id":"98","e_id":"152","z_id":"14","is_read":"0","z_time":"1482733284","text":"哦名字自己我","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"},{"id":"99","e_id":"152","z_id":"14","is_read":"0","z_time":"1482736599","text":"黑曼巴也可以","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"},{"id":"100","e_id":"152","z_id":"13","is_read":"0","z_time":"1482737095","text":"老K咯哦哦","headimg":"/Uploads/Picture/2016-12-24/585e167cd3862.png","username":"嘿嘿"},{"id":"104","e_id":"152","z_id":"14","is_read":"0","z_time":"1482737882","text":"红米我以为","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"}]}}
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
     * energy : {"id":"152","uid":"14","content":"还查查哈还拜拜吃吧还查查啊哈哈彼此才差劲","cover_ids":"","movie_id":"25","create_time":"1482721506","is_energy":"1","times":0,"headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈","comment":[{"id":"98","e_id":"152","z_id":"14","is_read":"0","z_time":"1482733284","text":"哦名字自己我","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"},{"id":"99","e_id":"152","z_id":"14","is_read":"0","z_time":"1482736599","text":"黑曼巴也可以","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"},{"id":"100","e_id":"152","z_id":"13","is_read":"0","z_time":"1482737095","text":"老K咯哦哦","headimg":"/Uploads/Picture/2016-12-24/585e167cd3862.png","username":"嘿嘿"},{"id":"104","e_id":"152","z_id":"14","is_read":"0","z_time":"1482737882","text":"红米我以为","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"}]}
     */

    private Energy energy;

    public Energy getEnergy() { return energy;}

    public void setEnergy(Energy energy) { this.energy = energy;}

    public static class Energy {
      /**
       * id : 152
       * uid : 14
       * content : 还查查哈还拜拜吃吧还查查啊哈哈彼此才差劲
       * cover_ids :
       * movie_id : 25
       * create_time : 1482721506
       * is_energy : 1
       * times : 0
       * headimg : /Uploads/Picture/2016-12-26/5860739b0345a.jpg
       * username : 哈哈哈
       * comment : [{"id":"98","e_id":"152","z_id":"14","is_read":"0","z_time":"1482733284","text":"哦名字自己我","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"},{"id":"99","e_id":"152","z_id":"14","is_read":"0","z_time":"1482736599","text":"黑曼巴也可以","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"},{"id":"100","e_id":"152","z_id":"13","is_read":"0","z_time":"1482737095","text":"老K咯哦哦","headimg":"/Uploads/Picture/2016-12-24/585e167cd3862.png","username":"嘿嘿"},{"id":"104","e_id":"152","z_id":"14","is_read":"0","z_time":"1482737882","text":"红米我以为","headimg":"/Uploads/Picture/2016-12-26/5860739b0345a.jpg","username":"哈哈哈"}]
       */

      private String id;
      private String uid;
      private String content;
      private String cover_ids;
      private String movie_id;
      private String create_time;
      private String is_energy;
      private int times;
      private String headimg;
      private String username;
      private List<Comment> comment;

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

      public int getTimes() { return times;}

      public void setTimes(int times) { this.times = times;}

      public String getHeadimg() { return headimg;}

      public void setHeadimg(String headimg) { this.headimg = headimg;}

      public String getUsername() { return username;}

      public void setUsername(String username) { this.username = username;}

      public List<Comment> getComment() { return comment;}

      public void setComment(List<Comment> comment) { this.comment = comment;}

      public static class Comment {
        /**
         * id : 98
         * e_id : 152
         * z_id : 14
         * is_read : 0
         * z_time : 1482733284
         * text : 哦名字自己我
         * headimg : /Uploads/Picture/2016-12-26/5860739b0345a.jpg
         * username : 哈哈哈
         */

        private String id;
        private String e_id;
        private String z_id;
        private String is_read;
        private String z_time;
        private String text;
        private String headimg;
        private String username;

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

        public String getHeadimg() { return headimg;}

        public void setHeadimg(String headimg) { this.headimg = headimg;}

        public String getUsername() { return username;}

        public void setUsername(String username) { this.username = username;}
      }
    }
  }
}
