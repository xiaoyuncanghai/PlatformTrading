package com.pt.lib_common.http.config;

import com.pt.lib_common.constants.Constant;

import java.util.List;

/**
 * Created by LIUYONGKUI726 on 2016-11-11.
 */

public class Config {


    /**
     * isFormat : false
     * sucessCode : ["0","1001"]
     * error : {"1001":"网络异常"}
     *
     */
    public static  boolean DEBUG = false;
    public static boolean useCache = false;
    public static String BASE_URL = Constant.BASE_URL;
    private String isFormat;
    /**
     * 1001 : 网络异常
     */

    private ErrorBean error;
    private List<String> sucessCode;

    public String getIsFormat() {
        return isFormat;
    }

    public void setIsFormat(String isFormat) {
        this.isFormat = isFormat;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<String> getSucessCode() {
        return sucessCode;
    }

    public void setSucessCode(List<String> sucessCode) {
        this.sucessCode = sucessCode;
    }

    public static class ErrorBean {
        private String value1001;

        public String getValue1001() {
            return value1001;
        }

        public void setValue1001(String value1001) {
            this.value1001 = value1001;
        }
    }



    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        Config.DEBUG = DEBUG;
    }

    public static boolean isUseCache() {
        return useCache;
    }

    public static void setUseCache(boolean useCache) {
        Config.useCache = useCache;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}
