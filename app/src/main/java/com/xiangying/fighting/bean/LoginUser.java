package com.xiangying.fighting.bean;

/**
 * Created by ray on 2016/8/25.
 * 当前登录用户
 */
public class LoginUser {
  public String nickname;
  public String username;//环信ID
  public String password;
  public String token;
  public HxEntity hx;
  public InfoEntity info;

  public LoginUser() {
  }

  public LoginUser(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public HxEntity getHx() {
    return hx;
  }

  public void setHx(HxEntity hx) {
    this.hx = hx;
  }

  public InfoEntity getInfo() {
    return info;
  }

  public void setInfo(InfoEntity info) {
    this.info = info;
  }


  public static class HxEntity {
    private String username;
    private String easemob;
    private String nickname;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getEasemob() {
      return easemob;
    }

    public void setEasemob(String easemob) {
      this.easemob = easemob;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }
  }

  public static class InfoEntity {
    private String uid;
    private String username;
    private String nickname;
    private String sex;
    private String qq;
    private Object email;
    private String cardid;
    private String region;
    private String idiograph;
    private Object avatar;
    private String birthday;
    private String score;
    private String level;
    private String last_login_ip;
    private String last_login_time;
    private String freeze_amount;
    private String amount;
    private String avatar_path;

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public String getSex() {
      return sex;
    }

    public void setSex(String sex) {
      this.sex = sex;
    }

    public String getQq() {
      return qq;
    }

    public void setQq(String qq) {
      this.qq = qq;
    }

    public Object getEmail() {
      return email;
    }

    public void setEmail(Object email) {
      this.email = email;
    }

    public String getCardid() {
      return cardid;
    }

    public void setCardid(String cardid) {
      this.cardid = cardid;
    }

    public String getRegion() {
      return region;
    }

    public void setRegion(String region) {
      this.region = region;
    }

    public String getIdiograph() {
      return idiograph;
    }

    public void setIdiograph(String idiograph) {
      this.idiograph = idiograph;
    }

    public Object getAvatar() {
      return avatar;
    }

    public void setAvatar(Object avatar) {
      this.avatar = avatar;
    }

    public String getBirthday() {
      return birthday;
    }

    public void setBirthday(String birthday) {
      this.birthday = birthday;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getLevel() {
      return level;
    }

    public void setLevel(String level) {
      this.level = level;
    }

    public String getLast_login_ip() {
      return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
      this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
      return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
      this.last_login_time = last_login_time;
    }

    public String getFreeze_amount() {
      return freeze_amount;
    }

    public void setFreeze_amount(String freeze_amount) {
      this.freeze_amount = freeze_amount;
    }

    public String getAmount() {
      return amount;
    }

    public void setAmount(String amount) {
      this.amount = amount;
    }

    public String getAvatar_path() {
      return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
      this.avatar_path = avatar_path;
    }
  }
}
