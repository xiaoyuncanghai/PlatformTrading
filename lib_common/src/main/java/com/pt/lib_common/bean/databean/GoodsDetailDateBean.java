package com.pt.lib_common.bean.databean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class GoodsDetailDateBean implements MultiItemEntity {

    public final static int KEY_GOODS_DETAIIL_TITLE = 0;
    public final static int KEY_GOODS_DETAIL_BODY = 1;

    private int itemType;

    public GoodsDetailDateBean(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private String title;
    private String description;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String goodsStatusDes;
    private String category;
    private String updateTime;
    private String createPhone;
    private int goodsStatus;
    private int goodsType;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoodsStatusDes() {
        return goodsStatusDes;
    }

    public void setGoodsStatusDes(String goodsStatusDes) {
        this.goodsStatusDes = goodsStatusDes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatePhone() {
        return createPhone;
    }

    public void setCreatePhone(String createPhone) {
        this.createPhone = createPhone;
    }

    private String pic1Url;
    private String pic2Url;
    private String pic3Url;

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
}
