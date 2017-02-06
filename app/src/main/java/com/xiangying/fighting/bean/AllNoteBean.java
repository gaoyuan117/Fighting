package com.xiangying.fighting.bean;

import java.util.List;

/**
 * Created by admin on 2017/1/23.
 */

public class AllNoteBean {

    /**
     * code : 1
     * message : 操作成功
     * data : [{"id":"1","uid":"2","content":"test1","movie_id":"0","create_time":"1484992037","cover_ids":"1128,1127","imgpath":["/Uploads/Picture/2017-01-19/58802a657512c.png","/Uploads/Picture/2017-01-19/58802a49e8cbc.jpg"],"moviePath":null}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * uid : 2
         * content : test1
         * movie_id : 0
         * create_time : 1484992037
         * cover_ids : 1128,1127
         * imgpath : ["/Uploads/Picture/2017-01-19/58802a657512c.png","/Uploads/Picture/2017-01-19/58802a49e8cbc.jpg"]
         * moviePath : null
         */

        private String id;
        private String uid;
        private String content;
        private String movie_id;
        private long create_time;
        private String cover_ids;
        private String moviePath;
        private List<String> imgpath;
        private int type;
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMovie_id() {
            return movie_id;
        }

        public void setMovie_id(String movie_id) {
            this.movie_id = movie_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getCover_ids() {
            return cover_ids;
        }

        public void setCover_ids(String cover_ids) {
            this.cover_ids = cover_ids;
        }

        public String getMoviePath() {
            return moviePath;
        }

        public void setMoviePath(String moviePath) {
            this.moviePath = moviePath;
        }

        public List<String> getImgpath() {
            return imgpath;
        }

        public void setImgpath(List<String> imgpath) {
            this.imgpath = imgpath;
        }
    }
}
