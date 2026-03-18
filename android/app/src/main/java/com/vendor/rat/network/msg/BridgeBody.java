package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;

public class BridgeBody extends BaseMsgBody {
    private String bridgePath;
    private String deviceId;

    public BridgeBody() {
    }

    public BridgeBody(String str, String str2) {
        this.deviceId = str;
        this.bridgePath = str2;
    }

    public String getBridgePath() {
        return this.bridgePath;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setBridgePath(String str) {
        this.bridgePath = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("BridgeBody{deviceId='");
        sb.append(this.deviceId);
        sb.append("', bridgePath='");
        sb.append(this.bridgePath);
        sb.append("'}");
        return sb.toString();
    }
}
