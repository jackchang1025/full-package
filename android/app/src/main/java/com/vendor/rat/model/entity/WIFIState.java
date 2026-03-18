package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.WIFIState

import androidx.annotation.NonNull;
import java.io.Serializable;

public class WIFIState implements Serializable {
    private String localIp;
    private String macAddress;
    private String wifiId;

    public WIFIState() {
    }

    public WIFIState(String macAddress, String wifiId, String localIp) {
        this.macAddress = macAddress;
        this.wifiId = wifiId;
        this.localIp = localIp;
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

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setWifiId(String wifiId) {
        this.wifiId = wifiId;
    }

    @NonNull
    @Override
    public String toString() {
        return "WIFIState{macAddress='" + macAddress
                + "', wifiId='" + wifiId
                + "', localIp='" + localIp + "'}";
    }
}
