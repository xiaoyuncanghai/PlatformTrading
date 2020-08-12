package com.pt.lib_common.easyhttp.cache;

import android.os.Environment;


public class EasyCacheDir {
	public static final String SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
	public static final String CACHE_SHORT_DIR = "/cache/http.cache.1";
	public static final String CACHE_MID_DIR = "/cache/http.cache.2";
	public static final String CACHE_LONG_DIR = "/cache/http.cache.3";
}
