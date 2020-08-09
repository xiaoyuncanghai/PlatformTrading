package com.pt.lib_common.http.manager;

import android.util.Log;

import com.pt.lib_common.http.cache.EnhancedCacheInterceptor;
import com.pt.lib_common.http.config.Config;
import com.pt.lib_common.http.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Author: Jeffer on 2017/3/15 10:35.
 * Email: jeffer7150@163.com
 * Description:
 */

public class RetrofitManager {
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;
    private OkHttpClient client;

    private RetrofitManager(){
        //initRetrofit();
    }

    public static synchronized RetrofitManager getInstance(){
        if (mRetrofitManager == null){
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }


    private OkHttpClient initRetrofit(String... params) {
        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String s) {
                Log.e("response:",s);
            }
        });
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (Config.DEBUG){
            //添加retrofit日志打印
            builder.addInterceptor(logginInterceptor);
        }
        LogUtil.e(Config.isUseCache()+"");
        if (Config.isUseCache()){
            // 添加缓存拦截器
            builder.addInterceptor(new EnhancedCacheInterceptor(params[0]));
        }



        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        client = builder.build();
        return client;

    }

    public RetrofitManager createRetrofit(String... params){
        if (params!=null&&params.length==1){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .client(initRetrofit(params[0]))
                    .build();
        }else{
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .client(initRetrofit())
                    .build();
        }

        return RetrofitManager.getInstance();
    }


    public <T> T createApi(Class<T> apiServer){
        return mRetrofit.create(apiServer);
    }
}
