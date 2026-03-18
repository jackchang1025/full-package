package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.DeviceMediaStoreImageVO
import androidx.annotation.NonNull;
import java.io.Serializable;

public class DeviceMediaStoreImageVO implements Serializable {
    private String deviceId;
    private String displayName;
    private String spaceId;

    public DeviceMediaStoreImageVO() {
    }

    public DeviceMediaStoreImageVO(String deviceId, String displayName, String spaceId) {
        this.deviceId = deviceId;
        this.displayName = displayName;
        this.spaceId = spaceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @NonNull
    public String toString() {
        return "DeviceMediaStoreImageVO{deviceId='" + this.deviceId
                + "', displayName='" + this.displayName
                + "', spaceId='" + this.spaceId + "'}";
    }
}
