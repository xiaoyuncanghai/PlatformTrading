package com.pt.module_mine.bean;

import android.net.Uri;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ImageDataBean implements MultiItemEntity {
    public static final int IMAGE_FROM_INTENT = 0;
    public static final int IMAGE_FROM_LOCAL = 2;
    public static final int NO_IMAGE = 3;

    public ImageDataBean(int itemType) {
        this.itemType = itemType;
    }

    private String imageUrl;

    private String imageName;
    private Uri imageUri;
    private String imagePath;

    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
