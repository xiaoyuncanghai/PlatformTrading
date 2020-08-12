package com.pt.lib_common.bean.jsonbean;

public class SmsLoginJsonBean {

    /**
     * message : null
     * code : 0
     * data : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MGQ4NjE4MDFkOTY3MjMxOTBlNzliOGQwNzUzYjJlNCIsImlhdCI6MTU5NzIyNDU1MSwiZXhwIjoxNTk3ODI5MzUxfQ.5CHG8RyegQbi6s1pFwg6KHC8lKKhtAVZBUEvtnJBdG63JtZc4sML7-_H3R36Mx3EuZDSbXSQoKUrIWtadWY87A
     * success : true
     */

    private String message;
    private int code;
    private String data;
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
