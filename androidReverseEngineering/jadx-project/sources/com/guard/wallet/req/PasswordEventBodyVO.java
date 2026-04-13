package com.guard.wallet.req;

import android.support.annotation.NonNull;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PasswordEventBodyVO extends MessageBodyVO {
    private String lockBatchId;
    private String password;

    public PasswordEventBodyVO() {
    }

    public PasswordEventBodyVO(String str, String str2) {
        this.password = str;
        this.lockBatchId = str2;
    }

    public String getLockBatchId() {
        return this.lockBatchId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLockBatchId(String str) {
        this.lockBatchId = str;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("PasswordEventBodyVO{password='");
        sb.append(this.password);
        sb.append("', lockBatchId='");
        return AbstractC0000a.m18n(sb, this.lockBatchId, "'}");
    }
}
