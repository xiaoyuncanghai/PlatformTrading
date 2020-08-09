package com.pt.lib_common.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pt.lib_common.http.router.Mango;

/**
 * Author: Jeffer on 2017/4/10 09:09.
 * Email: jeffer7150@163.com
 * Description:
 */

public class MangoUtil {

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable() {

        Context context = Mango.getInstance().context;
        if (context==null){
            return true;
        }
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =manager.getActiveNetworkInfo();
        // 去进行判断网络是否连接
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }


    /**
     * 获取baseUrl
     * @param url
     * @return
     */
    public static String getBaseUrl(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }


    /**
     * 获取子链接
     * @param url
     * @return
     */
    public static String getChildUrl(String url){
        return url.replace(getBaseUrl(url),"");
    }




}
