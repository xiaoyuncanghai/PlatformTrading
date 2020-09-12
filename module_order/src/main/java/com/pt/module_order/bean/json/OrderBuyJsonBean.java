package com.pt.module_order.bean.json;

import java.util.List;

public class OrderBuyJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"records":[{"id":429,"sn":"3","gid":3,"title":"3","description":"3","price":3,"cateId1":15,"cateId2":0,"cateName1":"金融业","cateName2":"","pic1":"3","pic2":"3","pic3":"3","pic4":"","pic5":"","goodsType":2,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"},{"id":428,"sn":"2","gid":2,"title":"2","description":"2","price":2,"cateId1":14,"cateId2":0,"cateName1":"IT/互联网/通信/电子","cateName2":"","pic1":"2","pic2":"2","pic3":"2","pic4":"","pic5":"","goodsType":1,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"},{"id":427,"sn":"1","gid":1,"title":"1","description":"1","price":1,"cateId1":14,"cateId2":0,"cateName1":"IT/互联网/通信/电子","cateName2":"","pic1":"1","pic2":"1","pic3":"1","pic4":"","pic5":"","goodsType":1,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"},{"id":426,"sn":"D20080916122718052","gid":0,"title":"mmm","description":"mmm","price":99,"cateId1":14,"cateId2":2,"cateName1":"IT/互联网/通信/电子","cateName2":"IT服务/系统集成","pic1":"mm","pic2":"mm","pic3":"mm","pic4":"","pic5":"","goodsType":1,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"}],"total":4,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":1}
     * success : true
     */

    private String message;
    private int code;
    private DataBean data;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String  message) {
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
         * records : [{"id":429,"sn":"3","gid":3,"title":"3","description":"3","price":3,"cateId1":15,"cateId2":0,"cateName1":"金融业","cateName2":"","pic1":"3","pic2":"3","pic3":"3","pic4":"","pic5":"","goodsType":2,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"},{"id":428,"sn":"2","gid":2,"title":"2","description":"2","price":2,"cateId1":14,"cateId2":0,"cateName1":"IT/互联网/通信/电子","cateName2":"","pic1":"2","pic2":"2","pic3":"2","pic4":"","pic5":"","goodsType":1,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"},{"id":427,"sn":"1","gid":1,"title":"1","description":"1","price":1,"cateId1":14,"cateId2":0,"cateName1":"IT/互联网/通信/电子","cateName2":"","pic1":"1","pic2":"1","pic3":"1","pic4":"","pic5":"","goodsType":1,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"},{"id":426,"sn":"D20080916122718052","gid":0,"title":"mmm","description":"mmm","price":99,"cateId1":14,"cateId2":2,"cateName1":"IT/互联网/通信/电子","cateName2":"IT服务/系统集成","pic1":"mm","pic2":"mm","pic3":"mm","pic4":"","pic5":"","goodsType":1,"buyerPhone":"15927623715","sellerPhone":"15927623715","funderPhone":"","orderStatus":0,"createTime":"2020-08-09 16:12:27"}]
         * total : 4
         * size : 10
         * current : 1
         * orders : []
         * hitCount : false
         * searchCount : true
         * pages : 1
         */

        private int total;
        private int size;
        private int current;
        private boolean hitCount;
        private boolean searchCount;
        private int pages;
        private List<RecordsBean> records;
        private List<?> orders;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isHitCount() {
            return hitCount;
        }

        public void setHitCount(boolean hitCount) {
            this.hitCount = hitCount;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }

        public static class RecordsBean {
            /**
             * id : 429
             * sn : 3
             * gid : 3
             * title : 3
             * description : 3
             * price : 3.0
             * cateId1 : 15
             * cateId2 : 0
             * cateName1 : 金融业
             * cateName2 :
             * pic1 : 3
             * pic2 : 3
             * pic3 : 3
             * pic4 :
             * pic5 :
             * goodsType : 2
             * buyerPhone : 15927623715
             * sellerPhone : 15927623715
             * funderPhone :
             * orderStatus : 0
             * createTime : 2020-08-09 16:12:27
             */

            private String id;
            private String sn;
            private int gid;
            private String title;
            private String description;
            private String price;
            private int cateId1;
            private int cateId2;
            private String cateName1;
            private String cateName2;
            private String pic1;
            private String pic2;
            private String pic3;
            private String pic4;
            private String pic5;
            private int goodsType;
            private String buyerPhone;
            private String sellerPhone;
            private String funderPhone;
            private int orderStatus;
            private String createTime;

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

            public String  getPrice() {
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

            public int getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(int goodsType) {
                this.goodsType = goodsType;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
