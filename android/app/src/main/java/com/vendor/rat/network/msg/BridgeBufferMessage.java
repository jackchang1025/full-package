package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;

public class BridgeBufferMessage {
    private BridgeBufferBody body;
    private final Integer type = 15;

    public BridgeBufferMessage() {
    }

    public BridgeBufferMessage(BridgeBufferBody bridgeBufferBody) {
        this.body = bridgeBufferBody;
    }

    public BridgeBufferBody getBody() {
        return this.body;
    }

    public Integer getType() {
        return this.type;
    }

    public void setBody(BridgeBufferBody bridgeBufferBody) {
        this.body = bridgeBufferBody;
    }

    @NonNull
    public String toString() {
        return "BridgeBufferMessage{type=" + this.type + ", body=" + this.body + '}';
    }
}
