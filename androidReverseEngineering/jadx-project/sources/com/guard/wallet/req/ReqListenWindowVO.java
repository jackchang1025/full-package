package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqListenWindowVO implements Serializable {
    private String containerCode;
    private String deviceId;
    private String langCode;

    public ReqListenWindowVO() {
    }

    public ReqListenWindowVO(String str, String str2, String str3) {
        this.deviceId = str;
        this.langCode = str2;
        this.containerCode = str3;
    }

    public String getContainerCode() {
        return this.containerCode;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setContainerCode(String str) {
        this.containerCode = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setLangCode(String str) {
        this.langCode = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ReqListenWindowVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', langCode='");
        sb.append(this.langCode);
        sb.append("', containerCode='");
        return AbstractC0000a.m18n(sb, this.containerCode, "'}");
    }
}
