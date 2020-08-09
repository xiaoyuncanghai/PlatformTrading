package com.pt.lib_common.http.cache;

import com.pt.lib_common.http.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created on 2016/11/30.
 *
 * @author WangYi
 */

public class EnhancedCacheInterceptor implements Interceptor {

    private String params;

    public EnhancedCacheInterceptor(String params) {
        this.params = params;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

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
            }
            //  RSAUtil.decryptByPrivateKeyWithBase64ForSplit(
            sb.append(buffer.readString(charset));
            buffer.close();
        }*/
        LogUtil.e( "EnhancedCacheInterceptor -> key:" + sb.toString());

        ResponseBody responseBody = response.body();
        MediaType contentType = responseBody.contentType();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String key = sb.toString();
        String json = buffer.clone().readString(charset);

        CacheManager.getInstance().putCache(key, json);
        LogUtil.e( "put cache-> key:" + key + "-> json:" + json);
        return chain.proceed(request);
    }
}
