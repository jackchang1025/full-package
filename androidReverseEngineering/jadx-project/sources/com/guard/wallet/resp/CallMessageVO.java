package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CallMessageVO extends MessageBodyVO {
    private String callNumber;
    private String callState;
    private Integer callType;

    public CallMessageVO() {
    }

    public CallMessageVO(Integer num, String str, String str2) {
        this.callType = num;
        this.callNumber = str;
        this.callState = str2;
    }

    public String getCallNumber() {
        return this.callNumber;
    }

    public String getCallState() {
        return this.callState;
    }

    public Integer getCallType() {
        return this.callType;
    }

    public void setCallNumber(String str) {
        this.callNumber = str;
    }

    public void setCallState(String str) {
        this.callState = str;
    }

    public void setCallType(Integer num) {
        this.callType = num;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("CallMessageVO{callType=");
        sb.append(this.callType);
        sb.append(", callNumber='");
        sb.append(this.callNumber);
        sb.append("', callState='");
        return AbstractC0000a.m18n(sb, this.callState, "'}");
    }
}
