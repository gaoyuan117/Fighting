package com.xiangying.fighting.ui.first.talks;

import java.util.List;

/**
 * Created by admin on 2017/1/24.
 */

public class CreatGroupBean {

    /**
     * code : 1
     * message : 操作成功
     * data : {"action":"post","application":"ffb28120-c03f-11e6-95a5-3fcf89acc14a","uri":"http://a1.easemob.com/1165161212178803/atest/chatgroups","entities":[],"data":{"groupid":"6139851898881"},"timestamp":1485253819119,"duration":0,"organization":"1165161212178803","applicationName":"atest"}
     */

    private int code;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * action : post
         * application : ffb28120-c03f-11e6-95a5-3fcf89acc14a
         * uri : http://a1.easemob.com/1165161212178803/atest/chatgroups
         * entities : []
         * data : {"groupid":"6139851898881"}
         * timestamp : 1485253819119
         * duration : 0
         * organization : 1165161212178803
         * applicationName : atest
         */

        private String action;
        private String application;
        private String uri;
        private DataBean data;
        private long timestamp;
        private int duration;
        private String organization;
        private String applicationName;
        private List<?> entities;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public List<?> getEntities() {
            return entities;
        }

        public void setEntities(List<?> entities) {
            this.entities = entities;
        }

        public static class DataBean {
            /**
             * groupid : 6139851898881
             */

            private String groupid;

            public String getGroupid() {
                return groupid;
            }

            public void setGroupid(String groupid) {
                this.groupid = groupid;
            }
        }
    }
}
