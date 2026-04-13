package com.guard.wallet.req;

/* loaded from: classes.dex */
public class NetStateVO extends MessageBodyVO {
    private Integer isConnected;
    private Integer isWifiConnected;
    private String localIp;
    private String macAddress;
    private String wifiId;

    public NetStateVO() {
        this.isConnected = -1;
        this.isWifiConnected = -1;
    }

    public Integer getIsConnected() {
        return this.isConnected;
    }

    public Integer getIsWifiConnected() {
        return this.isWifiConnected;
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

    public void setIsConnected(Integer num) {
        this.isConnected = num;
    }

    public void setIsWifiConnected(Integer num) {
        this.isWifiConnected = num;
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

    public NetStateVO(Integer num, Integer num2, String str, String str2, String str3) {
        this.isConnected = -1;
        this.isConnected = num;
        this.isWifiConnected = num2;
        this.macAddress = str;
        this.wifiId = str2;
        this.localIp = str3;
    }
}
