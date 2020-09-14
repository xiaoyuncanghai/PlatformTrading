package com.pt.module_homepage.jsonbean;

import java.util.List;

public class HomePagePromoteJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"records":[{"id":488,"title":"test003","price":192,"pic1":"CC95F08C-88C3-4012-9D6D-64A413D254B3_1.heic","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/CC95F08C-88C3-4012-9D6D-64A413D254B3_1.heic?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2},{"id":487,"title":"test","price":1000000,"pic1":"storageemulated0PicturesWeiXinwx_camera_1599650592023.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1599650592023.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":484,"title":"product test 001","price":92,"pic1":"CC95F08C-88C3-4012-9D6D-64A413D254B3.heic","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/CC95F08C-88C3-4012-9D6D-64A413D254B3.heic?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2},{"id":483,"title":"test发布","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2},{"id":479,"title":"需要买台电脑","price":200,"pic1":"","pic1Url":"","goodsType":1},{"id":477,"title":"联想电脑","price":5000,"pic1":"storageemulated0DCIMCameraIMG_20200913_153149.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200913_153149.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2}],"total":6,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":1}
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
         * records : [{"id":488,"title":"test003","price":192,"pic1":"CC95F08C-88C3-4012-9D6D-64A413D254B3_1.heic","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/CC95F08C-88C3-4012-9D6D-64A413D254B3_1.heic?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2},{"id":487,"title":"test","price":1000000,"pic1":"storageemulated0PicturesWeiXinwx_camera_1599650592023.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1599650592023.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":484,"title":"product test 001","price":92,"pic1":"CC95F08C-88C3-4012-9D6D-64A413D254B3.heic","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/CC95F08C-88C3-4012-9D6D-64A413D254B3.heic?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2},{"id":483,"title":"test发布","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2},{"id":479,"title":"需要买台电脑","price":200,"pic1":"","pic1Url":"","goodsType":1},{"id":477,"title":"联想电脑","price":5000,"pic1":"storageemulated0DCIMCameraIMG_20200913_153149.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200913_153149.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":2}]
         * total : 6
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
             * id : 488
             * title : test003
             * price : 192.0
             * pic1 : CC95F08C-88C3-4012-9D6D-64A413D254B3_1.heic
             * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/CC95F08C-88C3-4012-9D6D-64A413D254B3_1.heic?x-oss-process=image/resize,m_fill,h_150,w_150
             * goodsType : 2
             */

            private String id;
            private String title;
            private String price;
            private String pic1;
            private String pic1Url;
            private int goodsType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPic1() {
                return pic1;
            }

            public void setPic1(String pic1) {
                this.pic1 = pic1;
            }

            public String getPic1Url() {
                return pic1Url;
            }

            public void setPic1Url(String pic1Url) {
                this.pic1Url = pic1Url;
            }

            public int getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(int goodsType) {
                this.goodsType = goodsType;
            }
        }
    }
}
