package com.pt.module_homepage.jsonbean;

import java.util.List;

public class HomePageCategoryJsonBean {

    /**
     * message : null
     * code : 0
     * data : [{"id":14,"name":"IT/互联网/通信/电子","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/1.jpg"},{"id":15,"name":"金融业","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/2.jpg"},{"id":16,"name":"房地产/建筑业","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/3.jpg"},{"id":17,"name":"商业服务","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/4.jpg"},{"id":18,"name":"教育/培训","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/5.jpg"},{"id":19,"name":"广告/媒体","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/6.jpg"},{"id":20,"name":"消费品/贸易/批发/零售","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/7.jpg"},{"id":21,"name":"加工制造/仪表设备","icon":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/8.jpg"}]
     * success : true
     */

    private String message;
    private int code;
    private boolean success;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 14
         * name : IT/互联网/通信/电子
         * icon : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/prod/ad/1.jpg
         */

        private int id;
        private String name;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
