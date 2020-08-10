package com.pt.lib_common.bean;

public class BaseIntJsonBean {


    /**
     * status : 0
     * msg : 已经表态
     * result : null
     */

    private int status;
    private String msg;
    private Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
