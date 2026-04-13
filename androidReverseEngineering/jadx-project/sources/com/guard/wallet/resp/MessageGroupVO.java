package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class MessageGroupVO implements Serializable {
    private Integer enable;
    private String groupCode;

    public MessageGroupVO() {
    }

    public MessageGroupVO(String str, Integer num) {
        this.groupCode = str;
        this.enable = num;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setEnable(Integer num) {
        this.enable = num;
    }

    public void setGroupCode(String str) {
        this.groupCode = str;
    }

    @NonNull
    public String toString() {
        return "MessageGroupVO{groupCode='" + this.groupCode + "', enable=" + this.enable + '}';
    }
}
