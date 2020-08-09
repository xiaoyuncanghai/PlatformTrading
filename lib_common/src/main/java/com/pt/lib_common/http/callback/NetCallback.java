package com.pt.lib_common.http.callback;

import android.app.Activity;

import com.pt.lib_common.http.exception.CommonException;
import com.pt.lib_common.http.router.Mango;
import com.pt.lib_common.http.utils.LogUtil;
import com.pt.lib_common.util.LoadingDialog;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Author: Jeffer on 2017/3/15 11:58.
 * Email: jeffer7150@163.com
 * Description:
 */

public abstract class NetCallback<T>  {

    public void onBefore(Activity activity){
        LoadingDialog.showLoading(activity);
    }

    public void onResponse(Call<ResponseBody> call, T data) {
        //LogUtil.e(data.toString());
    }

    public void onFailure(Call<ResponseBody> call, CommonException exception) {
        LogUtil.e(exception.getMessage());
    }

    public void onFinish(){
        Mango.getInstance().setUseCache(false);
        LoadingDialog.hideLoading();
    }

    public void onLoadFromCache(T t){

    }

}
