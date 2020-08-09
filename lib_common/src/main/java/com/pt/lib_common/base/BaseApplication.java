package com.pt.lib_common.base;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.apkfuns.logutils.LogUtils;
import com.pt.lib_common.http.utils.LogUtil;
import com.pt.lib_common.util.Utils;
import com.sohu.cyan.android.sdk.api.Config;
import com.sohu.cyan.android.sdk.api.CyanSdk;
import com.sohu.cyan.android.sdk.exception.CyanException;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.List;


/**
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 * @name BaseApplication
 */
public class BaseApplication extends MultiDexApplication {

    public static final String ROOT_PACKAGE = "com.yuu1.fulisudi";

    private static BaseApplication sInstance;

    private List<ApplicationDelegate> mAppDelegateList;



    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.e( " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                //Log.e("app","onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(this,  cb);

        Utils.init(this);
        if (Utils.externalStorageExist()){
            File files_flsd = new File(Utils.getExternalStoragePath());
            if (!files_flsd.exists()){
                files_flsd.mkdir();
            }

            File apk_flsd = new File(Utils.getExternalStoragePath(),"apk_download");
            if (!apk_flsd.exists()){
                apk_flsd.mkdir();
            }

            File db_flsd = new File(Utils.getExternalStoragePath(),"db");
            if (!db_flsd.exists()){
                db_flsd.mkdir();
            }

            File res_flsd = new File(Utils.getExternalStoragePath(),"res");
            if (!res_flsd.exists()){
                res_flsd.mkdir();
            }
        }else{
            LogUtils.e("无SD卡");
            return;
        }

        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("Jeffer")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
        initCyanPlugin();

    }


    private void initCyanPlugin() {
        Config config = new Config();
        config.ui.toolbar_bg = Color.WHITE;
        config.comment.showScore = false;
        config.comment.uploadFiles = false;
        //config.comment.anonymous_token = ;
        //config.login.loginActivityClass = ((Activity)ARouter.getInstance().build(ARouterPath.LOGIN).navigation()).getClass();
        try {
            CyanSdk.register(this,"cyrkahRVs", "9f5633faf1191c10f359889c8395aded" , "http://www.yuu1.com", config);
        } catch (CyanException e) {
            e.printStackTrace();
        }
        //cYan= CyanSdk.getInstance(this);
        //cYan.addCommentToolbar((ViewGroup) getRootView(),"23431saa","2","http://m.sohu.com/n/280264562");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        /*for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }*/
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        /*for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }*/
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        /*for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }*/
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       /* for (ApplicationDelegate delegate : mAppDelegateList) {
            delegate.onConfigurationChanged(newConfig);
        }*/
    }
}
