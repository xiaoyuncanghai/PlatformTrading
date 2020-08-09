package com.pt.lib_common.base;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Author: Jeffer on 2018/2/26 09:16.
 * Email: jeffer7150@163.com
 * Description: make each component can receive the lifecycle of Application
 */

public interface ApplicationDelegate {

    void attachBaseContext(Context base);

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onConfigurationChanged(Configuration newConfig);

    void onTrimMemory(int level);
}
