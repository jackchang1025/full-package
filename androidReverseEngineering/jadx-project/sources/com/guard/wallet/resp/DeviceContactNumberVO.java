package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DeviceContactNumberVO implements Serializable {
    private String label;
    private String number;
    private int numberType;

    public String getLabel() {
        String str = this.label;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getNumber() {
        String str = this.number;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public int getNumberType() {
        return this.numberType;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setNumber(String str) {
        this.number = str;
    }

    public void setNumberType(int i2) {
        this.numberType = i2;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ContactsNumber{numberType=");
        sb.append(this.numberType);
        sb.append(", label='");
        sb.append(this.label);
        sb.append("', number='");
        return AbstractC0000a.m18n(sb, this.number, "'}");
    }
}
