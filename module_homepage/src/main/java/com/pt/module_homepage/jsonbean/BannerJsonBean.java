package com.pt.module_homepage.jsonbean;

import java.util.List;

public class BannerJsonBean {

    /**
     * message : null
     * code : 0
     * data : [{"title":"图片1","imageUrl":"20200809173310.png","linkUrl":"http://www.baidu.com","remark":""}]
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
         * title : 图片1
         * imageUrl : 20200809173310.png
         * linkUrl : http://www.baidu.com
         * remark :
         */

        private String title;
        private String imageUrl;
        private String linkUrl;
        private String remark;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
