package com.pt.module_order.bean.json;

import java.util.List;

public class OrderDetailJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"id":446,"linkId":0,"sn":"D20091416132866811","gid":477,"title":"联想电脑","description":"18年买的联想电脑，想换新的，原价7600","price":5000,"cateId1":14,"cateId2":0,"cateName1":"IT/互联网/通信/电子","cateName2":"","pic1":"storageemulated0DCIMCameraIMG_20200913_153149.jpg","pic2":"","pic3":"","pic4":"","pic5":"","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200913_153149.jpg","pic2Url":"","pic3Url":"","pic4Url":"","pic5Url":"","goodsType":2,"orderTypeDes":"买入订单","person":"王","phone":"1320000000","buyerPhone":"15927623715","sellerPhone":"15311976613","funderPhone":"15337255569","orderStatus":-10,"orderStatusDes":"已取消","createTime":"2020-09-14 16:13:28","orderLogList":["2020-09-14 17:06:13 资金方拒绝","2020-09-14 16:16:45 用户(联系方式：15927623715)申请资金方","2020-09-14 16:13:28 创建订单"]}
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
         * id : 446
         * linkId : 0
         * sn : D20091416132866811
         * gid : 477
         * title : 联想电脑
         * description : 18年买的联想电脑，想换新的，原价7600
         * price : 5000
         * cateId1 : 14
         * cateId2 : 0
         * cateName1 : IT/互联网/通信/电子
         * cateName2 :
         * pic1 : storageemulated0DCIMCameraIMG_20200913_153149.jpg
         * pic2 :
         * pic3 :
         * pic4 :
         * pic5 :
         * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200913_153149.jpg
         * pic2Url :
         * pic3Url :
         * pic4Url :
         * pic5Url :
         * goodsType : 2
         * orderTypeDes : 买入订单
         * person : 王
         * phone : 1320000000
         * buyerPhone : 15927623715
         * sellerPhone : 15311976613
         * funderPhone : 15337255569
         * orderStatus : -10
         * orderStatusDes : 已取消
         * createTime : 2020-09-14 16:13:28
         * orderLogList : ["2020-09-14 17:06:13 资金方拒绝","2020-09-14 16:16:45 用户(联系方式：15927623715)申请资金方","2020-09-14 16:13:28 创建订单"]
         */

        private int id;
        private int linkId;
        private String sn;
        private int gid;
        private String title;
        private String description;
        private int price;
        private int cateId1;
        private int cateId2;
        private String cateName1;
        private String cateName2;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic4;
        private String pic5;
        private String pic1Url;
        private String pic2Url;
        private String pic3Url;
        private String pic4Url;
        private String pic5Url;
        private int goodsType;
        private String orderTypeDes;
        private String person;
        private String phone;
        private String buyerPhone;
        private String sellerPhone;
        private String funderPhone;
        private int orderStatus;
        private String orderStatusDes;
        private String createTime;
        private List<String> orderLogList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLinkId() {
            return linkId;
        }

        public void setLinkId(int linkId) {
            this.linkId = linkId;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
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

        public int getCateId2() {
            return cateId2;
        }

        public void setCateId2(int cateId2) {
            this.cateId2 = cateId2;
        }

        public String getCateName1() {
            return cateName1;
        }

        public void setCateName1(String cateName1) {
            this.cateName1 = cateName1;
        }

        public String getCateName2() {
            return cateName2;
        }

        public void setCateName2(String cateName2) {
            this.cateName2 = cateName2;
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

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
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

        public String getPic4Url() {
            return pic4Url;
        }

        public void setPic4Url(String pic4Url) {
            this.pic4Url = pic4Url;
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

        public String getOrderTypeDes() {
            return orderTypeDes;
        }

        public void setOrderTypeDes(String orderTypeDes) {
            this.orderTypeDes = orderTypeDes;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBuyerPhone() {
            return buyerPhone;
        }

        public void setBuyerPhone(String buyerPhone) {
            this.buyerPhone = buyerPhone;
        }

        public String getSellerPhone() {
            return sellerPhone;
        }

        public void setSellerPhone(String sellerPhone) {
            this.sellerPhone = sellerPhone;
        }

        public String getFunderPhone() {
            return funderPhone;
        }

        public void setFunderPhone(String funderPhone) {
            this.funderPhone = funderPhone;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusDes() {
            return orderStatusDes;
        }

        public void setOrderStatusDes(String orderStatusDes) {
            this.orderStatusDes = orderStatusDes;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<String> getOrderLogList() {
            return orderLogList;
        }

        public void setOrderLogList(List<String> orderLogList) {
            this.orderLogList = orderLogList;
        }
    }
}
