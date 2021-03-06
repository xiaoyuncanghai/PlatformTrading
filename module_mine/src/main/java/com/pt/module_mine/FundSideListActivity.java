package com.pt.module_mine;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.module_mine.delegate.FundSideListActDelegate;

@Route(path = ARouterPath.FUND_SIDE)
public class FundSideListActivity extends ActivityPresenter<FundSideListActDelegate> {
    @Override
    protected Class<FundSideListActDelegate> getDelegateClass() {
        return FundSideListActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("选择资金方");
        setLeft("\ue605");
        if (tv_left!=null){
            tv_left.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
        finish();
    }
}
