package com.pt.platformtrading;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Process;

import com.facebook.fresco.helper.Phoenix;
import com.pt.lib_common.base.BaseApplication;
import com.pt.lib_common.http.router.Mango;
import com.pt.lib_common.util.Utils;

import java.util.List;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Author: Jeffer on 2018/2/26 09:18.
 * Email: jeffer7150@163.com
 * Description:
 */

public class MainApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //  Fresco 初始化
        Phoenix.init(this);
        //  初始化mango
        //初始化mango
        Mango.getInstance().init(this);
        Mango.getInstance().setDebug(true);
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            //ARouter.openDebug();
            //ARouter.openLog();
        }
        //ARouter.init(this);
        // Fragmentation 初始化
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.NONE)
                .debug(BuildConfig.DEBUG)
                // 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
        String currentProcessName = getCurrentProcessName();
        /*if (BuildConfig.APPLICATION_ID.equals(currentProcessName) || BuildConfig.APPLICATION_ID.concat(":channel").equals(currentProcessName)) {
            OnePush.init(this, ((platformCode, platformName) -> {
                //platformCode和platformName就是在<meta/>标签中，对应的"平台标识码"和平台名称
              *//*  else if (RomUtils.isHuaweiRom()) {
                    //return platformCode == 103;
                    return false;
                } else if (RomUtils.isFlymeRom()) {
                    //return platformCode == 102;
                    return false;
                }*//*
                LogUtil.e(platformName + "  " + platformCode);
                if (RomUtils.isMiuiRom()) {
                    return platformCode == 101;
                } else {
                    return platformCode == 106;
                }

            }));
            OnePush.register();
        }*/
    }

    /**
     * 获取当前进程名称
     *
     * @return processName
     */
    public String getCurrentProcessName() {
        int currentProcessId = Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            if (runningAppProcess.pid == currentProcessId) {
                return runningAppProcess.processName;
            }
        }
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
