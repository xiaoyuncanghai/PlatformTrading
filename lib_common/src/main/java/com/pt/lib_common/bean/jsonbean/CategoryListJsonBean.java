package com.pt.lib_common.bean.jsonbean;

import java.util.List;

public class CategoryListJsonBean {

    /**
     * code : 0
     * data : {"current":0,"hitCount":true,"orders":[{"asc":true,"column":""}],"pages":0,"records":[{"goodsType":0,"id":0,"pic1":"","pic1Url":"","price":0,"title":""}],"searchCount":true,"size":0,"total":0}
     * message :
     * success : true
     */

    private int code;
    private DataBean data;
    private String message;
    private boolean success;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * current : 0
         * hitCount : true
         * orders : [{"asc":true,"column":""}]
         * pages : 0
         * records : [{"goodsType":0,"id":0,"pic1":"","pic1Url":"","price":0,"title":""}]
         * searchCount : true
         * size : 0
         * total : 0
         */

        private int current;
        private boolean hitCount;
        private int pages;
        private boolean searchCount;
        private int size;
        private int total;
        private List<OrdersBean> orders;
        private List<RecordsBean> records;

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

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class OrdersBean {
            /**
             * asc : true
             * column :
             */

            private boolean asc;
            private String column;

            public boolean isAsc() {
                return asc;
            }

            public void setAsc(boolean asc) {
                this.asc = asc;
            }

            public String getColumn() {
                return column;
            }

            public void setColumn(String column) {
                this.column = column;
            }
        }

        public static class RecordsBean {
            /**
             * goodsType : 0
             * id : 0
             * pic1 :
             * pic1Url :
             * price : 0
             * title :
             */

            private int goodsType;
            private String id;
            private String pic1;
            private String pic1Url;
            private String price;
            private String title;

            public int getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(int goodsType) {
                this.goodsType = goodsType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
