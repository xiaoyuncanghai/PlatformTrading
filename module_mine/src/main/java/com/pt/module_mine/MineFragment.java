package com.pt.module_mine;

import android.os.Bundle;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_mine.delegate.MineFragmentDelegate;

public class MineFragment extends FragmentPresenter<MineFragmentDelegate> {


    @Override
    public void initData() {

    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<MineFragmentDelegate> getDelegateClass() {
        return MineFragmentDelegate.class;
    }

}
