package com.guard.wallet.msg;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class BridgeBufferBody extends BaseMsgBody {
    private String bridgePath;
    private String buffer;
    private String deviceId;
    private Boolean toDesktop;

    public BridgeBufferBody() {
    }

    public BridgeBufferBody(String str, String str2, Boolean bool, String str3) {
        this.deviceId = str;
        this.bridgePath = str2;
        this.toDesktop = bool;
        this.buffer = str3;
    }

    public String getBridgePath() {
        return this.bridgePath;
    }

    public String getBuffer() {
        return this.buffer;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public Boolean getToDesktop() {
        return this.toDesktop;
    }

    public void setBridgePath(String str) {
        this.bridgePath = str;
    }

    public void setBuffer(String str) {
        this.buffer = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setToDesktop(Boolean bool) {
        this.toDesktop = bool;
    }

    @NonNull
    public String toString() {
        return "BridgeBufferBody{deviceId='" + this.deviceId + "', bridgePath='" + this.bridgePath + "', toDesktop=" + this.toDesktop + ", buffer=" + this.buffer + '}';
    }
}
