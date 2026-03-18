package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class BridgeMessage implements Serializable {
    private BridgeBody body;
    private final Integer type = 7;

    public BridgeMessage() {
    }

    public BridgeMessage(BridgeBody bridgeBody) {
        this.body = bridgeBody;
    }

    public BridgeBody getBody() {
        return this.body;
    }

    public Integer getType() {
        return this.type;
    }

    public void setBody(BridgeBody bridgeBody) {
        this.body = bridgeBody;
    }

    @NonNull
    public String toString() {
        return "BridgeMessage{type=" + this.type + ", body=" + this.body + '}';
    }
}
