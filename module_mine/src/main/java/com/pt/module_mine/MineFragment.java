package com.pt.module_mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("我的");
    }
}
