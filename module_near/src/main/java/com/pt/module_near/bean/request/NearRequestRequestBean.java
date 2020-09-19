package com.pt.module_near.bean.request;

public class NearRequestRequestBean {
    /*cityCode，current，goodsType=1*/
    private String cityCode;
    private int current;
    private int goodsType;

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
}
