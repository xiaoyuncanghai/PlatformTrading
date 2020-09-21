package com.pt.lib_common.bean.jsonbean;

public class ModifyGoodsJsonBean {

    /**
     * message : null
     * code : 0
     * data : true
     * success : true
     */

    private String message;
    private int code;
    private boolean data;
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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
