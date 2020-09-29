package com.pt.module_mine.adpter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.lib_common.bean.databean.FundSideItem;
import com.pt.module_mine.R;

import java.util.List;

public class FundSideAdapter extends BaseQuickAdapter<FundSideItem, BaseViewHolder> {

    private Context context;
    private TextView tv_fund_side_name;
    private TextView tv_fund_side_phone;

    public FundSideAdapter(Context context, int layoutResId, @Nullable List<FundSideItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FundSideItem item) {
        tv_fund_side_name = helper.getView(R.id.tv_fund_side_name);
        tv_fund_side_phone = helper.getView(R.id.tv_fund_side_phone);

        tv_fund_side_name.setText("资金方: "+item.getName());
        tv_fund_side_phone.setText("电话: "+item.getPhone());
    }
}
