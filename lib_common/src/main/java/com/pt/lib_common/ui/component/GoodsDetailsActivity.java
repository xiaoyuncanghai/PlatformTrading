package com.pt.lib_common.ui.component;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.GoodsDetailsActDelegate;

@Route(path = ARouterPath.GOODS_DETAIL)
public class GoodsDetailsActivity extends ActivityPresenter<GoodsDetailsActDelegate> {
    @Override
    protected Class<GoodsDetailsActDelegate> getDelegateClass() {
        return GoodsDetailsActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("商品详情");
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
