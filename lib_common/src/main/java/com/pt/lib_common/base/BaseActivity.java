package com.pt.lib_common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pt.lib_common.http.utils.LogUtil;

import me.yokeyword.fragmentation.SupportActivity;


/**
 * Author: Jeffer on 2017/8/7 17:51.
 * Email: jeffer7150@163.com
 * Description:
 */

public class BaseActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*JPushInterface.onResume(this);
         //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);*/
        LogUtil.e(getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public String getSimpleName(){
        return "";
    }









}
