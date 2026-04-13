package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

/* loaded from: classes.dex */
public class DeviceAdminVO extends MessageBodyVO {
    private Integer isAdminActive;
    private Integer isDeviceOwner;
    private Integer isProfileOwner;
    private String packageName;

    public DeviceAdminVO() {
    }

    public DeviceAdminVO(String str, Integer num, Integer num2, Integer num3) {
        this.packageName = str;
        this.isAdminActive = num;
        this.isDeviceOwner = num2;
        this.isProfileOwner = num3;
    }

    public Integer getIsAdminActive() {
        return this.isAdminActive;
    }

    public Integer getIsDeviceOwner() {
        return this.isDeviceOwner;
    }

    public Integer getIsProfileOwner() {
        return this.isProfileOwner;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setIsAdminActive(Integer num) {
        this.isAdminActive = num;
    }

    public void setIsDeviceOwner(Integer num) {
        this.isDeviceOwner = num;
    }

    public void setIsProfileOwner(Integer num) {
        this.isProfileOwner = num;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    @NonNull
    public String toString() {
        return "DeviceAdminVO{packageName=" + this.packageName + ",isAdminActive=" + this.isAdminActive + ", isDeviceOwner=" + this.isDeviceOwner + ", isProfileOwner=" + this.isProfileOwner + '}';
    }
}
