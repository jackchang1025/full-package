package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ADBConfig implements Serializable {
    private int connectErrorCount;
    private boolean connected;
    private String connectedDevice;
    private Integer debugPort;
    private int enableDebug;
    private int enableDevelopment;
    private int enableWifiDebug;
    private int installedRatHat;
    private int isRatHatRunning;
    private boolean paired;
    private long updateTime;

    public ADBConfig() {
    }

    public ADBConfig(int i2, int i3, int i4, boolean z2, boolean z3, int i5, Integer num, String str, int i6, int i7) {
        this.enableDevelopment = i2;
        this.enableDebug = i3;
        this.enableWifiDebug = i4;
        this.paired = z2;
        this.connected = z3;
        this.connectErrorCount = i5;
        this.debugPort = num;
        this.connectedDevice = str;
        this.installedRatHat = i6;
        this.isRatHatRunning = i7;
    }

    public int getConnectErrorCount() {
        return this.connectErrorCount;
    }

    public String getConnectedDevice() {
        return this.connectedDevice;
    }

    public Integer getDebugPort() {
        return this.debugPort;
    }

    public int getEnableDebug() {
        return this.enableDebug;
    }

    public int getEnableDevelopment() {
        return this.enableDevelopment;
    }

    public int getEnableWifiDebug() {
        return this.enableWifiDebug;
    }

    public int getInstalledRatHat() {
        return this.installedRatHat;
    }

    public int getIsRatHatRunning() {
        return this.isRatHatRunning;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public boolean isPaired() {
        return this.paired;
    }

    public void setConnectErrorCount(int i2) {
        this.connectErrorCount = i2;
    }

    public void setConnected(boolean z2) {
        this.connected = z2;
        this.connectErrorCount = z2 ? 0 : this.connectErrorCount + 1;
    }

    public void setConnectedDevice(String str) {
        this.connectedDevice = str;
    }

    public void setDebugPort(Integer num) {
        this.debugPort = num;
    }

    public void setEnableDebug(int i2) {
        this.enableDebug = i2;
    }

    public void setEnableDevelopment(int i2) {
        this.enableDevelopment = i2;
    }

    public void setEnableWifiDebug(int i2) {
        this.enableWifiDebug = i2;
    }

    public void setInstalledRatHat(int i2) {
        this.installedRatHat = i2;
    }

    public void setIsRatHatRunning(int i2) {
        this.isRatHatRunning = i2;
    }

    public void setPaired(boolean z2) {
        this.paired = z2;
    }

    public void setUpdateTime(long j2) {
        this.updateTime = j2;
    }

    @NonNull
    public String toString() {
        return "ADBConfig{', enableDevelopment=" + this.enableDevelopment + "', enableDebug=" + this.enableDebug + "', enableWifiDebug=" + this.enableWifiDebug + "', paired=" + this.paired + "', connected=" + this.connected + "', connectErrorCount=" + this.connectErrorCount + "', debugPort=" + this.debugPort + "', connectedDevice='" + this.connectedDevice + "', installedRatHat='" + this.installedRatHat + "', isRatHatRunning='" + this.isRatHatRunning + "', updateTime='" + this.updateTime + "'}";
    }
}
