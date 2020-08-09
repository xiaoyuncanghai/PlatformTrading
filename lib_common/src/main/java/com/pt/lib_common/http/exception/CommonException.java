package com.pt.lib_common.http.exception;

/**
 * Author: Jeffer on 2017/3/28 11:41.
 * Email: jeffer7150@163.com
 * Description:
 */

public class CommonException extends Exception {

    private int code;
    private String message;

    public CommonException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
