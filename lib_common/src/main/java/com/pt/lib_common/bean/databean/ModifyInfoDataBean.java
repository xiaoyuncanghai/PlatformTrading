package com.pt.lib_common.bean.databean;

import java.io.Serializable;

public class ModifyInfoDataBean implements Serializable {
    private String title;
    private String description;
    private String pic1Url;
    private String pic2Url;
    private String pic3Url;
    private String category
            ;
    private String price;
    private String location;
    private int userType;
    private String cateId;

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
