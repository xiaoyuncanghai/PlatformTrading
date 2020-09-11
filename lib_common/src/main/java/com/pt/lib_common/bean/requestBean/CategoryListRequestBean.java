package com.pt.lib_common.bean.requestBean;

public class CategoryListRequestBean {

    /**
     * cateId1 : 0
     * cityCode :
     * current : 1
     * goodsType : 0
     * keyword :
     */

    private String cateId1;
    private String cityCode;
    private int current;
    private int goodsType;
    private String keyword;

    public String  getCateId1() {
        return cateId1;
    }

    public void setCateId1(String cateId1) {
        this.cateId1 = cateId1;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
