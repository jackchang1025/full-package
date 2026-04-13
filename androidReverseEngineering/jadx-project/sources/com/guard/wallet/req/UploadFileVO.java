package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class UploadFileVO implements Serializable {
    private String deviceId;
    private String spaceId;

    public UploadFileVO() {
    }

    public UploadFileVO(String str, String str2) {
        this.deviceId = str;
        this.spaceId = str2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setSpaceId(String str) {
        this.spaceId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("UploadFileVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', spaceId='");
        return AbstractC0000a.m18n(sb, this.spaceId, "'}");
    }
}
