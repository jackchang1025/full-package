package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ResStartApp implements Serializable {
    private String delegateId;
    boolean start;
    private String startMsg;
    private String startPackage;
    private boolean started;

    public ResStartApp() {
    }

    public ResStartApp(String str, boolean z2, boolean z3, String str2, String str3) {
        this.startPackage = str;
        this.start = z2;
        this.started = z3;
        this.delegateId = str2;
        this.startMsg = str3;
    }

    public String getDelegateId() {
        return this.delegateId;
    }

    public String getStartMsg() {
        return this.startMsg;
    }

    public String getStartPackage() {
        return this.startPackage;
    }

    public boolean isStart() {
        return this.start;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setDelegateId(String str) {
        this.delegateId = str;
    }

    public void setStart(boolean z2) {
        this.start = z2;
    }

    public void setStartMsg(String str) {
        this.startMsg = str;
    }

    public void setStartPackage(String str) {
        this.startPackage = str;
    }

    public void setStarted(boolean z2) {
        this.started = z2;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ResStartApp{start=");
        sb.append(this.start);
        sb.append(", startPackage=");
        sb.append(this.startPackage);
        sb.append(", started=");
        sb.append(this.started);
        sb.append(", startMsg=");
        sb.append(this.startMsg);
        sb.append(", delegateId='");
        return AbstractC0000a.m18n(sb, this.delegateId, "'}");
    }
}
