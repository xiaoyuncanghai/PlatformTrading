package com.pt.module_mine.adpter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_mine.R;
import com.pt.module_mine.bean.ImageBean;

import java.util.List;

public class ImageChooseAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {

    private Context context;
    public ImageChooseAdapter(Context context, int layoutResId, @Nullable List<ImageBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {
        ImageView imageView = helper.getView(R.id.imageView);
        Glide.with(context)
                .load(item.getImageUri())
                .placeholder(R.drawable.default_error)
                .centerCrop()
                .error(R.drawable.default_error).into(imageView);
    }
}
