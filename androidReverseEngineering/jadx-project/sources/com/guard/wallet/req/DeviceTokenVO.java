package com.guard.wallet.req;

import android.os.Build;
import android.support.annotation.NonNull;
import com.guard.wallet.MainApplication;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
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

    public static DeviceTokenVO of() {
        DeviceTokenVO deviceTokenVO = new DeviceTokenVO();
        deviceTokenVO.setDeviceUid(AbstractC0249e.m614c());
        deviceTokenVO.setBrandCode(Build.BRAND);
        deviceTokenVO.setApiGrade(Integer.valueOf(Build.VERSION.SDK_INT));
        deviceTokenVO.setPhoneNumber(AbstractC0249e.m625n());
        deviceTokenVO.setPackageName(MainApplication.getInstance().getPackageName());
        deviceTokenVO.setDeviceToken(AbstractC0252h.m708l("deviceToken"));
        deviceTokenVO.setLangCode(AbstractC0252h.m709m());
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
        StringBuilder sb = new StringBuilder("DeviceTokenVO{deviceId='");
        sb.append(this.deviceId);
        sb.append("', deviceUid='");
        sb.append(this.deviceUid);
        sb.append("', brandCode='");
        sb.append(this.brandCode);
        sb.append("', apiGrade=");
        sb.append(this.apiGrade);
        sb.append(", langCode='");
        sb.append(this.langCode);
        sb.append("', phoneNumber='");
        sb.append(this.phoneNumber);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', deviceToken='");
        return AbstractC0000a.m18n(sb, this.deviceToken, "'}");
    }
}
