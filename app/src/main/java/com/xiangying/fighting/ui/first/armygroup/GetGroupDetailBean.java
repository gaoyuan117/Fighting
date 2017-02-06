package com.xiangying.fighting.ui.first.armygroup;

/**
 * Created by admin on 2017/1/24.
 */

public class GetGroupDetailBean {

    /**
     * code : 1
     * message : 操作成功
     * data : {"groupname":"啊啊啊啊","nickname":"1"}
     */

    private int code;
    private String message;
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
         * groupname : 啊啊啊啊
         * nickname : 1
         */

        private String groupname;
        private String nickname;
        private String rename;

        public String getRename() {
            return rename;
        }

        public void setRename(String rename) {
            this.rename = rename;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
