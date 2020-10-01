package com.pt.module_near;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_near.delegate.NearFragmentDelegate;

import org.greenrobot.eventbus.EventBus;

public class NearFragment extends FragmentPresenter<NearFragmentDelegate> {

    @Override
    public void initData() {

    }

    public static NearFragment newInstance() {
        Bundle args = new Bundle();
        NearFragment fragment = new NearFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<NearFragmentDelegate> getDelegateClass() {
        return NearFragmentDelegate.class;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        viewDelegate.srl_near_page.autoRefresh();
    }

    public void refreshDeleteData(){
        viewDelegate.refreshDeleteData();
    }

    //每次切换的时候刷新数据
    public void  refreshData(){
        viewDelegate.srl_near_page.autoRefresh();
    }
}
