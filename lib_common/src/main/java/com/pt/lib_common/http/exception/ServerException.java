package com.pt.lib_common.http.exception;

/**
 * Author: Jeffer on 2017/3/28 11:36.
 * Email: jeffer7150@163.com
 * Description:
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
