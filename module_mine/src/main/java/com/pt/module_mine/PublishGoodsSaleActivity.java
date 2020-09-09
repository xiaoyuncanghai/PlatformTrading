package com.pt.module_mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.module_mine.delegate.PublishGoodsActDelegate;

@Route(path = ARouterPath.PUBLISH_GOODS_PATH)
public class PublishGoodsSaleActivity extends ActivityPresenter<PublishGoodsActDelegate> {
    @Override
    protected Class<PublishGoodsActDelegate> getDelegateClass() {
        return PublishGoodsActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("发布");
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
