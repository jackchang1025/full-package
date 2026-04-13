package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AppLocateValueVO implements Serializable {
    private String locateCode;
    private String locateValue;

    public AppLocateValueVO() {
    }

    public AppLocateValueVO(String str, String str2) {
        this.locateCode = str;
        this.locateValue = str2;
    }

    public String getLocateCode() {
        return this.locateCode;
    }

    public String getLocateValue() {
        return this.locateValue;
    }

    public void setLocateCode(String str) {
        this.locateCode = str;
    }

    public void setLocateValue(String str) {
        this.locateValue = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("AppLocateValueVO{locateCode='");
        sb.append(this.locateCode);
        sb.append("', locateValue='");
        return AbstractC0000a.m18n(sb, this.locateValue, "'}");
    }
}
