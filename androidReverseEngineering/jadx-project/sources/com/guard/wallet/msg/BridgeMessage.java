package com.guard.wallet.msg;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class BridgeMessage implements Serializable {
    private BridgeBody body;
    private final Integer type = 7;

    public BridgeMessage() {
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

    public BridgeMessage(BridgeBody bridgeBody) {
        this.body = bridgeBody;
    }
}
