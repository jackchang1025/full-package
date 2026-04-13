package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class CheckPortResult implements Serializable {
    private boolean connected;
    private String connectedDevice;
    private Integer debugPort;

    public CheckPortResult() {
    }

    public CheckPortResult(boolean z2, Integer num, String str) {
        this.connected = z2;
        this.debugPort = num;
        this.connectedDevice = str;
    }

    public String getConnectedDevice() {
        return this.connectedDevice;
    }

    public Integer getDebugPort() {
        return this.debugPort;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean z2) {
        this.connected = z2;
    }

    public void setConnectedDevice(String str) {
        this.connectedDevice = str;
    }

    public void setDebugPort(Integer num) {
        this.debugPort = num;
    }

    @NonNull
    public String toString() {
        return "CheckPortResult{connected=" + this.connected + ", debugPort=" + this.debugPort + ", connectedDevice=" + this.connectedDevice + '}';
    }
}
