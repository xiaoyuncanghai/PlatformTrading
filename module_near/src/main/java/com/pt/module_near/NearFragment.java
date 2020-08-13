package com.pt.module_near;

import android.os.Bundle;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_near.delegate.NearFragmentDelegate;

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
}
