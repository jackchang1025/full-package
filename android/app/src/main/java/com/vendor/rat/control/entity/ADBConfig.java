package com.vendor.rat.control.entity;

import androidx.annotation.NonNull;
import java.io.Serializable;

/**
 * ADB 配置状态
 * vendor: com.guard.wallet.entity.ADBConfig
 */
public class ADBConfig implements Serializable {
    private int enableDevelopment;
    private int enableDebug;
    private int enableWifiDebug;
    private boolean paired;
    private boolean connected;
    private int connectErrorCount;
    private Integer debugPort;
    private String connectedDevice;
    private int installedRatHat;
    private int isRatHatRunning;
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

    public int getEnableDevelopment() { return this.enableDevelopment; }
    public void setEnableDevelopment(int v) { this.enableDevelopment = v; }
    public int getEnableDebug() { return this.enableDebug; }
    public void setEnableDebug(int v) { this.enableDebug = v; }
    public int getEnableWifiDebug() { return this.enableWifiDebug; }
    public void setEnableWifiDebug(int v) { this.enableWifiDebug = v; }
    public boolean isPaired() { return this.paired; }
    public void setPaired(boolean v) { this.paired = v; }
    public boolean isConnected() { return this.connected; }
    public void setConnected(boolean v) {
        this.connected = v;
        this.connectErrorCount = v ? 0 : this.connectErrorCount + 1;
    }
    public int getConnectErrorCount() { return this.connectErrorCount; }
    public void setConnectErrorCount(int v) { this.connectErrorCount = v; }
    public Integer getDebugPort() { return this.debugPort; }
    public void setDebugPort(Integer v) { this.debugPort = v; }
    public String getConnectedDevice() { return this.connectedDevice; }
    public void setConnectedDevice(String v) { this.connectedDevice = v; }
    public int getInstalledRatHat() { return this.installedRatHat; }
    public void setInstalledRatHat(int v) { this.installedRatHat = v; }
    public int getIsRatHatRunning() { return this.isRatHatRunning; }
    public void setIsRatHatRunning(int v) { this.isRatHatRunning = v; }
    public long getUpdateTime() { return this.updateTime; }
    public void setUpdateTime(long v) { this.updateTime = v; }

    @NonNull
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
