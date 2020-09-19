package com.pt.module_near;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initHeader();
    }

    public void initHeader() {
        setTitle("附近");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        viewDelegate.srl_near_page.autoRefresh();
    }
}
