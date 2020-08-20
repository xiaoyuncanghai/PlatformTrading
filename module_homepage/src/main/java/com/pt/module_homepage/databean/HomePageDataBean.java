package com.pt.module_homepage.databean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class HomePageDataBean implements MultiItemEntity {

    public static final int TYPE_HOME_PAGE_BANNER = 0;
    public static final int TYPE_HOME_PAGE_CATEGORY = 1;
    public static final int TYPE_HOME_PAGE_PROMOTE = 2;

    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private List<BannerItemDataBean> bannerItemList;

    public List<BannerItemDataBean> getBannerItemList() {
        return bannerItemList;
    }

    public void setBannerItemList(List<BannerItemDataBean> bannerItemList) {
        this.bannerItemList = bannerItemList;
    }

    private int cate_id;
    private String cate_name;
    private String cate_icon;

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_icon() {
        return cate_icon;
    }

    public void setCate_icon(String cate_icon) {
        this.cate_icon = cate_icon;
    }

    private String promote_title;
    private String promote_price;
    private String promote_pic;
    private int promote_id;
    private int promote_type;

    public int getPromote_type() {
        return promote_type;
    }

    public void setPromote_type(int promote_type) {
        this.promote_type = promote_type;
    }

    public String getPromote_title() {
        return promote_title;
    }

    public void setPromote_title(String promote_title) {
        this.promote_title = promote_title;
    }

    public String getPromote_price() {
        return promote_price;
    }

    public void setPromote_price(String promote_price) {
        this.promote_price = promote_price;
    }

    public String getPromote_pic() {
        return promote_pic;
    }

    public void setPromote_pic(String promote_pic) {
        this.promote_pic = promote_pic;
    }

    public int getPromote_id() {
        return promote_id;
    }

    public void setPromote_id(int promote_id) {
        this.promote_id = promote_id;
    }
}
