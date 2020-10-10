package com.pt.module_near.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.lib_common.view.citychoose.db.DBManager;
import com.pt.lib_common.view.citychoose.model.City;
import com.pt.module_near.R;
import com.pt.module_near.bean.data.NearItemDataBean;

import java.util.List;

public class AllPersonAdapter extends BaseQuickAdapter<NearItemDataBean, BaseViewHolder> {
    private Context mContext;

    public AllPersonAdapter(Context context, int layoutResId, @Nullable List<NearItemDataBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NearItemDataBean item) {
        ImageView person_item_user_image = helper.getView(R.id.person_item_user_image);
        TextView person_item_user_uuid = helper.getView(R.id.person_item_user_uuid);
        TextView person_item_user_location = helper.getView(R.id.person_item_user_location);
        TextView person_item_user_publish_time = helper.getView(R.id.person_item_user_publish_time);
        TextView tv_person_search_title = helper.getView(R.id.tv_person_search_title);
        ImageView iv_pic1 = helper.getView(R.id.iv_pic1);
        ImageView iv_pic2 = helper.getView(R.id.iv_pic2);
        ImageView iv_pic3 = helper.getView(R.id.iv_pic3);
        TextView tv_person_search_money = helper.getView(R.id.tv_person_search_money);
        tv_person_search_title.setText(item.getTitle());
        Glide.with(mContext).load(R.drawable.default_user).into(person_item_user_image);
        person_item_user_uuid.setText(item.getUuid());
        String city_code = item.getLocation_code();
        City city = new DBManager(mContext).searchCityByCode(city_code);
        if (city != null) {
            person_item_user_location.setText(city.getName());
        } else {
            person_item_user_location.setText("全部");
        }
        person_item_user_publish_time.setText(item.getPublish_time());
        if (item.getPic3_url() != null && !item.getPic3_url().equals("")) {
            iv_pic3.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getPic3_url())
                    .placeholder(R.drawable.default_error)
                    .error(R.drawable.default_error)
                    .into(iv_pic3);
        } else {
            iv_pic3.setVisibility(View.GONE);
        }

        if (item.getPic2_url() != null && !item.getPic2_url().equals("")) {
            iv_pic2.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getPic2_url())
                    .placeholder(R.drawable.default_error)
                    .error(R.drawable.default_error)
                    .into(iv_pic2);
        } else {
            iv_pic2.setVisibility(View.GONE);
        }

        if (item.getPic_url() != null && !item.getPic_url().equals("")) {
            iv_pic1.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getPic_url())
                    .placeholder(R.drawable.default_error)
                    .error(R.drawable.default_error)
                    .into(iv_pic1);
        } else {
            iv_pic1.setVisibility(View.GONE);
        }

        tv_person_search_money.setText(item.getPrice());
        helper.addOnClickListener(R.id.tv_person_search_see);
    }
}
