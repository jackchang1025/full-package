package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CallStateVO implements Serializable {
    private String callState;
    private String description;
    private Integer state;

    public CallStateVO() {
    }

    public CallStateVO(Integer num, String str, String str2) {
        this.state = num;
        this.callState = str;
        this.description = str2;
    }

    public String getCallState() {
        return this.callState;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getState() {
        return this.state;
    }

    public void setCallState(String str) {
        this.callState = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setState(Integer num) {
        this.state = num;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("CallStateVO{state=");
        sb.append(this.state);
        sb.append(", callState='");
        sb.append(this.callState);
        sb.append("', description='");
        return AbstractC0000a.m18n(sb, this.description, "'}");
    }
}
