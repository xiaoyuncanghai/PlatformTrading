package com.pt.module_mine.bean.json;

import java.util.List;

public class PublishListJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"records":[{"id":584,"title":"1111","price":111,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"2222","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 23:19:24"},{"id":583,"title":"发布商品","price":3800,"pic1":"storageemulated0PicturesWeiXinmmexport1596459236198.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1596459236198.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"发布商品","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 23:03:22"},{"id":582,"title":"发布商品","price":5000,"pic1":"storageemulated0PicturestestJPEG_20201009_091359.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturestestJPEG_20201009_091359.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"发布商品","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 22:45:36"},{"id":581,"title":"求购测试图片","price":1000,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"求购测试图片","goodsType":1,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 21:17:43"},{"id":575,"title":"1","price":26491,"pic1":"storageemulated0DCIMCameraIMG_20200824_131348.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200824_131348.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"1","goodsType":1,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-01 21:28:28"},{"id":574,"title":"测试图片删除","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599056510429.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599056510429.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"测试","goodsType":1,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-01 18:04:40"},{"id":561,"title":"发布商品","price":333333,"pic1":"storageemulated0DCIMCameraIMG_20200824_110541.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200824_110541.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"发","goodsType":2,"goodsStatus":0,"goodsStatusDes":"下架","createTime":"2020-09-23 10:58:55"}],"total":7,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":1}
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
         * records : [{"id":584,"title":"1111","price":111,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"2222","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 23:19:24"},{"id":583,"title":"发布商品","price":3800,"pic1":"storageemulated0PicturesWeiXinmmexport1596459236198.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1596459236198.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"发布商品","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 23:03:22"},{"id":582,"title":"发布商品","price":5000,"pic1":"storageemulated0PicturestestJPEG_20201009_091359.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturestestJPEG_20201009_091359.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"发布商品","goodsType":2,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 22:45:36"},{"id":581,"title":"求购测试图片","price":1000,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"求购测试图片","goodsType":1,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-10 21:17:43"},{"id":575,"title":"1","price":26491,"pic1":"storageemulated0DCIMCameraIMG_20200824_131348.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200824_131348.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"1","goodsType":1,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-01 21:28:28"},{"id":574,"title":"测试图片删除","price":1000,"pic1":"storageemulated0PicturesWeiXinmmexport1599056510429.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599056510429.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"测试","goodsType":1,"goodsStatus":1,"goodsStatusDes":"上架","createTime":"2020-10-01 18:04:40"},{"id":561,"title":"发布商品","price":333333,"pic1":"storageemulated0DCIMCameraIMG_20200824_110541.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0DCIMCameraIMG_20200824_110541.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","description":"发","goodsType":2,"goodsStatus":0,"goodsStatusDes":"下架","createTime":"2020-09-23 10:58:55"}]
         * total : 7
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
             * id : 584
             * title : 1111
             * price : 111.0
             * pic1 : storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg
             * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
             * description : 2222
             * goodsType : 2
             * goodsStatus : 1
             * goodsStatusDes : 上架
             * createTime : 2020-10-10 23:19:24
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
            private String createTime;

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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
