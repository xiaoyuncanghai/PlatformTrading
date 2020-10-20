package com.pt.module_mine.adpter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_mine.R;
import com.pt.module_mine.bean.ImageDataBean;

import java.util.ArrayList;
import java.util.List;

public class ImageModifyAdapter extends BaseMultiItemQuickAdapter<ImageDataBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;
    private List<ImageDataBean> dataBeans;
    private int MAX = 3;
    public ImageModifyAdapter(Context context, List<ImageDataBean> data) {
        super(data);
        this.context = context;
        this.dataBeans = data;
        addItemType(ImageDataBean.IMAGE_FROM_INTENT, R.layout.picker_item_photo);
        addItemType(ImageDataBean.IMAGE_FROM_LOCAL, R.layout.picker_item_photo);
        addItemType(ImageDataBean.NO_IMAGE, R.layout.item_add);
    }

    @Override
    public int getItemCount() {
        int count = dataBeans.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataBeans.size() && position != MAX) {
            return ImageDataBean.NO_IMAGE;
        }
        return dataBeans.get(position).getItemType();
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageDataBean item) {
        switch (helper.getItemViewType()) {
            case ImageDataBean.IMAGE_FROM_INTENT:
                ImageView photoIntentView = helper.getView(R.id.iv_photo);
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .apply(new RequestOptions().centerCrop())
                        .thumbnail(0.1f)
                        .into(photoIntentView);
                helper.addOnClickListener(R.id.iv_close);
                break;
            case ImageDataBean.IMAGE_FROM_LOCAL:
                ImageView photoLocalView = helper.getView(R.id.iv_photo);
                Glide.with(mContext)
                        .load(item.getImageUri())
                        .apply(new RequestOptions().centerCrop())
                        .thumbnail(0.1f)
                        .into(photoLocalView);
                helper.addOnClickListener(R.id.iv_close);
                break;

            case ImageDataBean.NO_IMAGE:
                break;
        }

    }
}
