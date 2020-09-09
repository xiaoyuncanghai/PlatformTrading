package com.pt.module_mine.bean.json;

public class ApplySaleJsonBean {

    /**
     * message : null
     * code : 0
     * data : null
     * success : true
     */

    private String message;
    private int code;
    private String  data;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
