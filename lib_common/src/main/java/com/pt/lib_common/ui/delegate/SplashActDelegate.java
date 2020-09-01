package com.pt.lib_common.ui.delegate;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.pt.lib_common.R;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;

public class SplashActDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        LogUtils.d("token = " + SPHelper.getString("token", "", true));
        if (SPHelper.getString("token", "", true).equals("")) {
            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH)
                    .withTransition(R.anim.activity_up_in, R.anim.activity_up_out).navigation();
        } else {
            //MainApplication已经设置, 不需要重复设置
            ARouter.getInstance().build(ARouterPath.MAIN_PATH)
                    .withTransition(R.anim.activity_up_in, R.anim.activity_up_out).navigation();
        }
        getActivity().finish();
    }
}
