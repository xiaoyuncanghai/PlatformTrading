package com.pt.lib_common.http.router;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pt.lib_common.http.api.ApiService;
import com.pt.lib_common.http.cache.CacheManager;
import com.pt.lib_common.http.callback.NetCallback;
import com.pt.lib_common.http.config.Config;
import com.pt.lib_common.http.exception.CommonException;
import com.pt.lib_common.http.exception.FormatException;
import com.pt.lib_common.http.exception.MangoException;
import com.pt.lib_common.http.manager.RetrofitManager;
import com.pt.lib_common.http.utils.LogUtil;
import com.pt.lib_common.http.utils.MangoUtil;
import com.pt.lib_common.util.RSAUtil;

import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Jeffer on 2017/3/15 11:52.
 * Email: jeffer7150@163.com
 * Description:
 */

public class Mango {

    private static Mango mango;
    private NetCallback netCallback;
    private CommonException ce;
    private Gson gson;
    public Context context;
    private ApiService apiService;

    private Mango(){
    }

    public void init(Context context){
        this.context = context;
    }

    public static synchronized Mango getInstance(){
        if (mango == null){
            mango = new Mango();
        }
        return mango;
    }

    public Mango baseUrl(String url){
        Config.setBaseUrl(url);
        return Mango.getInstance();
    }



    public Mango setUseCache(boolean useCache) {
        Config.setUseCache(useCache);
        return Mango.getInstance();
    }

    /**
     * 自动解析json，回调jsonBean实体
     * @param activity  当前activity页面对象
     * @param path  资源路由url
     * @param fieldMaps  请求体
     * @param jsonBean   解析的实体模板class
     * @param netCallback  请求回调接口
     * @param <T>    解析的实体模板
     * @return    Call操作类，可用于取消请求
     */
    public <T> Call post(Activity activity , String path, Map<String, Object> fieldMaps, final Class<T> jsonBean, final NetCallback<T> netCallback) {
        setUseCache(false);
        //LogUtil.e(RSAUtil.decryptByPrivateKeyWithBase64ForSplit((String) fieldMaps.get("param")));
        netCallback.onBefore(activity);
        Call<ResponseBody> call = null;
        if (gson==null){
            gson = new Gson();
        }
        call = RetrofitManager.getInstance()
                .createRetrofit()
                .createApi(ApiService.class)
                .post(path, fieldMaps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response == null || response.body()==null) {
                    ce = MangoException.handlerException(new FormatException());
                    netCallback.onFailure(call, ce);

                }else{
                    try {
                        T o =  gson.fromJson(response.body().string(),jsonBean);
                        netCallback.onResponse(call, o);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ce =MangoException.handlerException(e);
                        netCallback.onFailure(call, ce);
                    }
                }

                netCallback.onFinish();


              /*  T o = null;
                try {
                    o = gson.fromJson(response.body().string(),jsonBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                netCallback.onResponse(call, o);
                netCallback.onFinish();*/


            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                throwable.printStackTrace();
                ce =MangoException.handlerException(throwable);
                netCallback.onFailure(call, ce);
                netCallback.onFinish();
            }
        });
        return call;
    }


    /**
     * 自动解析json，回调jsonBean实体  带缓存的
     * @param activity  当前activity页面对象
     * @param path  资源路由url
     * @param fieldMaps  请求体
     * @param jsonBean   解析的实体模板class
     * @param netCallback  请求回调接口
     * @param <T>    解析的实体模板
     * @return    Call操作类，可用于取消请求
     */
    public <T> Call postWithCache(final String params , Activity activity , String path, Map<String, Object> fieldMaps, final Class<T> jsonBean, final NetCallback<T> netCallback) {
        setUseCache(true);
        netCallback.onBefore(activity);
        Call<ResponseBody> call = null;
        if (gson==null){
            gson = new Gson();
        }
        call = RetrofitManager.getInstance()
                .createRetrofit(params)
                .createApi(ApiService.class)
                .post(path, fieldMaps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response == null || response.body()==null) {
                    ce = MangoException.handlerException(new FormatException());
                    netCallback.onFailure(call, ce);

                }else{
                    try {
                        T o =  gson.fromJson(response.body().string(),jsonBean);
                        netCallback.onResponse(call, o);
                    } catch (Exception e) {
                        ce =MangoException.handlerException(e);
                        netCallback.onFailure(call, ce);
                    }
                }
                netCallback.onFinish();

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                if (!Config.isUseCache() || MangoUtil.isNetworkAvailable()) {
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                    ce =MangoException.handlerException(throwable);
                    netCallback.onFailure(call, ce);
                    netCallback.onFinish();

                    return;
                }

                Request request = call.request();
                String url = request.url().toString();
                RequestBody requestBody = request.body();
                Charset charset = Charset.forName("UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                sb.append(params);
               /* if (request.method().equals("POST")) {
                    MediaType contentType = requestBody.contentType();

                    if (contentType != null) {
                        charset = contentType.charset(Charset.forName("UTF-8"));
                    }
                    Buffer buffer = new Buffer();
                    try {
                        requestBody.writeTo(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        ce =MangoException.handlerException(e);
                    }
                    sb.append(buffer.readString(charset));
                    buffer.close();
                }*/

                String cache = CacheManager.getInstance().getCache(sb.toString());
                LogUtil.e( "get cache-> key->"+sb.toString() +"cache->"+ cache);

                if (!TextUtils.isEmpty(cache) && jsonBean != null) {
                    T obj = null;
                    try {
                        obj = gson.fromJson(cache, jsonBean);
                        if (obj != null) {
                            netCallback.onLoadFromCache(obj);
                            netCallback.onFinish();
                            return;
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        ce =MangoException.handlerException(e);
                    }

                }
                netCallback.onFailure(call, ce);
                netCallback.onFinish();
                LogUtil.d( ce.getMessage());
            }
        });
        return call;
    }


    /**
     * 回调String类型json数据
     * @param path   资源路由url
     * @param fieldMaps   请求体
     * @param netCallback   请求回调
     * @return  Call操作类，可用于取消请求
     */
    public Call post(Activity activity , String path, Map<String, Object> fieldMaps, final NetCallback<String> netCallback){
        Call<ResponseBody> call = null;
        netCallback.onBefore(activity);
        LogUtil.e(RSAUtil.decryptByPrivateKeyWithBase64ForSplit((String) fieldMaps.get("param")));
        if (gson==null){
            gson = new Gson();
        }
        call = RetrofitManager.getInstance()
                .createRetrofit()
                .createApi(ApiService.class)
                .post(path, fieldMaps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response == null || response.body()==null) {
                    ce =MangoException.handlerException(new FormatException());
                    netCallback.onFailure(call, ce);
                }else{
                    try {
                        String json = response.body().string();
                        netCallback.onResponse(call, json);
                    } catch (Exception e) {
                        ce =MangoException.handlerException(e);
                        netCallback.onFailure(call, ce);
                    }
                }
                netCallback.onFinish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                ce =MangoException.handlerException(throwable);
                netCallback.onFailure(call, ce);
                netCallback.onFinish();
            }
        });
        return call;

    }



    public void setDebug(boolean isDebug){
        Config.setDEBUG(isDebug);
    }
}
