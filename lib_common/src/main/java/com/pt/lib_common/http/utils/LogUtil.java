package com.pt.lib_common.http.utils;

import android.text.TextUtils;
import android.util.Log;

import com.pt.lib_common.http.config.Config;


public class LogUtil {

	public static void v(String s){
		if(Config.isDEBUG()){
			if (TextUtils.isEmpty(s)){
				Log.v("yuu1_flsd", "日志为空");
			}else{
				Log.v("yuu1_flsd", s);
			}

		}
	}
	public static void d(String s){
		if(Config.isDEBUG()){
			if (TextUtils.isEmpty(s)){
				Log.d("yuu1_flsd", "日志为空");
			}else{
				Log.d("yuu1_flsd", s);
			}
		}

	}
	public static void i(String s){
		if(Config.isDEBUG()){
			if (TextUtils.isEmpty(s)){
				Log.i("yuu1_flsd", "日志为空");
			}else{
				Log.i("yuu1_flsd", s);
			}
		}



	}
	public static void w(String s){
		if(Config.isDEBUG()){
			if (TextUtils.isEmpty(s)){
				Log.w("yuu1_flsd", "日志为空");
			}else{
				Log.w("yuu1_flsd", s);
			}
		}

	}
	public static void e(String s){
		if(Config.isDEBUG()){
			if (TextUtils.isEmpty(s)){
				Log.e("yuu1_flsd", "日志为空");
			}else{
				Log.e("yuu1_flsd", s);
			}
		}

	}

}
