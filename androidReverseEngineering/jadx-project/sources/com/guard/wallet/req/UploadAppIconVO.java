package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class UploadAppIconVO implements Serializable {
    private String deviceId;
    private String packageName;
    private String spaceId;

    public UploadAppIconVO(String str, String str2, String str3) {
        this.deviceId = str;
        this.packageName = str2;
        this.spaceId = str3;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setSpaceId(String str) {
        this.spaceId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("UploadAppIconVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', spaceId='");
        return AbstractC0000a.m18n(sb, this.spaceId, "'}");
    }
}
