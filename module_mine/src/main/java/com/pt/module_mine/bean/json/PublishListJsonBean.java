package com.pt.module_mine.bean.json;

import java.util.List;

public class PublishListJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"records":[{"id":438,"title":"test","price":10000000,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"test","goodsType":2,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":437,"title":"test","price":10000000,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"test","goodsType":2,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":433,"title":"fhnd","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599475950815.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"hshsh","goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":432,"title":"fhnd","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599475950815.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"hshsh","goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":431,"title":"标题","price":100,"pic1":"","pic1Url":"","description":"123456789","goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架"}],"total":5,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":1}
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
         * records : [{"id":438,"title":"test","price":10000000,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"test","goodsType":2,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":437,"title":"test","price":10000000,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"test","goodsType":2,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":433,"title":"fhnd","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599475950815.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"hshsh","goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":432,"title":"fhnd","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599475950815.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"hshsh","goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架"},{"id":431,"title":"标题","price":100,"pic1":"","pic1Url":"","description":"123456789","goodsType":1,"goodsStatus":0,"goodsStatusDes":"下架"}]
         * total : 5
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
             * id : 438
             * title : test
             * price : 10000000
             * pic1 : storageemulated0PicturesWeiXinmmexport1599476030839.jpg
             * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
             * description : test
             * goodsType : 2
             * goodsStatus : 0
             * goodsStatusDes : 下架
             */

            private String id;
            private String title;
            private String price;
            private String pic1;
            private String pic1Url;
            private String description;
            private int goodsType;
            private int goodsStatus;
            private String goodsStatusDes;

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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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
        }
    }
}
