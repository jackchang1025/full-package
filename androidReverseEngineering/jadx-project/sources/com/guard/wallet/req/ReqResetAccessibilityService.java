package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReqResetAccessibilityService implements Serializable {
    private String serviceName;

    public ReqResetAccessibilityService() {
    }

    public ReqResetAccessibilityService(String str) {
        this.serviceName = str;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    @NonNull
    public String toString() {
        return AbstractC0000a.m18n(new StringBuilder("ReqResetAccessibilityService{serviceName='"), this.serviceName, "'}");
    }
}
