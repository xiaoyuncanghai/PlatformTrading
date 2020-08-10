package com.pt.lib_common.bean;

public class EventMessageModel {
    public int type=0;
    private String labelCount="0";
    private int msgCount = 0;

    public String getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(String labelCount) {
        this.labelCount = labelCount;
    }

    public EventMessageModel(int type) {
        this.type = type;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
