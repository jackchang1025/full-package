package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
// ADAPT: static of() simplified - depends on vendor MainApplication, utils e/h

import android.os.Build;
import androidx.annotation.NonNull;
import java.io.Serializable;

public class DeviceTokenVO implements Serializable {
    private Integer apiGrade;
    private String brandCode;
    private String deviceId;
    private String deviceToken;
    private String deviceUid;
    private String langCode;
    private String packageName;
    private String phoneNumber;

    public DeviceTokenVO() {
    }

    public DeviceTokenVO(String str, String str2, String str3, Integer num, String str4, String str5, String str6, String str7) {
        this.deviceId = str;
        this.deviceUid = str2;
        this.brandCode = str3;
        this.apiGrade = num;
        this.langCode = str4;
        this.phoneNumber = str5;
        this.packageName = str6;
        this.deviceToken = str7;
    }

    // TODO: VENDOR_VERIFY - static of() depends on vendor MainApplication, utils e/h
    public static DeviceTokenVO of() {
        DeviceTokenVO deviceTokenVO = new DeviceTokenVO();
        deviceTokenVO.setBrandCode(Build.BRAND);
        deviceTokenVO.setApiGrade(Integer.valueOf(Build.VERSION.SDK_INT));
        return deviceTokenVO;
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

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public String getDeviceUid() {
        return this.deviceUid;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public String getPackageName() {
        return this.packageName;
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

    public void setDeviceToken(String str) {
        this.deviceToken = str;
    }

    public void setDeviceUid(String str) {
        this.deviceUid = str;
    }

    public void setLangCode(String str) {
        this.langCode = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    @NonNull
    public String toString() {
        return "DeviceTokenVO{deviceId='" + this.deviceId + "', deviceUid='" + this.deviceUid + "', brandCode='" + this.brandCode + "', apiGrade=" + this.apiGrade + ", langCode='" + this.langCode + "', phoneNumber='" + this.phoneNumber + "', packageName='" + this.packageName + "', deviceToken='" + this.deviceToken + "'}";
    }
}
