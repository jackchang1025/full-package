package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;

public class BridgeHttpMessage {
    private String body;
    private final Integer type = 17;

    public BridgeHttpMessage(String str) {
        this.body = str;
    }

    public String getBody() {
        return this.body;
    }

    public Integer getType() {
        return this.type;
    }

    public void setBody(String str) {
        this.body = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("BridgeHttpMessage{type=");
        sb.append(this.type);
        sb.append(", body='");
        sb.append(this.body);
        sb.append("'}");
        return sb.toString();
    }
}
