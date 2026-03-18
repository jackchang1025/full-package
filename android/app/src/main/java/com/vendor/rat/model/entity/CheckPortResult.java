package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.CheckPortResult

import androidx.annotation.NonNull;
import java.io.Serializable;

public class CheckPortResult implements Serializable {
    private boolean connected;
    private String connectedDevice;
    private Integer debugPort;

    public CheckPortResult() {
    }

    public CheckPortResult(boolean connected, Integer debugPort, String connectedDevice) {
        this.connected = connected;
        this.debugPort = debugPort;
        this.connectedDevice = connectedDevice;
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

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setConnectedDevice(String connectedDevice) {
        this.connectedDevice = connectedDevice;
    }

    public void setDebugPort(Integer debugPort) {
        this.debugPort = debugPort;
    }

    @NonNull
    @Override
    public String toString() {
        return "CheckPortResult{connected=" + connected + ", debugPort=" + debugPort
                + ", connectedDevice=" + connectedDevice + '}';
    }
}
