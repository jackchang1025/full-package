package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
// ADAPT: static of() removed - depends on vendor utils (e.class, h.class)

import android.os.Build;
import androidx.annotation.NonNull;
import java.io.Serializable;

public class DeviceUpdateVO implements Serializable {
    private Integer apiGrade;
    private String brandCode;
    private String deviceId;
    private String deviceUid;
    private String langCode;
    private String phoneNumber;

    public DeviceUpdateVO() {
    }

    public DeviceUpdateVO(String str, String str2, String str3, Integer num, String str4, String str5) {
        this.deviceId = str;
        this.deviceUid = str2;
        this.brandCode = str3;
        this.apiGrade = num;
        this.langCode = str4;
        this.phoneNumber = str5;
    }

    // TODO: VENDOR_VERIFY - static of() depends on vendor utils e.c(), e.n(), h.m()
    public static DeviceUpdateVO of() {
        DeviceUpdateVO deviceUpdateVO = new DeviceUpdateVO();
        deviceUpdateVO.setBrandCode(Build.BRAND);
        deviceUpdateVO.setApiGrade(Integer.valueOf(Build.VERSION.SDK_INT));
        return deviceUpdateVO;
    }

    public Integer getApiGrade() {
        return this.apiGrade;
    }

    public String getBrandCode() {
        return this.brandCode;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getDeviceUid() {
        return this.deviceUid;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setApiGrade(Integer num) {
        this.apiGrade = num;
    }

    public void setBrandCode(String str) {
        this.brandCode = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setDeviceUid(String str) {
        this.deviceUid = str;
    }

    public void setLangCode(String str) {
        this.langCode = str;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    @NonNull
    public String toString() {
        return "DeviceUpdateVO{deviceId='" + this.deviceId + "', deviceUid='" + this.deviceUid + "', brandCode='" + this.brandCode + "', apiGrade=" + this.apiGrade + ", langCode='" + this.langCode + "', phoneNumber='" + this.phoneNumber + "'}";
    }
}
