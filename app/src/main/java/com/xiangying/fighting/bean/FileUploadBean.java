package com.xiangying.fighting.bean;

/**
 * Created by xiaoniao on 2016/9/13.
 */
public class FileUploadBean {


    /**
     * code : 1
     * message : 操作成功
     * data : {"imags":{"name":"1473747233536image.jpg","type":"application/octet-stream","size":80418,"key":"imags","ext":"jpg","md5":"a0f1e86dd557a7aa4f9eba0d6c9fb8ec","sha1":"ed6f54d7e692a425b3dfb291e9f73bc554c648f3","savename":"57d7992428f52.jpg","savepath":"2016-09-13/","path":"/Uploads/Picture/2016-09-13/57d7992428f52.jpg","id":401}}
     */

    private int code;
    private String message;
    /**
     * imags : {"name":"1473747233536image.jpg","type":"application/octet-stream","size":80418,"key":"imags","ext":"jpg","md5":"a0f1e86dd557a7aa4f9eba0d6c9fb8ec","sha1":"ed6f54d7e692a425b3dfb291e9f73bc554c648f3","savename":"57d7992428f52.jpg","savepath":"2016-09-13/","path":"/Uploads/Picture/2016-09-13/57d7992428f52.jpg","id":401}
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
         * name : 1473747233536image.jpg
         * type : application/octet-stream
         * size : 80418
         * key : imags
         * ext : jpg
         * md5 : a0f1e86dd557a7aa4f9eba0d6c9fb8ec
         * sha1 : ed6f54d7e692a425b3dfb291e9f73bc554c648f3
         * savename : 57d7992428f52.jpg
         * savepath : 2016-09-13/
         * path : /Uploads/Picture/2016-09-13/57d7992428f52.jpg
         * id : 401
         */

        private ImagsBean imags;

        public ImagsBean getImags() {
            return imags;
        }

        public void setImags(ImagsBean imags) {
            this.imags = imags;
        }

        public static class ImagsBean {
            private String name;
            private String type;
            private int size;
            private String key;
            private String ext;
            private String md5;
            private String sha1;
            private String savename;
            private String savepath;
            private String path;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getExt() {
                return ext;
            }

            public void setExt(String ext) {
                this.ext = ext;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }

            public String getSha1() {
                return sha1;
            }

            public void setSha1(String sha1) {
                this.sha1 = sha1;
            }

            public String getSavename() {
                return savename;
            }

            public void setSavename(String savename) {
                this.savename = savename;
            }

            public String getSavepath() {
                return savepath;
            }

            public void setSavepath(String savepath) {
                this.savepath = savepath;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
