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

public class Order2BuyAdapter extends BaseQuickAdapter<OrderItemBean, BaseViewHolder> {
    private Context context;

    public Order2BuyAdapter(Context context, int layoutResId, @Nullable List<OrderItemBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItemBean item) {
        TextView order_buy_item_title = helper.getView(R.id.order_buy_item_title);
        TextView order_buy_item_description = helper.getView(R.id.order_buy_item_description);
        TextView order_buy_item_price = helper.getView(R.id.order_buy_item_price);
        TextView order_buy_item_status = helper.getView(R.id.order_buy_item_status);
        ImageView order_buy_item_image = helper.getView(R.id.order_buy_item_image);

        order_buy_item_title.setText(item.getTitle());
        order_buy_item_description.setText(item.getDescription());
        order_buy_item_price.setText(item.getPrice());
        order_buy_item_status.setText(item.getOrderStatusDes());
        Glide.with(context).load(item.getPic())
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error).into(order_buy_item_image);
    }
}
