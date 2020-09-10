package com.pt.module_mine.adpter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_mine.R;
import com.pt.module_mine.bean.PublishItemDataBean;

import org.w3c.dom.Text;

import java.util.List;

public class PublistListAdapter extends BaseQuickAdapter<PublishItemDataBean, BaseViewHolder> {

    private Context context;
    public PublistListAdapter(Context context,int layoutResId, @Nullable List<PublishItemDataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishItemDataBean item) {
        ImageView publish_item_image = helper.getView(R.id.publish_item_image);
        TextView publish_item_title = helper.getView(R.id.publish_item_title);
        TextView publish_item_description = helper.getView(R.id.publish_item_description);
        TextView publish_item_price = helper.getView(R.id.publish_item_price);
        TextView publish_item_status = helper.getView(R.id.publish_item_status);
        TextView publish_item_status_description = helper.getView(R.id.publish_item_status_description);

        publish_item_title.setText(item.getTitle());
        publish_item_description.setText(item.getDescription());
        publish_item_price.setText(item.getPrice());
        Glide.with(context).load(item.getPic1Url())
                .placeholder(com.pt.lib_common.R.drawable.ic_common_happy_yuu1_cat1)
                .error(com.pt.lib_common.R.drawable.ic_common_happy_yuu1_cat1)
                .into(publish_item_image);

        switch (item.getGoodsStatus()) {
            case 0:
                publish_item_status.setText("下架");
                break;
            case 1:
                publish_item_status.setText("上架");
                break;
            case 3:
                publish_item_status.setText("违规");
                break;
            case 4:
                publish_item_status.setText("禁售");
                break;
        }

        publish_item_status_description.setText("备注:"+ item.getGoodsStatusDes());
    }
}
