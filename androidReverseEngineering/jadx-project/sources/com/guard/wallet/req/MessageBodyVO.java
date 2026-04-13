package com.guard.wallet.req;

import java.io.Serializable;

/* loaded from: classes.dex */
public class MessageBodyVO implements Serializable {
    private Long timestamp = Long.valueOf(System.nanoTime());

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long l2) {
        this.timestamp = l2;
    }
}
