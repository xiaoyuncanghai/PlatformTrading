package com.pt.module_order.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_order.R;
import com.pt.module_order.bean.data.OrderItemBean;

import java.util.List;

public class Order1AllAdapter extends BaseQuickAdapter<OrderItemBean, BaseViewHolder> {
    private Context context;
    public Order1AllAdapter(Context context, int layoutResId, @Nullable List<OrderItemBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItemBean item) {
        TextView order_all_item_title = helper.getView(R.id.order_all_item_title);
        TextView order_all_item_description = helper.getView(R.id.order_all_item_description);
        TextView order_all_item_price = helper.getView(R.id.order_all_item_price);
        TextView order_all_item_status = helper.getView(R.id.order_all_item_status);
        ImageView order_all_item_image = helper.getView(R.id.order_all_item_image);
        order_all_item_title.setText(item.getTitle());
        order_all_item_description.setText(item.getDescription());
        order_all_item_price.setText(item.getPrice());
        if (item.getOrderStatus() == 0) {
            order_all_item_status.setText("刚上架");
        } else if (item.getOrderStatus() == 10) {
            order_all_item_status.setText("确定资金方");
        } else if (item.getOrderStatus() == -10) {
            order_all_item_status.setText("已取消");
        }
        Glide.with(context).load(item.getPic())
                .placeholder(R.drawable.ic_common_fb)
                .error(R.drawable.ic_common_fb).into(order_all_item_image);
    }
}
