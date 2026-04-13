package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class MessageGroupVO implements Serializable {
    private Integer enable;
    private String groupCode;

    public MessageGroupVO() {}
    public MessageGroupVO(String groupCode, Integer enable) {
        this.groupCode = groupCode; this.enable = enable;
    }

    public Integer getEnable() { return this.enable; }
    public String getGroupCode() { return this.groupCode; }
    public void setEnable(Integer v) { this.enable = v; }
    public void setGroupCode(String v) { this.groupCode = v; }

    @NonNull
    @Override
    public String toString() {
        return "MessageGroupVO{groupCode='" + this.groupCode + "', enable=" + this.enable + "}";
    }
}
