package com.pt.module_mine.oss;

public class Config {
    // 访问的endpoint地址
    public static final String OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    //callback 地址
    public static final String OSS_CALLBACK_URL = "http://120.76.137.162:18081/oss/callback";
    //sts鉴权地址
    public static final String STS_SERVER_URL = "http://120.76.137.162:18081/oss/get_token";
    //bucket_name
    public static final String BUCKET_NAME = "baimawang168";

    public static final String OSS_ACCESS_KEY_ID = "*************";
    public static final String OSS_ACCESS_KEY_SECRET = "*************";

    public static final int DOWNLOAD_SUC = 1;
    public static final int DOWNLOAD_Fail = 2;
    public static final int UPLOAD_SUC = 3;
    public static final int UPLOAD_Fail = 4;
    public static final int UPLOAD_PROGRESS = 5;
    public static final int LIST_SUC = 6;
    public static final int HEAD_SUC = 7;
    public static final int RESUMABLE_SUC = 8;
    public static final int SIGN_SUC = 9;
    public static final int BUCKET_SUC = 10;
    public static final int GET_STS_SUC = 11;
    public static final int MULTIPART_SUC = 12;
    public static final int STS_TOKEN_SUC = 13;
    public static final int FAIL = 9999;
    public static final int REQUESTCODE_AUTH = 10111;
    public static final int REQUESTCODE_LOCALPHOTOS = 10112;
}
