package com.guard.wallet.msg;

import java.io.Serializable;

/* loaded from: classes.dex */
public class BaseMsgBody implements Serializable {
    private final Long timestamp = Long.valueOf(System.currentTimeMillis());

    public Long getTimestamp() {
        return this.timestamp;
    }
}
