package com.pt.lib_common.bean.jsonbean;

public class GoodsDetatilJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"id":434,"sn":"20090918164714536","title":"Test Product 001","description":"Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001","price":890,"cateId1":14,"cateName1":"IT/互联网/通信/电子","pic1":"ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED.jpg","pic2":"106E99A1-4F6A-45A2-B320-B0AD4A8E8473.jpg","pic3":"9F983DBA-EC35-42B8-8773-B597CF782EDD.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/106E99A1-4F6A-45A2-B320-B0AD4A8E8473.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/9F983DBA-EC35-42B8-8773-B597CF782EDD.jpg","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","violationReason":"","releaseTime":"2020-09-09 20:04:52","updateTime":"2020-09-09 18:16:47","createUserPhone":"15337255569","createTime":"2020-09-09 18:16:47"}
     * success : true
     */

    private String message;
    private int code;
    private DataBean data;
    private boolean success;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * id : 434
         * sn : 20090918164714536
         * title : Test Product 001
         * description : Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001 Test Product 001
         * price : 890.0
         * cateId1 : 14
         * cateName1 : IT/互联网/通信/电子
         * pic1 : ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED.jpg
         * pic2 : 106E99A1-4F6A-45A2-B320-B0AD4A8E8473.jpg
         * pic3 : 9F983DBA-EC35-42B8-8773-B597CF782EDD.jpg
         * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED.jpg
         * pic2Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/106E99A1-4F6A-45A2-B320-B0AD4A8E8473.jpg
         * pic3Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/9F983DBA-EC35-42B8-8773-B597CF782EDD.jpg
         * goodsType : 2
         * goodsStatus : 1
         * goodsStatusDes : 上架
         * violationReason :
         * releaseTime : 2020-09-09 20:04:52
         * updateTime : 2020-09-09 18:16:47
         * createUserPhone : 15337255569
         * createTime : 2020-09-09 18:16:47
         */

        private int id;
        private String sn;
        private String title;
        private String description;
        private String price;
        private int cateId1;
        private String cateName1;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic1Url;
        private String pic2Url;
        private String pic3Url;
        private int goodsType;
        private int goodsStatus;
        private String goodsStatusDes;
        private String violationReason;
        private String releaseTime;
        private String updateTime;
        private String createUserPhone;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getCateId1() {
            return cateId1;
        }

        public void setCateId1(int cateId1) {
            this.cateId1 = cateId1;
        }

        public String getCateName1() {
            return cateName1;
        }

        public void setCateName1(String cateName1) {
            this.cateName1 = cateName1;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getPic1Url() {
            return pic1Url;
        }

        public void setPic1Url(String pic1Url) {
            this.pic1Url = pic1Url;
        }

        public String getPic2Url() {
            return pic2Url;
        }

        public void setPic2Url(String pic2Url) {
            this.pic2Url = pic2Url;
        }

        public String getPic3Url() {
            return pic3Url;
        }

        public void setPic3Url(String pic3Url) {
            this.pic3Url = pic3Url;
        }

        public int getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(int goodsType) {
            this.goodsType = goodsType;
        }

        public int getGoodsStatus() {
            return goodsStatus;
        }

        public void setGoodsStatus(int goodsStatus) {
            this.goodsStatus = goodsStatus;
        }

        public String getGoodsStatusDes() {
            return goodsStatusDes;
        }

        public void setGoodsStatusDes(String goodsStatusDes) {
            this.goodsStatusDes = goodsStatusDes;
        }

        public String getViolationReason() {
            return violationReason;
        }

        public void setViolationReason(String violationReason) {
            this.violationReason = violationReason;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreateUserPhone() {
            return createUserPhone;
        }

        public void setCreateUserPhone(String createUserPhone) {
            this.createUserPhone = createUserPhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
