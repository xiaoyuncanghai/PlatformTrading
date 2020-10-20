package com.pt.lib_common.bean.jsonbean;

import android.widget.ScrollView;

public class GoodsDetatilJsonBean {


    /**
     * message : null
     * code : 0
     * data : {"id":596,"sn":"20101911013764616","title":"123","description":"123456","price":123,"cateId1":14,"cateName1":"一点公益","pic1":"storageemulated0PicturesScreenshotsScreenshot_20201019_103335_com.pt.platformtrading_location.jpg","pic2":"storageemulated0PicturesScreenshotsScreenshot_20201019_103412_com.pt.platformtrading_location.jpg","pic3":"storageemulated0PicturesScreenshotsScreenshot_20201019_104228_com.huawei.calendar.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20201019_103335_com.pt.platformtrading_location.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20201019_103412_com.pt.platformtrading_location.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20201019_104228_com.huawei.calendar.jpg","pic4":"","pic4Url":"","pic5":"","pic5Url":"","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","violationReason":"","releaseTime":null,"updateTime":"2020-10-19 11:01:37","createUserPhone":"15927623715","createTime":"2020-10-19 11:01:37","cityCode":"420100"}
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
         * id : 596
         * sn : 20101911013764616
         * title : 123
         * description : 123456
         * price : 123.0
         * cateId1 : 14
         * cateName1 : 一点公益
         * pic1 : storageemulated0PicturesScreenshotsScreenshot_20201019_103335_com.pt.platformtrading_location.jpg
         * pic2 : storageemulated0PicturesScreenshotsScreenshot_20201019_103412_com.pt.platformtrading_location.jpg
         * pic3 : storageemulated0PicturesScreenshotsScreenshot_20201019_104228_com.huawei.calendar.jpg
         * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20201019_103335_com.pt.platformtrading_location.jpg
         * pic2Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20201019_103412_com.pt.platformtrading_location.jpg
         * pic3Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20201019_104228_com.huawei.calendar.jpg
         * pic4 :
         * pic4Url :
         * pic5 :
         * pic5Url :
         * goodsType : 2
         * goodsStatus : 1
         * goodsStatusDes : 上架
         * violationReason :
         * releaseTime : null
         * updateTime : 2020-10-19 11:01:37
         * createUserPhone : 15927623715
         * createTime : 2020-10-19 11:01:37
         * cityCode : 420100
         */

        private String id;
        private String sn;
        private String title;
        private String description;
        private String price;
        private String cateId1;
        private String cateName1;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic1Url;
        private String pic2Url;
        private String pic3Url;
        private String pic4;
        private String pic4Url;
        private String pic5;
        private String pic5Url;
        private int goodsType;
        private int goodsStatus;
        private String goodsStatusDes;
        private String violationReason;
        private Object releaseTime;
        private String updateTime;
        private String createUserPhone;
        private String createTime;
        private String cityCode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getCateId1() {
            return cateId1;
        }

        public void setCateId1(String cateId1) {
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

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic4Url() {
            return pic4Url;
        }

        public void setPic4Url(String pic4Url) {
            this.pic4Url = pic4Url;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        public String getPic5Url() {
            return pic5Url;
        }

        public void setPic5Url(String pic5Url) {
            this.pic5Url = pic5Url;
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

        public Object getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(Object releaseTime) {
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

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }
    }
}
