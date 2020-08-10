package com.pt.lib_common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Author: Jeffer on 2018/3/15 17:49.
 * Email: jeffer7150@163.com
 * Description:
 */

public class ItemSelectDialogModel implements MultiItemEntity {


    public static final int TYPE_SELECT =1;
    public static final int TYPE_CANCEL =2;

    private int itemType;

    private String itemText;

    public ItemSelectDialogModel(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
