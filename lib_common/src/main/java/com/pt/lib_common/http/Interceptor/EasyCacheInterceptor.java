package com.pt.lib_common.http.Interceptor;

import com.pt.lib_common.http.cache.EasyCacheTime;
import com.pt.lib_common.http.cache.EasyCacheType;
import com.pt.lib_common.http.utils.EasyNetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangy on 2017/2/15.
 */
public class EasyCacheInterceptor implements Interceptor {
	private final int mType;

	public EasyCacheInterceptor(int mType) {
		this.mType = mType;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		final Request originalRequest = chain.request();
		String cacheHeaderValue = null;
		if (mType == EasyCacheType.CACHE_TYPE_SHORT) {
			cacheHeaderValue = EasyNetworkUtils.isNetWorkOnLine()
					? "public, max-age=" + EasyCacheTime.CACHE_TIME_SHORT
					: "public, only-if-cached, max-stale=86400";
		} else if (mType == EasyCacheType.CACHE_TYPE_MID) {
			cacheHeaderValue = EasyNetworkUtils.isNetWorkOnLine()
					? "public, max-age=" + EasyCacheTime.CACHE_TIME_MID
					: "public, only-if-cached, max-stale=86400";
		} else if (mType == EasyCacheType.CACHE_TYPE_LONG) {
			cacheHeaderValue = EasyNetworkUtils.isNetWorkOnLine()
					? "public, max-age=" + EasyCacheTime.CACHE_TIME_LONG
					: "public, only-if-cached, max-stale=86400";
		}

		final Request finalRequest = originalRequest.newBuilder()
				.tag(originalRequest.url().toString())
				.build();

		Response response = chain.proceed(originalRequest);

		if (mType != EasyCacheType.CACHE_TYPE_DEFAULT && response.code() < 400) {
			return response.newBuilder()
					.removeHeader("Pragma")
					.removeHeader("Cache-Control")
					.addHeader("Cache-Control", cacheHeaderValue)
					.build();
		}
		return response;
	}
}
