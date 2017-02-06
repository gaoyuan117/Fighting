package com.xiangying.fighting.ui.first.talks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * Created by admin on 2017/1/22.
 */

public class GroupUsers implements Parcelable{

    /**
     * code : 1
     * message : 操作成功
     * data : [{"id":"5","gid":"6104551587841","is_manager":"1","username":"18954066795","groupid":"0","rename":"","nickname":"1","headimg":"","isOnline":"online"},{"id":"6","gid":"6104551587841","is_manager":"0","username":"13220122946","groupid":"0","rename":"","nickname":"哈哈","headimg":"","isOnline":"offline"},{"id":"7","gid":"6104551587841","is_manager":"0","username":"13240094302","groupid":"0","rename":"","nickname":"13240094302","headimg":"","isOnline":"offline"},{"id":"8","gid":"6104551587841","is_manager":"0","username":"14763766689","groupid":"0","rename":"啦啦啦","nickname":"啦啦啦","headimg":"","isOnline":"offline"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    protected GroupUsers(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<GroupUsers> CREATOR = new Creator<GroupUsers>() {
        @Override
        public GroupUsers createFromParcel(Parcel in) {
            return new GroupUsers(in);
        }

        @Override
        public GroupUsers[] newArray(int size) {
            return new GroupUsers[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

    public static class DataBean implements Parcelable{
        /**
         * id : 5
         * gid : 6104551587841
         * is_manager : 1
         * username : 18954066795
         * groupid : 0
         * rename :
         * nickname : 1
         * headimg :
         * isOnline : online
         */

        private String id;
        private String gid;
        private String is_manager;
        private String username;
        private String groupid;
        private String rename;
        private String nickname;
        private String headimg;
        private String isOnline;

        protected DataBean(Parcel in) {
            id = in.readString();
            gid = in.readString();
            is_manager = in.readString();
            username = in.readString();
            groupid = in.readString();
            rename = in.readString();
            nickname = in.readString();
            headimg = in.readString();
            isOnline = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getIs_manager() {
            return is_manager;
        }

        public void setIs_manager(String is_manager) {
            this.is_manager = is_manager;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getRename() {
            return rename;
        }

        public void setRename(String rename) {
            this.rename = rename;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(gid);
            dest.writeString(is_manager);
            dest.writeString(username);
            dest.writeString(groupid);
            dest.writeString(rename);
            dest.writeString(nickname);
            dest.writeString(headimg);
            dest.writeString(isOnline);
        }
    }
}
