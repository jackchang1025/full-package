package com.guard.wallet.resp;

import java.io.Serializable;

public class DeviceMediaStoreImageVO implements Serializable {
    private String deviceId;
    private String displayName;
    private String spaceId;

    public String getDeviceId() { return this.deviceId; }
    public String getDisplayName() { return this.displayName; }
    public String getSpaceId() { return this.spaceId; }

    public void setDeviceId(String v) { this.deviceId = v; }
    public void setDisplayName(String v) { this.displayName = v; }
    public void setSpaceId(String v) { this.spaceId = v; }
}
