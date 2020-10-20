package com.pt.module_mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.module_mine.delegate.GoodsModifyActDelegate;
import com.pt.module_mine.delegate.GoodsModifyActivityDelegate;

@Route(path = ARouterPath.GOODS_MODIFY)
public class GoodsModifyActivity extends ActivityPresenter<GoodsModifyActivityDelegate> {
    @Override
    protected Class<GoodsModifyActivityDelegate> getDelegateClass() {
        return GoodsModifyActivityDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("商品修改");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewDelegate.onActivityResult(requestCode, resultCode, data);
    }
}
