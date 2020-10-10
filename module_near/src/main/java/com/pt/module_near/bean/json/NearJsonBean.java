package com.pt.module_near.bean.json;

import java.util.List;

public class NearJsonBean {
    /**
     * message : null
     * code : 0
     * data : {"records":[{"id":581,"title":"求购测试图片","description":"求购测试图片","createTime":"2020-10-10 21:17:43","cityCode":"420100","userId":"8b68606917","price":1000,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":1},{"id":577,"title":"求购1","description":"求购","createTime":"2020-10-09 09:02:17","cityCode":"420100","userId":"ab42e81280","price":1111,"pic1":"storageemulated0DCIMCameraIMG_20201007_122209.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20201007_122209.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0DCIMCameraIMG_20201005_120702.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20201005_120702.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"","pic3Url":"","goodsType":1},{"id":565,"title":"求购3","description":"求购2","createTime":"2020-09-23 16:01:45","cityCode":"420100","userId":"ab42e81280","price":500,"pic1":"storageemulated0Pictures1600753993985.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0Pictures1600753993985.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":1}],"total":3,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":1}
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
         * records : [{"id":581,"title":"求购测试图片","description":"求购测试图片","createTime":"2020-10-10 21:17:43","cityCode":"420100","userId":"8b68606917","price":1000,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":1},{"id":577,"title":"求购1","description":"求购","createTime":"2020-10-09 09:02:17","cityCode":"420100","userId":"ab42e81280","price":1111,"pic1":"storageemulated0DCIMCameraIMG_20201007_122209.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20201007_122209.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0DCIMCameraIMG_20201005_120702.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20201005_120702.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"","pic3Url":"","goodsType":1},{"id":565,"title":"求购3","description":"求购2","createTime":"2020-09-23 16:01:45","cityCode":"420100","userId":"ab42e81280","price":500,"pic1":"storageemulated0Pictures1600753993985.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0Pictures1600753993985.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":1}]
         * total : 3
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
             * id : 581
             * title : 求购测试图片
             * description : 求购测试图片
             * createTime : 2020-10-10 21:17:43
             * cityCode : 420100
             * userId : 8b68606917
             * price : 1000.0
             * pic1 : storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg
             * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
             * pic2 :
             * pic2Url :
             * pic3 :
             * pic3Url :
             * goodsType : 1
             */

            private String id;
            private String title;
            private String description;
            private String createTime;
            private String cityCode;
            private String userId;
            private String price;
            private String pic1;
            private String pic1Url;
            private String pic2;
            private String pic2Url;
            private String pic3;
            private String pic3Url;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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

            public String getPic2() {
                return pic2;
            }

            public void setPic2(String pic2) {
                this.pic2 = pic2;
            }

            public String getPic2Url() {
                return pic2Url;
            }

            public void setPic2Url(String pic2Url) {
                this.pic2Url = pic2Url;
            }

            public String getPic3() {
                return pic3;
            }

            public void setPic3(String pic3) {
                this.pic3 = pic3;
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
        }
    }
}
