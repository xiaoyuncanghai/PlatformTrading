package com.pt.lib_common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author: Jeffer on 2017/8/7 17:52.
 * Email: jeffer7150@163.com
 * Description:
 */

public  class BaseFragment extends SupportFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public String getSimpleName(){
        return getClass().getSimpleName();
    }


    //    protected View mRootView;
//    public Context mContext;
//    protected boolean isVisible;
//    private boolean isPrepared;
//    private boolean isFirst = true;
//
//    public BaseFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        LogUtil.d("fragment->setUserVisibleHint");
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            lazyLoad();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getActivity();
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        if (mRootView == null) {
//            mRootView = initView(inflater, container, savedInstanceState);
//        }
////        Log.d("TAG", "fragment->onCreateView");
//        return mRootView;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        Log.d("TAG", "fragment->onActivityCreated");
//        isPrepared = true;
//        lazyLoad();
//    }
//
//    protected void lazyLoad() {
//        if (!isPrepared || !isVisible || !isFirst) {
//            return;
//        }
//        LogUtil.d(getClass().getName() + "->initData()");
//        initData();
//        isFirst = false;
//    }
//
//    //do something
//    protected void onInvisible() {
//
//
//    }
//
//    public abstract View initView(LayoutInflater inflater, ViewGroup container,
//                                  Bundle savedInstanceState);
//
//    public abstract void initData();


}
