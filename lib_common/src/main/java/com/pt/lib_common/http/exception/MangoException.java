package com.pt.lib_common.http.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.pt.lib_common.http.utils.MangoUtil;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.cert.CertPathValidatorException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Author: Jeffer on 2017/3/28 11:34.
 * Email: jeffer7150@163.com
 * Description:
 */

public class MangoException {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int ACCESS_DENIED = 302;
    private static final int HANDEL_ERRROR = 417;
    /*
    * 异常处理
    * */
    public static CommonException handlerException(Throwable e){
       // Log.e("Mango_HttpError", e.getMessage());
        CommonException ex;
        if (MangoUtil.isNetworkAvailable()){
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                ex = new CommonException(e, ERROR.HTTP_ERROR);
                switch (httpException.code()) {
                    case UNAUTHORIZED:
                        ex.setMessage("未授权的请求");
                    case FORBIDDEN:
                        ex.setMessage("禁止访问");
                    case NOT_FOUND:
                        ex.setMessage("服务器地址未找到");
                    case REQUEST_TIMEOUT:
                        ex.setMessage("请求超时");
                    case GATEWAY_TIMEOUT:
                        ex.setMessage("网关响应超时");
                    case INTERNAL_SERVER_ERROR:
                        ex.setMessage("服务器出错");
                    case BAD_GATEWAY:
                        ex.setMessage("无效的请求");
                    case SERVICE_UNAVAILABLE:
                        ex.setMessage("服务器不可用");
                    case ACCESS_DENIED:
                        ex.setMessage("网络错误");
                    case HANDEL_ERRROR:
                        ex.setMessage("接口处理失败");
                    default:
                        ex.setMessage(e.getMessage());
                        break;
                }
                ex.setCode(httpException.code());
                return ex;
            } else if (e instanceof ServerException) {
                ServerException resultException = (ServerException) e;
                ex = new CommonException(resultException, resultException.code);
                ex.setMessage(resultException.getMessage());
                return ex;
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                ex = new CommonException(e, ERROR.PARSE_ERROR);
                ex.setMessage("数据解析错误");
                return ex;
            } else if (e instanceof ConnectException) {
                ex = new CommonException(e, ERROR.NETWORD_ERROR);
                ex.setMessage("连接失败");
                return ex;
            } else if (e instanceof SSLHandshakeException) {
                ex = new CommonException(e, ERROR.SSL_ERROR);
                ex.setMessage("证书验证失败");
                return ex;
            } else if (e instanceof CertPathValidatorException) {
                ex = new CommonException(e, ERROR.SSL_NOT_FOUND);
                ex.setMessage("证书路径没找到");
                return ex;
            }
            else if (e instanceof ConnectTimeoutException){
                ex = new CommonException(e, ERROR.TIMEOUT_ERROR);
                ex.setMessage("连接超时");
                return ex;
            } else if (e instanceof java.net.SocketTimeoutException) {
                ex = new CommonException(e, ERROR.TIMEOUT_ERROR);
                ex.setMessage("连接超时");
                return ex;
            } else if (e instanceof ClassCastException) {
                ex = new CommonException(e, ERROR.FORMAT_ERROR);
                ex.setMessage("类型转换出错");
                return ex;
            } else if (e instanceof NullPointerException) {
                ex = new CommonException(e, ERROR.NULL);
                ex.setMessage("数据有空");
                return ex;
            } else if (e instanceof FormatException) {
                FormatException resultException = (FormatException) e;
                ex = new CommonException(resultException, resultException.code);
                ex.setMessage(resultException.message);
                return ex;
            }else if(e instanceof UnknownHostException){
                ex = new CommonException(e, ERROR.NO_NETWORK);
                ex.setMessage("网络无连接或域名解析出错，请检查后重试");
                return ex;
            }else{
                ex = new CommonException(e, ERROR.UNKNOWN);
                e.printStackTrace();
                ex.setMessage("未知错误，请稍后重试");
                return ex;
            }
        }else{
            ex = new CommonException(e, ERROR.NO_NETWORK);
            ex.setMessage("网络连接失败，请检查网络设置");
            return ex;
        }

    }

    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 证书未找到
         */
        public static final int SSL_NOT_FOUND = 1007;

        /**
         * 出现空值
         */
        public static final int NULL = -100;

        /**
         * 格式错误
         */
        public static final int FORMAT_ERROR = 1008;

        /**
         * 网络无连接
         */
        public static final int NO_NETWORK = 1009;

        /**
         * 无网络权限
         */
        public static final int NO_PERMISSION = 1010;
    }
}
