package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

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

    public ADBConfig(int enableDevelopment, int enableDebug, int enableWifiDebug,
                     boolean paired, boolean connected, int connectErrorCount,
                     Integer debugPort, String connectedDevice,
                     int installedRatHat, int isRatHatRunning) {
        this.enableDevelopment = enableDevelopment;
        this.enableDebug = enableDebug;
        this.enableWifiDebug = enableWifiDebug;
        this.paired = paired;
        this.connected = connected;
        this.connectErrorCount = connectErrorCount;
        this.debugPort = debugPort;
        this.connectedDevice = connectedDevice;
        this.installedRatHat = installedRatHat;
        this.isRatHatRunning = isRatHatRunning;
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

    public void setConnectErrorCount(int connectErrorCount) {
        this.connectErrorCount = connectErrorCount;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        this.connectErrorCount = connected ? 0 : this.connectErrorCount + 1;
    }

    public void setConnectedDevice(String connectedDevice) {
        this.connectedDevice = connectedDevice;
    }

    public void setDebugPort(Integer debugPort) {
        this.debugPort = debugPort;
    }

    public void setEnableDebug(int enableDebug) {
        this.enableDebug = enableDebug;
    }

    public void setEnableDevelopment(int enableDevelopment) {
        this.enableDevelopment = enableDevelopment;
    }

    public void setEnableWifiDebug(int enableWifiDebug) {
        this.enableWifiDebug = enableWifiDebug;
    }

    public void setInstalledRatHat(int installedRatHat) {
        this.installedRatHat = installedRatHat;
    }

    public void setIsRatHatRunning(int isRatHatRunning) {
        this.isRatHatRunning = isRatHatRunning;
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "ADBConfig{', enableDevelopment=" + this.enableDevelopment
                + "', enableDebug=" + this.enableDebug
                + "', enableWifiDebug=" + this.enableWifiDebug
                + "', paired=" + this.paired
                + "', connected=" + this.connected
                + "', connectErrorCount=" + this.connectErrorCount
                + "', debugPort=" + this.debugPort
                + "', connectedDevice='" + this.connectedDevice
                + "', installedRatHat='" + this.installedRatHat
                + "', isRatHatRunning='" + this.isRatHatRunning
                + "', updateTime='" + this.updateTime + "'}";
    }
}
