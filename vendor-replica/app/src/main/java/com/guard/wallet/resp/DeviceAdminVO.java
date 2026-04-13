package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceAdminVO extends MessageBodyVO {
    private Integer isAdminActive;
    private Integer isDeviceOwner;
    private Integer isProfileOwner;
    private String packageName;

    public DeviceAdminVO() {}
    public DeviceAdminVO(String packageName, Integer isAdminActive, Integer isDeviceOwner, Integer isProfileOwner) {
        this.packageName = packageName; this.isAdminActive = isAdminActive;
        this.isDeviceOwner = isDeviceOwner; this.isProfileOwner = isProfileOwner;
    }

    public Integer getIsAdminActive() { return this.isAdminActive; }
    public Integer getIsDeviceOwner() { return this.isDeviceOwner; }
    public Integer getIsProfileOwner() { return this.isProfileOwner; }
    public String getPackageName() { return this.packageName; }
    public void setIsAdminActive(Integer v) { this.isAdminActive = v; }
    public void setIsDeviceOwner(Integer v) { this.isDeviceOwner = v; }
    public void setIsProfileOwner(Integer v) { this.isProfileOwner = v; }
    public void setPackageName(String v) { this.packageName = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceAdminVO{packageName=" + this.packageName + ",isAdminActive=" + this.isAdminActive
                + ", isDeviceOwner=" + this.isDeviceOwner + ", isProfileOwner=" + this.isProfileOwner + "}";
    }
}
