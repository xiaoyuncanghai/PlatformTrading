package com.pt.lib_common.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.lib_common.R;
import com.pt.lib_common.bean.ItemSelectDialogModel;

import java.util.List;

/**
 * Author: Jeffer on 2018/3/15 17:49.
 * Email: jeffer7150@163.com
 * Description:
 */

public class ItemSelectDialogAdapter extends BaseMultiItemQuickAdapter<ItemSelectDialogModel, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ItemSelectDialogAdapter(List<ItemSelectDialogModel> data) {
        super(data);
        addItemType(ItemSelectDialogModel.TYPE_SELECT, R.layout.layout_item_select_content);
        addItemType(ItemSelectDialogModel.TYPE_CANCEL, R.layout.layout_item_select_cancel);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemSelectDialogModel item) {
        switch (helper.getItemViewType()) {
            case ItemSelectDialogModel.TYPE_SELECT:
                helper.setText(R.id.tv_content,item.getItemText());
                break;
            case ItemSelectDialogModel.TYPE_CANCEL:
                helper.setText(R.id.tv_cancel,item.getItemText());
                break;
        }
    }


}
