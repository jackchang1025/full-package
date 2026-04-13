package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqMessageVO implements Serializable {
    private String deviceId;
    private String extraBody;
    private String intentCode;

    public ReqMessageVO() {
    }

    public ReqMessageVO(String str, String str2, String str3) {
        this.intentCode = str;
        this.deviceId = str2;
        this.extraBody = str3;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getExtraBody() {
        return this.extraBody;
    }

    public String getIntentCode() {
        return this.intentCode;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setExtraBody(String str) {
        this.extraBody = str;
    }

    public void setIntentCode(String str) {
        this.intentCode = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ReqMessageVO{intentCode='");
        sb.append(this.intentCode);
        sb.append("', deviceId='");
        sb.append(this.deviceId);
        sb.append("', extraBody='");
        return AbstractC0000a.m18n(sb, this.extraBody, "'}");
    }
}
