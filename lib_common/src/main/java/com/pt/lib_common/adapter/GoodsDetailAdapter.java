package com.pt.lib_common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.lib_common.R;
import com.pt.lib_common.bean.databean.GoodsDetailDateBean;

import java.util.List;

public class GoodsDetailAdapter extends BaseMultiItemQuickAdapter<GoodsDetailDateBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;

    public GoodsDetailAdapter(Context context, List<GoodsDetailDateBean> data) {
        super(data);
        this.context = context;
        addItemType(GoodsDetailDateBean.KEY_GOODS_DETAIIL_TITLE, R.layout.goods_detail_title);
        addItemType(GoodsDetailDateBean.KEY_GOODS_DETAIL_BODY, R.layout.goods_detail_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailDateBean item) {
        switch (helper.getItemViewType()) {
            case GoodsDetailDateBean.KEY_GOODS_DETAIIL_TITLE:
                TextView goods_detail_title = helper.getView(R.id.goods_detail_title);
                TextView goods_detail_status = helper.getView(R.id.goods_detail_status);
                TextView goods_detail_category = helper.getView(R.id.goods_detail_category);
                TextView goods_detail_time = helper.getView(R.id.goods_detail_time);
                TextView goods_detail_phone = helper.getView(R.id.goods_detail_phone);
                TextView goods_detail_description = helper.getView(R.id.goods_detail_description);
                TextView goods_detail_price = helper.getView(R.id.goods_detail_price);
                goods_detail_title.setText(item.getTitle());
                goods_detail_status.setText("状态: " + item.getGoodsStatusDes());
                goods_detail_category.setText("所属分类: " + item.getCategory());
                goods_detail_time.setText("时间: " + item.getUpdateTime());
                goods_detail_phone.setText("电话" + item.getCreatePhone());
                goods_detail_description.setText("描述: " + item.getDescription());
                goods_detail_price.setText("价格: " + item.getPrice());
                break;

            case GoodsDetailDateBean.KEY_GOODS_DETAIL_BODY:
                ImageView category_body_pic = helper.getView(R.id.category_body_pic);
                ImageView category_body_pic2 = helper.getView(R.id.category_body_pic2);
                ImageView category_body_pic3 = helper.getView(R.id.category_body_pic3);
                if (!item.getPic1Url().equals("")) {
                    category_body_pic.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(item.getPic1Url())
                            .placeholder(R.drawable.ic_place_holder)
                            .centerCrop()
                            .error(R.drawable.ic_place_holder).into(category_body_pic);
                }

                if (!item.getPic2Url().equals("")) {
                    category_body_pic2.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(item.getPic2Url())
                            .placeholder(R.drawable.ic_place_holder)
                            .centerCrop()
                            .error(R.drawable.ic_place_holder).into(category_body_pic2);
                }

                if (!item.getPic3Url().equals("")) {
                    category_body_pic3.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(item.getPic3Url())
                            .placeholder(R.drawable.ic_place_holder)
                            .centerCrop()
                            .error(R.drawable.ic_place_holder).into(category_body_pic3);
                }

                break;

        }
    }
}
