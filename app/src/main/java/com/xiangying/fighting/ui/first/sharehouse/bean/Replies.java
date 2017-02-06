package com.xiangying.fighting.ui.first.sharehouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/16.
 */
public class Replies implements Parcelable {//回复类
    private int id;
    private int uid;
    private String avatar;
    private String content;
    private String nickname;

    private int replyUid=-1;
    private String replyAvatar;
    private String replyNickname;
    private String replyTimeTime;

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

    public int getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(int replyUid) {
        this.replyUid = replyUid;
    }

    public String getReplyAvatar() {
        return replyAvatar;
    }

    public void setReplyAvatar(String replyAvatar) {
        this.replyAvatar = replyAvatar;
    }

    public String getReplyNickname() {
        return replyNickname;
    }

    public void setReplyNickname(String replyNickname) {
        this.replyNickname = replyNickname;
    }

    public String getReplyTimeTime() {
        return replyTimeTime;
    }

    public void setReplyTimeTime(String replyTimeTime) {
        this.replyTimeTime = replyTimeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.uid);
        dest.writeString(this.avatar);
        dest.writeString(this.content);
        dest.writeString(this.nickname);
        dest.writeInt(this.replyUid);
        dest.writeString(this.replyAvatar);
        dest.writeString(this.replyNickname);
        dest.writeString(this.replyTimeTime);
    }

    public Replies() {
    }

    protected Replies(Parcel in) {
        this.id = in.readInt();
        this.uid = in.readInt();
        this.avatar = in.readString();
        this.content = in.readString();
        this.nickname = in.readString();
        this.replyUid = in.readInt();
        this.replyAvatar = in.readString();
        this.replyNickname = in.readString();
        this.replyTimeTime = in.readString();
    }

    public static final Creator<Replies> CREATOR = new Creator<Replies>() {
        public Replies createFromParcel(Parcel source) {
            return new Replies(source);
        }

        public Replies[] newArray(int size) {
            return new Replies[size];
        }
    };
}