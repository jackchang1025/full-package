package com.guard.wallet.req;

import android.os.Build;
import android.support.annotation.NonNull;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
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

    public static DeviceUpdateVO of() {
        DeviceUpdateVO deviceUpdateVO = new DeviceUpdateVO();
        deviceUpdateVO.setDeviceUid(AbstractC0249e.m614c());
        deviceUpdateVO.setBrandCode(Build.BRAND);
        deviceUpdateVO.setApiGrade(Integer.valueOf(Build.VERSION.SDK_INT));
        deviceUpdateVO.setPhoneNumber(AbstractC0249e.m625n());
        deviceUpdateVO.setLangCode(AbstractC0252h.m709m());
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
        StringBuilder sb = new StringBuilder("DeviceUpdateVO{deviceId='");
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
        return AbstractC0000a.m18n(sb, this.phoneNumber, "'}");
    }
}
