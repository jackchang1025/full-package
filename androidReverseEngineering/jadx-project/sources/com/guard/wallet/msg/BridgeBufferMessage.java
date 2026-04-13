package com.guard.wallet.msg;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class BridgeBufferMessage {
    private BridgeBufferBody body;
    private final Integer type = 15;

    public BridgeBufferMessage() {
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

    public BridgeBufferMessage(BridgeBufferBody bridgeBufferBody) {
        this.body = bridgeBufferBody;
    }
}
