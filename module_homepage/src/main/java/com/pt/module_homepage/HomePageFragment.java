package com.pt.module_homepage;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_homepage.delegate.HomePageFragmentDelegate;

import org.greenrobot.eventbus.EventBus;

public class HomePageFragment extends FragmentPresenter<HomePageFragmentDelegate> {


    @Override
    public void initData() {

    }

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected Class<HomePageFragmentDelegate> getDelegateClass() {
        return HomePageFragmentDelegate.class;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewDelegate.onActivityResult(requestCode, resultCode, data);
    }


}
