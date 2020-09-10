package com.pt.lib_common.ui.delegate;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.pt.lib_common.R;
import com.pt.lib_common.themvp.view.AppDelegate;

public class OrderDetailsActDelegate extends AppDelegate {

    private RecyclerView rcv_order_detail;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        rcv_order_detail = get(R.id.rcv_order_detail);


    }
}
