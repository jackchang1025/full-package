package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class WIFIState implements Serializable {
    private String localIp;
    private String macAddress;
    private String wifiId;

    public WIFIState() {
    }

    public WIFIState(String str, String str2, String str3) {
        this.macAddress = str;
        this.wifiId = str2;
        this.localIp = str3;
    }

    public String getLocalIp() {
        return this.localIp;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public String getWifiId() {
        return this.wifiId;
    }

    public void setLocalIp(String str) {
        this.localIp = str;
    }

    public void setMacAddress(String str) {
        this.macAddress = str;
    }

    public void setWifiId(String str) {
        this.wifiId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("WIFIState{macAddress='");
        sb.append(this.macAddress);
        sb.append("', wifiId='");
        sb.append(this.wifiId);
        sb.append("', localIp='");
        return AbstractC0000a.m18n(sb, this.localIp, "'}");
    }
}
