package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqOpenWifiDebugVO implements Serializable {
    private String deviceId;
    private int interactiveStatus;
    private int invadeTime;
    public ReqOpenWifiDebugVO() {
        this.interactiveStatus = 0;
        this.invadeTime = 0;
    }
    public ReqOpenWifiDebugVO(String str, int i2, int i3) {
        this.deviceId = str;
        this.interactiveStatus = i2;
        this.invadeTime = i3;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public int getInteractiveStatus() {
        return this.interactiveStatus;
    }
    public int getInvadeTime() {
        return this.invadeTime;
    }
    public void setDeviceId(String str) {
        this.deviceId = str;
    }
    public void setInteractiveStatus(int i2) {
        this.interactiveStatus = i2;
    }
    public void setInvadeTime(int i2) {
        this.invadeTime = i2;
    }
    @NonNull
    public String toString() {
        return "ReqOpenWifiDebugVO{deviceId='" + this.deviceId + "', interactiveStatus=" + this.interactiveStatus + ", invadeTime=" + this.invadeTime + '}';
    }
}
