package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CacheTaskResponseVO implements Serializable {
    private String containerCode;
    private String deviceId;
    private String reqUri;
    private String response;

    public CacheTaskResponseVO() {
    }

    public CacheTaskResponseVO(String str, String str2, String str3, String str4) {
        this.deviceId = str;
        this.reqUri = str2;
        this.containerCode = str3;
        this.response = str4;
    }

    public String getContainerCode() {
        return this.containerCode;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getReqUri() {
        return this.reqUri;
    }

    public String getResponse() {
        return this.response;
    }

    public void setContainerCode(String str) {
        this.containerCode = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setReqUri(String str) {
        this.reqUri = str;
    }

    public void setResponse(String str) {
        this.response = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("CacheTaskResultVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', reqUri='");
        sb.append(this.reqUri);
        sb.append("', containerCode='");
        sb.append(this.containerCode);
        sb.append("', response='");
        return AbstractC0000a.m18n(sb, this.response, "'}");
    }
}
