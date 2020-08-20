package com.pt.module_homepage.databean;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

public class BannerItemDataBean implements BaseBannerInfo {
    private String title;
    private String imageUrl;
    private String linkUrl;
    private String reMark;

    /*public String getTitle() {
        return title;
    }*/

    public void setTitle(String title) {
        this.title = title;
    }

    /*public String getImageUrl() {
        return imageUrl;
    }*/

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    @Override
    public String getXBannerUrl() {
        return imageUrl;
    }

    @Override
    public String getXBannerTitle() {
        return title;
    }
}
