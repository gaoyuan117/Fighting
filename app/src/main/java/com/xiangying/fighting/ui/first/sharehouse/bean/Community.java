package com.xiangying.fighting.ui.first.sharehouse.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;

public class Community implements Parcelable {

    public static final int COMMU_TYPE_COMMU=0;
    public static final int COMMU_TYPE_BOOK=2;
    /**
     * 书友圈类型一 ：用户自己发表
     */
    public static String TOPIC_TYPE_ONE = "TOPIC_TYPE_ONE";

    /**
     * 书友圈类型二：添加图书 系统自动发表
     */
    public static String TOPIC_TYPE_TWO = "TOPIC_TYPE_TWO";

    private int id ;
    private int uid;
    private double latitude;
    private double longitude;
    private String content;
    private String pushTime;
    private String title;
    private int praiseAmount;//赞
    private int commentAmount;//评论
    private String image=null;
    private int isPraise;
    private String avatar;
    private String nickname;
    private int pages=-1;
    private int bookid;
    private int bookuid;
    private String location;
    private Bitmap pic;
    private boolean isPicOver=false;
    private String gender;//性别
    private String Rating;//图书星级评分
    private int type;
    private ArrayList<Comments> comments;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isPicOver() {
        return isPicOver;
    }

    public void setIsPicOver(boolean isPicOver) {
        this.isPicOver = isPicOver;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getBookuid() {
        return bookuid;
    }

    public void setBookuid(int bookuid) {
        this.bookuid = bookuid;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(int praiseAmount) {
        this.praiseAmount = praiseAmount;
    }

    public int getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }


    public Community() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.uid);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.content);
        dest.writeString(this.pushTime);
        dest.writeString(this.title);
        dest.writeInt(this.praiseAmount);
        dest.writeInt(this.commentAmount);
        dest.writeString(this.image);
        dest.writeInt(this.isPraise);
        dest.writeString(this.avatar);
        dest.writeString(this.nickname);
        dest.writeInt(this.pages);
        dest.writeInt(this.bookid);
        dest.writeInt(this.bookuid);
        dest.writeString(this.location);
        dest.writeParcelable(this.pic, 0);
        dest.writeByte(isPicOver ? (byte) 1 : (byte) 0);
        dest.writeString(this.gender);
        dest.writeString(this.Rating);
        dest.writeInt(this.type);
        dest.writeTypedList(comments);
    }

    protected Community(Parcel in) {
        this.id = in.readInt();
        this.uid = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.content = in.readString();
        this.pushTime = in.readString();
        this.title = in.readString();
        this.praiseAmount = in.readInt();
        this.commentAmount = in.readInt();
        this.image = in.readString();
        this.isPraise = in.readInt();
        this.avatar = in.readString();
        this.nickname = in.readString();
        this.pages = in.readInt();
        this.bookid = in.readInt();
        this.bookuid = in.readInt();
        this.location = in.readString();
        this.pic = in.readParcelable(Bitmap.class.getClassLoader());
        this.isPicOver = in.readByte() != 0;
        this.gender = in.readString();
        this.Rating = in.readString();
        this.type = in.readInt();
        this.comments = in.createTypedArrayList(Comments.CREATOR);
    }

    public static final Creator<Community> CREATOR = new Creator<Community>() {
        public Community createFromParcel(Parcel source) {
            return new Community(source);
        }

        public Community[] newArray(int size) {
            return new Community[size];
        }
    };
}
