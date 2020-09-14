package com.pt.lib_common.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.lib_common.R;
import com.pt.lib_common.bean.databean.CategoryItemDateBean;

import java.util.List;

public class CategoryListAdapter extends BaseQuickAdapter<CategoryItemDateBean, BaseViewHolder> {

    private Context context;

    public CategoryListAdapter(Context context, int layoutResId, @Nullable List<CategoryItemDateBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryItemDateBean item) {
        ImageView publish_item_image = helper.getView(R.id.category_item_image);
        TextView publish_item_title = helper.getView(R.id.category_item_title);
        TextView publish_item_price = helper.getView(R.id.category_item_price);

        publish_item_title.setText(item.getTitle());
        publish_item_price.setText(item.getPrice());
        Glide.with(context).load(item.getPicUrl())
                .placeholder(com.pt.lib_common.R.drawable.ic_common_happy_yuu1_cat1)
                .error(com.pt.lib_common.R.drawable.ic_common_happy_yuu1_cat1)
                .into(publish_item_image);
    }
}
