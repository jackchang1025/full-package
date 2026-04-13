package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqWifiSettingDialogVO implements Serializable {
    private String deviceId;

    public ReqWifiSettingDialogVO(String str) {
        this.deviceId = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    @NonNull
    public String toString() {
        return AbstractC0000a.m18n(new StringBuilder("ReqWifiSettingDialogVO{deviceId='"), this.deviceId, "'}");
    }
}
