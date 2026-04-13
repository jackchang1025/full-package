package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqCacheTaskBodyVO implements Serializable {
    private String containerCode;
    private String deviceId;

    public ReqCacheTaskBodyVO() {
    }

    public ReqCacheTaskBodyVO(String str, String str2) {
        this.deviceId = str;
        this.containerCode = str2;
    }

    public String getContainerCode() {
        return this.containerCode;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setContainerCode(String str) {
        this.containerCode = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ReqCacheTaskBodyVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', containerCode='");
        return AbstractC0000a.m18n(sb, this.containerCode, "'}");
    }
}
