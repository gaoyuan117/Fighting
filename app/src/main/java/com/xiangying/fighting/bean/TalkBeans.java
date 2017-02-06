package com.xiangying.fighting.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TalkBeans implements Parcelable{
    public static final int TYPE_USER=1;
    public static final int TYPE_GROUP=2;

    public static final String [] Pics=new String[]{
            "http://t2.27270.com/uploads/tu/201606/31/uoiednduejj.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/s3ea3guysy3.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/k1guehmw24k.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/5xiru3asidk.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/iawoez0x0cg.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/xt1gcnvxkj2.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/pmypfd5ojwi.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/uurjpdtjmpj.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/21f0so1xeys.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/oyd1dtnnt4u.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/2gx1pdv5tlz.jpg",
            "http://t2.27270.com/uploads/tu/201606/31/rt0vaum1s2c.jpg",
    };
    public static final String[] Names=new String[]{
            "花落无声","任真天","Svip^露宝","我能遨游书海~",
            "NO,NO,NO","露露","月光族","邪魅-宝宝",
            "一夫多妻制","你若化成风","滴血玫瑰","苦笑^倾城",
    };

    private int sex;
    private String qq;
    private String email;
    private String cardid;
    private String region;
    private String idiograph;
    private String birthday;
    private String score;
    private String level;
    private String avatar_path;
    private boolean is_friend;
    private int uid;
    private String username,nickname;
    private String name,state="在线",avatar,job,content;
    private int type=TYPE_USER;
    private String  id;
    private Long messagetime;
    private String login;
    private String reg_time;
    private String freeze_amount;
    private String amount;
    private String referrer;
    private String sign;
    private String district;
    private String zd_id;
    private String headimg;
    private String status;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
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

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZd_id() {
        return zd_id;
    }

    public void setZd_id(String zd_id) {
        this.zd_id = zd_id;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public TalkBeans(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public TalkBeans(String name, String avatar,String id) {
        this.name = name;
        this.avatar = avatar;
        this.id=id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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

    public Long getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(Long messagetime) {
        this.messagetime = messagetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public boolean getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sex);
        dest.writeString(this.qq);
        dest.writeString(this.email);
        dest.writeString(this.cardid);
        dest.writeString(this.region);
        dest.writeString(this.idiograph);
        dest.writeString(this.birthday);
        dest.writeString(this.score);
        dest.writeString(this.level);
        dest.writeString(this.avatar_path);
        dest.writeByte(this.is_friend ? (byte) 1 : (byte) 0);
        dest.writeInt(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.nickname);
        dest.writeString(this.name);
        dest.writeString(this.state);
        dest.writeString(this.avatar);
        dest.writeString(this.job);
        dest.writeString(this.content);
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeValue(this.messagetime);
        dest.writeString(this.login);
        dest.writeString(this.reg_time);
        dest.writeString(this.freeze_amount);
        dest.writeString(this.amount);
        dest.writeString(this.referrer);
        dest.writeString(this.sign);
        dest.writeString(this.district);
        dest.writeString(this.zd_id);
        dest.writeString(this.headimg);
        dest.writeString(this.status);
    }

    protected TalkBeans(Parcel in) {
        this.sex = in.readInt();
        this.qq = in.readString();
        this.email = in.readString();
        this.cardid = in.readString();
        this.region = in.readString();
        this.idiograph = in.readString();
        this.birthday = in.readString();
        this.score = in.readString();
        this.level = in.readString();
        this.avatar_path = in.readString();
        this.is_friend = in.readByte() != 0;
        this.uid = in.readInt();
        this.username = in.readString();
        this.nickname = in.readString();
        this.name = in.readString();
        this.state = in.readString();
        this.avatar = in.readString();
        this.job = in.readString();
        this.content = in.readString();
        this.type = in.readInt();
        this.id = in.readString();
        this.messagetime = (Long) in.readValue(Long.class.getClassLoader());
        this.login = in.readString();
        this.reg_time = in.readString();
        this.freeze_amount = in.readString();
        this.amount = in.readString();
        this.referrer = in.readString();
        this.sign = in.readString();
        this.district = in.readString();
        this.zd_id = in.readString();
        this.headimg = in.readString();
        this.status = in.readString();
    }

    public static final Creator<TalkBeans> CREATOR = new Creator<TalkBeans>() {
        @Override public TalkBeans createFromParcel(Parcel source) {return new TalkBeans(source);}

        @Override public TalkBeans[] newArray(int size) {return new TalkBeans[size];}
    };
}
