package com.pt.module_near.bean.data;

public class NearItemDataBean {
    private String title;
    private String price;
    private String picture;
    private String pic_url = "";
    private String pic2_url = "";
    private String pic3_url = "";

    public String getPic2_url() {
        return pic2_url;
    }

    public void setPic2_url(String pic2_url) {
        this.pic2_url = pic2_url;
    }

    public String getPic3_url() {
        return pic3_url;
    }

    public void setPic3_url(String pic3_url) {
        this.pic3_url = pic3_url;
    }

    private String id;

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
