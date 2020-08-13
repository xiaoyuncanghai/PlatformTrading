package com.pt.module_homepage;

import android.os.Bundle;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_homepage.delegate.HomePageFragmentDelegate;

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
    protected Class<HomePageFragmentDelegate> getDelegateClass() {
        return HomePageFragmentDelegate.class;
    }
}
