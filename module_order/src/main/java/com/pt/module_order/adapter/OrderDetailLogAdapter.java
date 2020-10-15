package com.pt.module_order.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_order.R;

import java.util.List;

public class OrderDetailLogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public OrderDetailLogAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView order_detail_log_info_item_content = helper.getView(R.id.order_detail_log_info_item_content);
        order_detail_log_info_item_content.setText(item);
    }
}
