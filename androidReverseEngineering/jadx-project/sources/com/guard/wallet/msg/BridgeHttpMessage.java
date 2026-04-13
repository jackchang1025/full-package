package com.guard.wallet.msg;

import android.support.annotation.NonNull;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
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
        return AbstractC0000a.m18n(sb, this.body, "'}");
    }
}
