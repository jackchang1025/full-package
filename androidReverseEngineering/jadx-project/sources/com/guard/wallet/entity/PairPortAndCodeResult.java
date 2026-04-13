package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PairPortAndCodeResult {
    private String host;
    private String pairCode;
    private Integer pairPort;

    public PairPortAndCodeResult() {
    }

    public PairPortAndCodeResult(String str, Integer num, String str2) {
        this.host = str;
        this.pairPort = num;
        this.pairCode = str2;
    }

    public String getHost() {
        return this.host;
    }

    public String getPairCode() {
        return this.pairCode;
    }

    public Integer getPairPort() {
        return this.pairPort;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setPairCode(String str) {
        this.pairCode = str;
    }

    public void setPairPort(Integer num) {
        this.pairPort = num;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("PairPortAndCodeResult{pairPort=");
        sb.append(this.pairPort);
        sb.append(", pairCode='");
        sb.append(this.pairCode);
        sb.append("', host='");
        return AbstractC0000a.m18n(sb, this.host, "'}");
    }
}
