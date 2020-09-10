package com.pt.lib_common.ui.delegate;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.pt.lib_common.R;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class OrderCategoryActDelegate extends AppDelegate {

    private SmartRefreshLayout srl_order_category;
    private RecyclerView rcv_order_category;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order_category;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_order_category = get(R.id.srl_order_category);
        rcv_order_category = get(R.id.rcv_order_category);


    }
}
