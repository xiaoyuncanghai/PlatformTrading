package com.pt.lib_common.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
        ImageView category_item_image = helper.getView(R.id.category_item_image);
        TextView category_item_title = helper.getView(R.id.category_item_title);
        TextView category_item_price = helper.getView(R.id.category_item_price);
        TextView category_item_description = helper.getView(R.id.category_item_description);
        TextView category_item_tv_see = helper.getView(R.id.category_item_tv_see);

        Glide.with(context).load(item.getPicUrl())
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                .into(category_item_image);
        category_item_title.setText(item.getTitle());
        category_item_description.setText(item.getTitle());
        category_item_price.setText("￥:" + item.getPrice());
    }
}
