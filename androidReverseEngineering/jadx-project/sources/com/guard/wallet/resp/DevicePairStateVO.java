package com.guard.wallet.resp;

import android.os.Build;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import p005h.C0318e;

/* loaded from: classes.dex */
public class DevicePairStateVO implements Serializable {
    private Integer deviceConnected;
    private Integer deviceDebugPort;
    private Integer devicePaired;
    private Integer isWifiConnected;
    private Integer netConnected;
    private Integer ratHatImplant;
    private Integer ratHatRunning;
    private Integer supportPair;
    private String wifiId;

    public DevicePairStateVO() {
    }

    public DevicePairStateVO(Integer num, Integer num2, String str, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, Integer num8) {
        this.netConnected = num;
        this.isWifiConnected = num2;
        this.wifiId = str;
        this.supportPair = num3;
        this.devicePaired = num4;
        this.deviceConnected = num5;
        this.deviceDebugPort = num6;
        this.ratHatImplant = num7;
        this.ratHatRunning = num8;
    }

    public static DevicePairStateVO of() {
        DevicePairStateVO devicePairStateVO = new DevicePairStateVO();
        NetStateVO z02 = AbstractC0251g.z0();
        devicePairStateVO.setNetConnected(z02.getIsConnected());
        devicePairStateVO.setIsWifiConnected(z02.getIsWifiConnected());
        devicePairStateVO.setWifiId(z02.getWifiId());
        if (Build.VERSION.SDK_INT > 29) {
            devicePairStateVO.setSupportPair(1);
        } else {
            devicePairStateVO.setSupportPair(0);
        }
        ADBConfig m689J = AbstractC0252h.m689J();
        devicePairStateVO.setRatHatImplant(Integer.valueOf(m689J.getInstalledRatHat()));
        devicePairStateVO.setDevicePaired(Integer.valueOf(m689J.isPaired() ? 1 : 0));
        devicePairStateVO.setDeviceConnected(Integer.valueOf(m689J.isConnected() ? 1 : 0));
        devicePairStateVO.setDeviceDebugPort(m689J.getDebugPort());
        if (C0318e.m844S() == null || !C0318e.m844S().f608B.get()) {
            devicePairStateVO.setRatHatRunning(0);
        } else {
            devicePairStateVO.setRatHatImplant(1);
            devicePairStateVO.setRatHatRunning(1);
        }
        return devicePairStateVO;
    }

    public Integer getDeviceConnected() {
        return this.deviceConnected;
    }

    public Integer getDeviceDebugPort() {
        return this.deviceDebugPort;
    }

    public Integer getDevicePaired() {
        return this.devicePaired;
    }

    public Integer getIsWifiConnected() {
        return this.isWifiConnected;
    }

    public Integer getNetConnected() {
        return this.netConnected;
    }

    public Integer getRatHatImplant() {
        return this.ratHatImplant;
    }

    public Integer getRatHatRunning() {
        return this.ratHatRunning;
    }

    public Integer getSupportPair() {
        return this.supportPair;
    }

    public String getWifiId() {
        return this.wifiId;
    }

    public void setDeviceConnected(Integer num) {
        this.deviceConnected = num;
    }

    public void setDeviceDebugPort(Integer num) {
        this.deviceDebugPort = num;
    }

    public void setDevicePaired(Integer num) {
        this.devicePaired = num;
    }

    public void setIsWifiConnected(Integer num) {
        this.isWifiConnected = num;
    }

    public void setNetConnected(Integer num) {
        this.netConnected = num;
    }

    public void setRatHatImplant(Integer num) {
        this.ratHatImplant = num;
    }

    public void setRatHatRunning(Integer num) {
        this.ratHatRunning = num;
    }

    public void setSupportPair(Integer num) {
        this.supportPair = num;
    }

    public void setWifiId(String str) {
        this.wifiId = str;
    }

    @NonNull
    public String toString() {
        return "DevicePairStateVO{netConnected=" + this.netConnected + ", isWifiConnected=" + this.isWifiConnected + ", wifiId='" + this.wifiId + "', supportPair=" + this.supportPair + ", devicePaired=" + this.devicePaired + ", deviceConnected=" + this.deviceConnected + ", deviceDebugPort=" + this.deviceDebugPort + ", ratHatImplant=" + this.ratHatImplant + ", ratHatRunning=" + this.ratHatRunning + '}';
    }
}
