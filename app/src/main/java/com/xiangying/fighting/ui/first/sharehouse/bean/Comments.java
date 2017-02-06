package com.xiangying.fighting.ui.first.sharehouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
public class Comments implements Parcelable {//评论
  private ArrayList<Replies> replies;//回复
  private int id;
  private int uid;
  private String avatar;
  private String content;
  private String nickname;
  private String commentTime;

  public String getCommentTime() {
    return commentTime;
  }

  public void setCommentTime(String commentTime) {
    this.commentTime = commentTime;
  }

  public ArrayList<Replies> getReplies() {
    return replies;
  }

  public void setReplies(ArrayList<Replies> replies) {
    this.replies = replies;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Comments() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(replies);
    dest.writeInt(this.id);
    dest.writeInt(this.uid);
    dest.writeString(this.avatar);
    dest.writeString(this.content);
    dest.writeString(this.nickname);
    dest.writeString(this.commentTime);
  }

  protected Comments(Parcel in) {
    this.replies = in.createTypedArrayList(Replies.CREATOR);
    this.id = in.readInt();
    this.uid = in.readInt();
    this.avatar = in.readString();
    this.content = in.readString();
    this.nickname = in.readString();
    this.commentTime = in.readString();
  }

  public static final Creator<Comments> CREATOR = new Creator<Comments>() {
    public Comments createFromParcel(Parcel source) {
      return new Comments(source);
    }

    public Comments[] newArray(int size) {
      return new Comments[size];
    }
  };
}
