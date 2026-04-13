package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class TouchEvent implements Serializable {
    private String codeName;
    private String deviceName;
    private String typeName;
    private String value;

    public TouchEvent() {
    }

    public TouchEvent(String str, String str2, String str3, String str4) {
        this.deviceName = str;
        this.typeName = str2;
        this.codeName = str3;
        this.value = str4;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getValue() {
        return this.value;
    }

    public void setCodeName(String str) {
        this.codeName = str;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("TouchEvent{deviceName='");
        sb.append(this.deviceName);
        sb.append("', typeName='");
        sb.append(this.typeName);
        sb.append("', codeName='");
        sb.append(this.codeName);
        sb.append("', value='");
        return AbstractC0000a.m18n(sb, this.value, "'}");
    }
}
