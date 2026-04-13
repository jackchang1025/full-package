package com.guard.wallet.req;

import android.support.annotation.NonNull;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class LocalDebugEventVO extends MessageBodyVO {
    private String message;
    private String topic;
    private String type;

    public LocalDebugEventVO() {
    }

    public LocalDebugEventVO(String str, String str2, String str3) {
        this.topic = str;
        this.type = str2;
        this.message = str3;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getType() {
        return this.type;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setTopic(String str) {
        this.topic = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("LocalDebugEventVO{topic='");
        sb.append(this.topic);
        sb.append("', type='");
        sb.append(this.type);
        sb.append("', message='");
        return AbstractC0000a.m18n(sb, this.message, "'}");
    }
}
