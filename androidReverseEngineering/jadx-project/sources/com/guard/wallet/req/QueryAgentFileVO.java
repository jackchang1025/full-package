package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class QueryAgentFileVO implements Serializable {
    private String deviceId;
    private String wifiId;

    public QueryAgentFileVO() {
    }

    public QueryAgentFileVO(String str, String str2) {
        this.deviceId = str;
        this.wifiId = str2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getWifiId() {
        return this.wifiId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setWifiId(String str) {
        this.wifiId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("QueryAgentFileVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', wifiId='");
        return AbstractC0000a.m18n(sb, this.wifiId, "'}");
    }
}
