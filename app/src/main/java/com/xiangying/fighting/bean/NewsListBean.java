/*
 * NewsListBean     2016/12/12 12:55
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/12 12:55
 */
public class NewsListBean {


  /**
   * code : 1
   * message : 操作成功
   * data : {"total":12,"result":[{"id":"25","title":"个asdgas","content":"<p>&nbsp;广东省高阿什斗鬼矮冬瓜<\/p>","is_hot":"0","create_time":"1481511122","update_time":"1481511122","type_id":"2","cover_id":"0","movie_id":"5","imgPath":null,"moviePath":"/Uploads/Movie/2016-12-08/584927159d981.qlv"},{"id":"17","title":"大概瓦儿歌","content":"<p>&nbsp;噶速度个<\/p>","is_hot":"0","create_time":"1481510380","update_time":"1481510380","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"16","title":"etw大丰港","content":"<p>噶啥多干 干撒个<\/p>","is_hot":"0","create_time":"1481510370","update_time":"1481510370","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"15","title":"dgsdg","content":"<p>dgewtet<\/p>","is_hot":"0","create_time":"1481510356","update_time":"1481510356","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"14","title":"dertwet","content":"<p>adgasdfgas<\/p>","is_hot":"0","create_time":"1481510345","update_time":"1481510345","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"13","title":"dgasdg ","content":"<p>gasdg ads<\/p>","is_hot":"0","create_time":"1481510334","update_time":"1481510334","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"12","title":"this is a test","content":"<p>this is a test<\/p>","is_hot":"0","create_time":"1481510047","update_time":"1481510047","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"11","title":"gasdga","content":"<p>打噶啥多干<\/p>","is_hot":"0","create_time":"1481510025","update_time":"1481510025","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"10","title":"公司","content":"<p>干撒个<\/p>","is_hot":"0","create_time":"1481509928","update_time":"1481509928","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"9","title":"发送到广东省高","content":"<p>鬼打鬼的故事大概a&nbsp;<\/p>","is_hot":"0","create_time":"1481509852","update_time":"1481509852","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null}]}
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
     * total : 12
     * result : [{"id":"25","title":"个asdgas","content":"<p>&nbsp;广东省高阿什斗鬼矮冬瓜<\/p>","is_hot":"0","create_time":"1481511122","update_time":"1481511122","type_id":"2","cover_id":"0","movie_id":"5","imgPath":null,"moviePath":"/Uploads/Movie/2016-12-08/584927159d981.qlv"},{"id":"17","title":"大概瓦儿歌","content":"<p>&nbsp;噶速度个<\/p>","is_hot":"0","create_time":"1481510380","update_time":"1481510380","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"16","title":"etw大丰港","content":"<p>噶啥多干 干撒个<\/p>","is_hot":"0","create_time":"1481510370","update_time":"1481510370","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"15","title":"dgsdg","content":"<p>dgewtet<\/p>","is_hot":"0","create_time":"1481510356","update_time":"1481510356","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"14","title":"dertwet","content":"<p>adgasdfgas<\/p>","is_hot":"0","create_time":"1481510345","update_time":"1481510345","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"13","title":"dgasdg ","content":"<p>gasdg ads<\/p>","is_hot":"0","create_time":"1481510334","update_time":"1481510334","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"12","title":"this is a test","content":"<p>this is a test<\/p>","is_hot":"0","create_time":"1481510047","update_time":"1481510047","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"11","title":"gasdga","content":"<p>打噶啥多干<\/p>","is_hot":"0","create_time":"1481510025","update_time":"1481510025","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"10","title":"公司","content":"<p>干撒个<\/p>","is_hot":"0","create_time":"1481509928","update_time":"1481509928","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null},{"id":"9","title":"发送到广东省高","content":"<p>鬼打鬼的故事大概a&nbsp;<\/p>","is_hot":"0","create_time":"1481509852","update_time":"1481509852","type_id":"2","cover_id":"1022","movie_id":"0","imgPath":"/Uploads/Picture/2016-12-08/58490c5b3a791.jpg","moviePath":null}]
     */

    private int total;
    private List<Result> result;

    public int getTotal() { return total;}

    public void setTotal(int total) { this.total = total;}

    public List<Result> getResult() { return result;}

    public void setResult(List<Result> result) { this.result = result;}

    public static class Result {
      /**
       * id : 25
       * title : 个asdgas
       * content : <p>&nbsp;广东省高阿什斗鬼矮冬瓜</p>
       * is_hot : 0
       * create_time : 1481511122
       * update_time : 1481511122
       * type_id : 2
       * cover_id : 0
       * movie_id : 5
       * imgPath : null
       * moviePath : /Uploads/Movie/2016-12-08/584927159d981.qlv
       */

      private String id;
      private String title;
      private String content;
      private String is_hot;
      private long create_time;
      private long update_time;
      private String type_id;
      private String cover_id;
      private String movie_id;
      private String imgPath;
      private String moviePath;

      public String getId() { return id;}

      public void setId(String id) { this.id = id;}

      public String getTitle() { return title;}

      public void setTitle(String title) { this.title = title;}

      public String getContent() { return content;}

      public void setContent(String content) { this.content = content;}

      public String getIs_hot() { return is_hot;}

      public void setIs_hot(String is_hot) { this.is_hot = is_hot;}

      public long getCreate_time() { return create_time;}

      public void setCreate_time(long create_time) { this.create_time = create_time;}

      public long getUpdate_time() { return update_time;}

      public void setUpdate_time(long update_time) { this.update_time = update_time;}

      public String getType_id() { return type_id;}

      public void setType_id(String type_id) { this.type_id = type_id;}

      public String getCover_id() { return cover_id;}

      public void setCover_id(String cover_id) { this.cover_id = cover_id;}

      public String getMovie_id() { return movie_id;}

      public void setMovie_id(String movie_id) { this.movie_id = movie_id;}

      public String getImgPath() { return imgPath;}

      public void setImgPath(String imgPath) { this.imgPath = imgPath;}

      public String getMoviePath() { return moviePath;}

      public void setMoviePath(String moviePath) { this.moviePath = moviePath;}
    }
  }
}
