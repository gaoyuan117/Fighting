package com.xiangying.fighting.ui.two.findrent.bean;

import java.util.ArrayList;

/**
 * Created by xiaoniao on 2016/9/18.
 */
public class RentDetailBean {
    private int code;
    private String message;
    /**
     * uid : 38
     * username : 17775526994
     * nickname : 17775526994
     * sex : 0
     * qq :
     * email :
     * cardid : ZDH17775526994
     * region :
     * idiograph :
     * avatar : null
     * birthday : 0000-00-00
     * score : 10
     * level : 0
     * last_login_ip : 2883440236
     * last_login_time : 1473737537
     * freeze_amount : 0.00
     * amount : 0.00
     * avatar_path :
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * id : 49
         * uid : 36
         * litpic : 5,8,9
         * title : 舒适房屋出租
         * price : 500.00
         * hall_room : 一室一厅
         * area : 50
         * floor : 5楼
         * height : 8楼
         * qu : 所在地区
         * qu_name : 所在小区名字
         * qu_site : 所在小区地址
         * description : 美丽的小区，家电齐全
         * type : 0
         * time : 1473310441
         * cardid : ZDH18179323942
         * username : 18179323942
         */

        private String id;
        private String uid;
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
        private long time;
        private String cardid;
        private String username;
        private ArrayList<String> imgPath;

        public ArrayList<String> getImgPath() {
            return imgPath;
        }

        public void setImgPath(ArrayList<String> imgPath) {
            this.imgPath = imgPath;
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

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHall_room() {
            return hall_room;
        }

        public void setHall_room(String hall_room) {
            this.hall_room = hall_room;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getQu() {
            return qu;
        }

        public void setQu(String qu) {
            this.qu = qu;
        }

        public String getQu_name() {
            return qu_name;
        }

        public void setQu_name(String qu_name) {
            this.qu_name = qu_name;
        }

        public String getQu_site() {
            return qu_site;
        }

        public void setQu_site(String qu_site) {
            this.qu_site = qu_site;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    }
