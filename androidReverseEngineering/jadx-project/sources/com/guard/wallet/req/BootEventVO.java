package com.guard.wallet.req;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class BootEventVO extends MessageBodyVO {
    private Integer hasReceiveCompleted;
    private String packageName;

    public BootEventVO() {
    }

    public BootEventVO(String str, Integer num) {
        this.packageName = str;
        this.hasReceiveCompleted = num;
    }

    public Integer getHasReceiveCompleted() {
        return this.hasReceiveCompleted;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setHasReceiveCompleted(Integer num) {
        this.hasReceiveCompleted = num;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    @NonNull
    public String toString() {
        return "BootEventVO{packageName='" + this.packageName + "', hasReceiveCompleted=" + this.hasReceiveCompleted + '}';
    }
}
