package com.pt.module_order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_order.R;
import com.pt.module_order.bean.data.OrderItemBean;

import java.util.List;

public class Order2BuyAdapter extends BaseQuickAdapter<OrderItemBean, BaseViewHolder> {
    private Context context;
    private int user_type;

    public Order2BuyAdapter(Context context, int layoutResId, @Nullable List<OrderItemBean> data, int userType) {
        super(layoutResId, data);
        this.context = context;
        this.user_type = userType;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItemBean item) {
        ImageView order_buy_item_image = helper.getView(R.id.order_buy_item_image);
        TextView order_buy_item_title = helper.getView(R.id.order_buy_item_title);
        TextView order_buy_item_description = helper.getView(R.id.order_buy_item_description);
        TextView order_buy_item_create_time = helper.getView(R.id.order_buy_item_create_time);
        TextView order_buy_item_price = helper.getView(R.id.order_buy_item_price);
        TextView order_buy_item_status = helper.getView(R.id.order_buy_item_status);
        TextView order_buy_item_confirm = helper.getView(R.id.order_buy_item_confirm);
        TextView order_buy_item_cancel = helper.getView(R.id.order_buy_item_cancel);
        TextView order_buy_item_cancel_status = helper.getView(R.id.order_buy_item_cancel_status);
        Glide.with(context).load(item.getPic())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .into(order_buy_item_image);
        order_buy_item_title.setText(item.getTitle());
        order_buy_item_description.setText(item.getDescription());
        order_buy_item_create_time.setText(item.getCreate_time());
        order_buy_item_price.setText("￥:" + item.getPrice());

        if (item.getOrderStatus() == 0) {
            if (user_type == 1) {
                order_buy_item_status.setVisibility(View.GONE);
                order_buy_item_confirm.setVisibility(View.VISIBLE);
                order_buy_item_confirm.setText("申请资金");
                order_buy_item_cancel.setVisibility(View.VISIBLE);
            } else if (user_type == 2) {
                order_buy_item_status.setVisibility(View.VISIBLE);
                order_buy_item_status.setText("买家申请资金中");
                order_buy_item_confirm.setVisibility(View.GONE);
                order_buy_item_cancel.setVisibility(View.VISIBLE);
            } else if (user_type == 3) {
                order_buy_item_status.setVisibility(View.GONE);
                order_buy_item_confirm.setVisibility(View.GONE);
                order_buy_item_cancel.setVisibility(View.VISIBLE);
            }
        } else if (item.getOrderStatus() == 10) {
            if (user_type == 3) {
                order_buy_item_status.setVisibility(View.GONE);
                //资金状态
                order_buy_item_confirm.setVisibility(View.VISIBLE);
                order_buy_item_confirm.setText("同意申请");
            } else {
                order_buy_item_status.setVisibility(View.VISIBLE);
                order_buy_item_status.setText(item.getOrderStatusDes());
                order_buy_item_confirm.setVisibility(View.GONE);
            }
            order_buy_item_cancel.setVisibility(View.VISIBLE);
        } else if (item.getOrderStatus() == -10) {
            order_buy_item_cancel_status.setVisibility(View.VISIBLE);
            order_buy_item_status.setVisibility(View.GONE);
            //order_buy_item_status.setText(item.getOrderStatusDes());
            order_buy_item_confirm.setVisibility(View.GONE);
            order_buy_item_cancel.setVisibility(View.GONE);
        } else if (item.getOrderStatus() == 20) {
            order_buy_item_status.setVisibility(View.VISIBLE);
            order_buy_item_status.setText(item.getOrderStatusDes());
            order_buy_item_confirm.setVisibility(View.GONE);
            order_buy_item_cancel.setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.order_buy_item_cancel);
        helper.addOnClickListener(R.id.order_buy_item_confirm);
    }
}
