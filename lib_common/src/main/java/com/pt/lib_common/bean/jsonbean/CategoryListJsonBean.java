package com.pt.lib_common.bean.jsonbean;

import java.util.List;

public class CategoryListJsonBean {

    /**
     * message : null
     * code : 0
     * data : {"records":[{"id":588,"title":"123456789","description":"12688","createTime":"2020-10-13 23:47:52","cityCode":"420100","userId":"8b68606917","price":12,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0PicturesWeiXinmmexport1599475989385.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475989385.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"storageemulated0PicturesWeiXinmmexport1599475950815.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":587,"title":"123456854","description":"123468797673","createTime":"2020-10-13 23:42:52","cityCode":"","userId":"8b68606917","price":123455,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0PicturesWeiXinwx_camera_1601917750269.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601917750269.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"storageemulated0PicturesWeiXinmmexport1601464215120.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1601464215120.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":586,"title":"1234567","description":"1阿飞咯8","createTime":"2020-10-13 23:41:54","cityCode":"","userId":"8b68606917","price":100,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0PicturesWeiXinmmexport1601464215120.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1601464215120.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"storageemulated0PicturesScreenshotsScreenshot_20200911_010907_com.ss.android.ugc.aweme.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20200911_010907_com.ss.android.ugc.aweme.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":579,"title":"拒绝","description":"考虑","createTime":"2020-10-09 09:35:39","cityCode":"110100","userId":"74e220857d","price":10000,"pic1":"storageemulated0PicturestestJPEG_20201009_093315.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturestestJPEG_20201009_093315.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":2},{"id":578,"title":"电脑","description":"笔记本电脑","createTime":"2020-10-09 09:33:47","cityCode":"310100","userId":"74e220857d","price":1000,"pic1":"storageemulated0PicturestestJPEG_20201009_093315.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturestestJPEG_20201009_093315.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":2},{"id":568,"title":"海陆空","description":"摸鸡鸡","createTime":"2020-09-28 20:45:26","cityCode":"110100","userId":"b6b02d3264","price":100000,"pic1":"","pic1Url":"","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":1}],"total":6,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":1}
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
         * records : [{"id":588,"title":"123456789","description":"12688","createTime":"2020-10-13 23:47:52","cityCode":"420100","userId":"8b68606917","price":12,"pic1":"storageemulated0PicturesWeiXinmmexport1599476030839.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0PicturesWeiXinmmexport1599475989385.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475989385.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"storageemulated0PicturesWeiXinmmexport1599475950815.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":587,"title":"123456854","description":"123468797673","createTime":"2020-10-13 23:42:52","cityCode":"","userId":"8b68606917","price":123455,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0PicturesWeiXinwx_camera_1601917750269.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601917750269.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"storageemulated0PicturesWeiXinmmexport1601464215120.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1601464215120.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":586,"title":"1234567","description":"1阿飞咯8","createTime":"2020-10-13 23:41:54","cityCode":"","userId":"8b68606917","price":100,"pic1":"storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinwx_camera_1601517319866.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"storageemulated0PicturesWeiXinmmexport1601464215120.jpg","pic2Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1601464215120.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic3":"storageemulated0PicturesScreenshotsScreenshot_20200911_010907_com.ss.android.ugc.aweme.jpg","pic3Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesScreenshotsScreenshot_20200911_010907_com.ss.android.ugc.aweme.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","goodsType":1},{"id":579,"title":"拒绝","description":"考虑","createTime":"2020-10-09 09:35:39","cityCode":"110100","userId":"74e220857d","price":10000,"pic1":"storageemulated0PicturestestJPEG_20201009_093315.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturestestJPEG_20201009_093315.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":2},{"id":578,"title":"电脑","description":"笔记本电脑","createTime":"2020-10-09 09:33:47","cityCode":"310100","userId":"74e220857d","price":1000,"pic1":"storageemulated0PicturestestJPEG_20201009_093315.jpg","pic1Url":"http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturestestJPEG_20201009_093315.jpg?x-oss-process=image/resize,m_fill,h_150,w_150","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":2},{"id":568,"title":"海陆空","description":"摸鸡鸡","createTime":"2020-09-28 20:45:26","cityCode":"110100","userId":"b6b02d3264","price":100000,"pic1":"","pic1Url":"","pic2":"","pic2Url":"","pic3":"","pic3Url":"","goodsType":1}]
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
             * id : 588
             * title : 123456789
             * description : 12688
             * createTime : 2020-10-13 23:47:52
             * cityCode : 420100
             * userId : 8b68606917
             * price : 12
             * pic1 : storageemulated0PicturesWeiXinmmexport1599476030839.jpg
             * pic1Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599476030839.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
             * pic2 : storageemulated0PicturesWeiXinmmexport1599475989385.jpg
             * pic2Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475989385.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
             * pic3 : storageemulated0PicturesWeiXinmmexport1599475950815.jpg
             * pic3Url : http://baimawang168.oss-cn-shenzhen.aliyuncs.com/pic/storageemulated0PicturesWeiXinmmexport1599475950815.jpg?x-oss-process=image/resize,m_fill,h_150,w_150
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
