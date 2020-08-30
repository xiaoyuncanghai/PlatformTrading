package com.pt.module_near.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_near.R;
import com.pt.module_near.bean.data.NearItemDataBean;

import java.util.List;

public class NearAdapter extends BaseQuickAdapter<NearItemDataBean, BaseViewHolder> {

    private Context context;
    public NearAdapter(Context context, int layoutResId, @Nullable List<NearItemDataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NearItemDataBean item) {
        TextView near_promote_tv_title = helper.getView(R.id.near_promote_tv_title);
        TextView near_price = helper.getView(R.id.near_promote_tv_price);
        ImageView near_promote_iv = helper.getView(R.id.near_promote_iv);
        near_promote_tv_title.setText(item.getTitle());
        near_price.setText(item.getPrice());
        Glide.with(context)
                .load(item.getPicture())
                .placeholder(R.drawable.ic_common_happy_yuu1_cat1)
                .error(R.drawable.ic_common_happy_yuu1_cat1).into(near_promote_iv);
    }
}
