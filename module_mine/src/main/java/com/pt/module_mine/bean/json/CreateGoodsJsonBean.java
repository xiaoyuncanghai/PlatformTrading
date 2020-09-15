package com.pt.module_mine.bean.json;

public class CreateGoodsJsonBean {


    /**
     * message : null
     * code : 0
     * data : {"id":498,"sn":"20091517140957127","title":"呵呵","description":"呵呵","price":100,"cateId1":15,"cateName1":null,"pic1":"storageemulated0DCIMCameraIMG_20200824_110755.jpg","pic2":null,"pic3":null,"pic1Url":null,"pic2Url":null,"pic3Url":null,"pic4":null,"pic4Url":null,"pic5":null,"pic5Url":null,"goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架","violationReason":null,"releaseTime":null,"updateTime":"2020-09-15 17:14:09","createUserPhone":"15927623715","createTime":"2020-09-15 17:14:09"}
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
         * id : 498
         * sn : 20091517140957127
         * title : 呵呵
         * description : 呵呵
         * price : 100
         * cateId1 : 15
         * cateName1 : null
         * pic1 : storageemulated0DCIMCameraIMG_20200824_110755.jpg
         * pic2 : null
         * pic3 : null
         * pic1Url : null
         * pic2Url : null
         * pic3Url : null
         * pic4 : null
         * pic4Url : null
         * pic5 : null
         * pic5Url : null
         * goodsType : 1
         * goodsStatus : 0
         * goodsStatusDes : 下架
         * violationReason : null
         * releaseTime : null
         * updateTime : 2020-09-15 17:14:09
         * createUserPhone : 15927623715
         * createTime : 2020-09-15 17:14:09
         */

        private int id;
        private String sn;
        private String title;
        private String description;
        private int price;
        private int cateId1;
        private Object cateName1;
        private String pic1;
        private Object pic2;
        private Object pic3;
        private Object pic1Url;
        private Object pic2Url;
        private Object pic3Url;
        private Object pic4;
        private Object pic4Url;
        private Object pic5;
        private Object pic5Url;
        private int goodsType;
        private int goodsStatus;
        private String goodsStatusDes;
        private Object violationReason;
        private Object releaseTime;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCateId1() {
            return cateId1;
        }

        public void setCateId1(int cateId1) {
            this.cateId1 = cateId1;
        }

        public Object getCateName1() {
            return cateName1;
        }

        public void setCateName1(Object cateName1) {
            this.cateName1 = cateName1;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public Object getPic2() {
            return pic2;
        }

        public void setPic2(Object pic2) {
            this.pic2 = pic2;
        }

        public Object getPic3() {
            return pic3;
        }

        public void setPic3(Object pic3) {
            this.pic3 = pic3;
        }

        public Object getPic1Url() {
            return pic1Url;
        }

        public void setPic1Url(Object pic1Url) {
            this.pic1Url = pic1Url;
        }

        public Object getPic2Url() {
            return pic2Url;
        }

        public void setPic2Url(Object pic2Url) {
            this.pic2Url = pic2Url;
        }

        public Object getPic3Url() {
            return pic3Url;
        }

        public void setPic3Url(Object pic3Url) {
            this.pic3Url = pic3Url;
        }

        public Object getPic4() {
            return pic4;
        }

        public void setPic4(Object pic4) {
            this.pic4 = pic4;
        }

        public Object getPic4Url() {
            return pic4Url;
        }

        public void setPic4Url(Object pic4Url) {
            this.pic4Url = pic4Url;
        }

        public Object getPic5() {
            return pic5;
        }

        public void setPic5(Object pic5) {
            this.pic5 = pic5;
        }

        public Object getPic5Url() {
            return pic5Url;
        }

        public void setPic5Url(Object pic5Url) {
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

        public Object getViolationReason() {
            return violationReason;
        }

        public void setViolationReason(Object violationReason) {
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
    }
}
