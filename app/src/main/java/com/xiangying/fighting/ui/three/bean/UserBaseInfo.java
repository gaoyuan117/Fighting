package com.xiangying.fighting.ui.three.bean;

import java.io.Serializable;

/**
 * Created by xiaoniao on 2016/9/13.
 */
public class UserBaseInfo implements Serializable{
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
         * nickname : 18954066795
         * sex : 1
         * birthday : 0000-00-00
         * qq :
         * score : 0
         * login : 134
         * reg_ip : 3232235891
         * reg_time : 1479284545
         * last_login_ip : 3232235876
         * last_login_time : 1481680726
         * status : 1
         * avatar : 1026
         * freeze_amount : 0.00
         * amount : null
         * level : 0
         * referrer : 0
         * sign : this is a test
         * district : 北京东城
         * zd_id : 21192321
         * avatar_path : /Uploads/Picture/2016-12-09/584a446439533.jpg
         * username : 18954066795
         * email : null
         */
        private String uid;
        private String nickname;
        private String sex;
        private String birthday;
        private String qq;
        private String score;
        private String login;
        private String reg_ip;
        private String reg_time;
        private String last_login_ip;
        private String last_login_time;
        private String status;
        private String avatar;
        private String freeze_amount;
        private String amount;
        private String level;
        private String referrer;
        private String sign;
        private String district;
        private String zd_id;
        private String avatar_path;
        private String username;
        private Object email;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() { return nickname;}

        public void setNickname(String nickname) { this.nickname = nickname;}

        public String getSex() { return sex;}

        public void setSex(String sex) { this.sex = sex;}

        public String getBirthday() { return birthday;}

        public void setBirthday(String birthday) { this.birthday = birthday;}

        public String getQq() { return qq;}

        public void setQq(String qq) { this.qq = qq;}

        public String getScore() { return score;}

        public void setScore(String score) { this.score = score;}

        public String getLogin() { return login;}

        public void setLogin(String login) { this.login = login;}

        public String getReg_ip() { return reg_ip;}

        public void setReg_ip(String reg_ip) { this.reg_ip = reg_ip;}

        public String getReg_time() { return reg_time;}

        public void setReg_time(String reg_time) { this.reg_time = reg_time;}

        public String getLast_login_ip() { return last_login_ip;}

        public void setLast_login_ip(String last_login_ip) { this.last_login_ip = last_login_ip;}

        public String getLast_login_time() { return last_login_time;}

        public void setLast_login_time(String last_login_time) { this.last_login_time = last_login_time;}

        public String getStatus() { return status;}

        public void setStatus(String status) { this.status = status;}

        public String getAvatar() { return avatar;}

        public void setAvatar(String avatar) { this.avatar = avatar;}

        public String getFreeze_amount() { return freeze_amount;}

        public void setFreeze_amount(String freeze_amount) { this.freeze_amount = freeze_amount;}

        public String getAmount() { return amount;}

        public void setAmount(String amount) { this.amount = amount;}

        public String getLevel() { return level;}

        public void setLevel(String level) { this.level = level;}

        public String getReferrer() { return referrer;}

        public void setReferrer(String referrer) { this.referrer = referrer;}

        public String getSign() { return sign;}

        public void setSign(String sign) { this.sign = sign;}

        public String getDistrict() { return district;}

        public void setDistrict(String district) { this.district = district;}

        public String getZd_id() { return zd_id;}

        public void setZd_id(String zd_id) { this.zd_id = zd_id;}

        public String getAvatar_path() { return avatar_path;}

        public void setAvatar_path(String avatar_path) { this.avatar_path = avatar_path;}

        public String getUsername() { return username;}

        public void setUsername(String username) { this.username = username;}

        public Object getEmail() { return email;}

        public void setEmail(Object email) { this.email = email;}
    }
}
