package com.pt.lib_common.easyhttp;

import android.content.Context;

import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.easyhttp.cache.EasyCacheType;
import com.pt.lib_common.easyhttp.callback.EasyCallback;
import com.pt.lib_common.easyhttp.config.EasyHttpConfig;
import com.pt.lib_common.easyhttp.download.EasyDownloadManager;
import com.pt.lib_common.easyhttp.manager.EasyHttpClientManager;
import com.pt.lib_common.easyhttp.request.EasyRequestParams;

public class EasyHttpClient {
    /**
     * EasyHttpClient init.
     *
     * @param context
     */
    public static void init(Context context) {
        EasyHttpClientManager.getInstance().init(context);
    }

    public static void init(Context context, EasyHttpConfig config) {
        EasyHttpClientManager.getInstance().init(context, config);
    }

    /**
     * Download environment init.
     * Make sure the init function is only called once.
     */
    public static void initDownloadEnvironment() {
        EasyDownloadManager.getInstance().init(EasyHttpClientManager.getInstance().getContext());
    }

    public static void initDownloadEnvironment(int threadCount) {
        EasyDownloadManager.getInstance().init(EasyHttpClientManager.getInstance().getContext(), threadCount);
    }

    /**
     * set the debug mode.
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        EasyHttpClientManager.getInstance().setDebug(debug);
    }

    /**
     * get request.
     *
     * @param url
     * @param callback
     * @param <T>
     */
    public static <T> void get(String url, EasyCallback<T> callback) {
        get(HttpConstant.BASE_URL + url, null, callback);
    }

    public static <T> void get(String url, EasyRequestParams easyRequestParams, EasyCallback<T> callBack) {
        EasyHttpClientManager.getInstance().get(HttpConstant.BASE_URL + url, easyRequestParams, EasyCacheType.CACHE_TYPE_NO_SETTING, callBack);
    }

    public static <T> void get(String url, int cacheType, EasyCallback<T> callback) {
        get(HttpConstant.BASE_URL + url, null, cacheType, callback);
    }

    public static <T> void get(String url, EasyRequestParams easyRequestParams, int cacheType, EasyCallback<T> callback) {
        EasyHttpClientManager.getInstance().get(HttpConstant.BASE_URL + url, easyRequestParams, cacheType, callback);
    }

    /**
     * post request.
     *
     * @param url
     * @param easyRequestParams
     * @param callback
     * @param <T>
     */
    public static <T> void post(String url, EasyRequestParams easyRequestParams, EasyCallback<T> callback) {
        EasyHttpClientManager.getInstance().postOrDeleteOrPut(HttpConstant.BASE_URL + url, easyRequestParams, callback, EasyHttpClientManager.REQUEST_POST);
    }

    /**
     * delete request.
     *
     * @param url
     * @param easyRequestParams
     * @param callback
     * @param <T>
     */
    public static <T> void delete(String url, EasyRequestParams easyRequestParams, EasyCallback<T> callback) {
        EasyHttpClientManager.getInstance().postOrDeleteOrPut(HttpConstant.BASE_URL + url, easyRequestParams, callback, EasyHttpClientManager.REQUEST_DELETE);
    }

    /**
     * put request
     *
     * @param url
     * @param easyRequestParams
     * @param callback
     * @param <T>
     */
    public static <T> void put(String url, EasyRequestParams easyRequestParams, EasyCallback<T> callback) {
        EasyHttpClientManager.getInstance().postOrDeleteOrPut(HttpConstant.BASE_URL + url, easyRequestParams, callback, EasyHttpClientManager.REQUEST_PUT);
    }

    /**
     * upload file.
     *
     * @param url
     * @param filePath
     * @param callback
     * @param <T>
     */
    public static <T> void uploadFile(String url, String filePath, EasyCallback<T> callback) {
        EasyHttpClientManager.getInstance().uploadFile(HttpConstant.BASE_URL + url, filePath, callback);
    }

    /**
     * cancel request.
     *
     * @param cacheType
     */
    public static void cancel(int cacheType) {
        EasyHttpClientManager.getInstance().cancelRequest(cacheType);
    }
}
