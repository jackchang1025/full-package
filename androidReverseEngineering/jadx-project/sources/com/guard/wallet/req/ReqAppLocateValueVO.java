package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqAppLocateValueVO implements Serializable {
    private String deviceId;
    private String langCode;

    public ReqAppLocateValueVO() {
    }

    public ReqAppLocateValueVO(String str, String str2) {
        this.deviceId = str;
        this.langCode = str2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setLangCode(String str) {
        this.langCode = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ReqAppLocateValueVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("'langCode='");
        return AbstractC0000a.m18n(sb, this.langCode, "'}");
    }
}
