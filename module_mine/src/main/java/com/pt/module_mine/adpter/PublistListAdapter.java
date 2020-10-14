package com.pt.module_mine.adpter;

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
import com.pt.module_mine.R;
import com.pt.module_mine.bean.PublishItemDataBean;

import java.util.List;

public class PublistListAdapter extends BaseQuickAdapter<PublishItemDataBean, BaseViewHolder> {

    private Context context;

    public PublistListAdapter(Context context, int layoutResId, @Nullable List<PublishItemDataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishItemDataBean item) {
        ImageView publish_item_image = helper.getView(R.id.publish_item_image);
        TextView publish_item_title = helper.getView(R.id.publish_item_title);
        TextView publish_item_description = helper.getView(R.id.publish_item_description);
        TextView publish_item_create_time = helper.getView(R.id.publish_item_create_time);
        TextView publish_item_price = helper.getView(R.id.publish_item_price);
        TextView publish_item_onSale = helper.getView(R.id.publish_item_onSale);
        publish_item_title.setText(item.getTitle());
        publish_item_description.setText(item.getDescription());
        publish_item_create_time.setText(item.getCreate_time());
        publish_item_price.setText("￥:" + item.getPrice());
        Glide.with(context).load(item.getPic1Url())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .placeholder(com.pt.lib_common.R.drawable.default_error)
                .error(com.pt.lib_common.R.drawable.default_error)
                .into(publish_item_image);
        if (item.getGoodsStatus() == 1) {
            publish_item_onSale.setVisibility(View.VISIBLE);
            publish_item_onSale.setText("下架");
        } else if (item.getGoodsStatus() == 0) {
            //下架状态
            publish_item_onSale.setVisibility(View.VISIBLE);
            publish_item_onSale.setText("上架");
        } else if (item.getGoodsStatus() == 3) {
            publish_item_onSale.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.publish_item_onSale);
        helper.addOnClickListener(R.id.publish_item_modify);
        helper.addOnClickListener(R.id.publish_item_delete);
    }
}
