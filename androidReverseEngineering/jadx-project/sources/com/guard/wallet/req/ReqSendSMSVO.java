package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqSendSMSVO implements Serializable {
    private String content;
    private String phoneNumber;

    public ReqSendSMSVO() {
    }

    public ReqSendSMSVO(String str, String str2) {
        this.phoneNumber = str;
        this.content = str2;
    }

    public String getContent() {
        return this.content;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ReqSendSMSVO{phoneNumber='");
        sb.append(this.phoneNumber);
        sb.append("', content='");
        return AbstractC0000a.m18n(sb, this.content, "'}");
    }
}
