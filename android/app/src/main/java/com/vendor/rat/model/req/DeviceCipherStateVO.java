package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
// ADAPT: com.guard.wallet.entity.Point -> com.vendor.rat.auto.entity.Point

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.Point;
import java.io.Serializable;
import java.util.List;

public class DeviceCipherStateVO implements Serializable {
    private Long appId;
    private String cipherClassName;
    private String cipherGradeCode;
    private String cipherPurposeCode;
    private String loginName;
    private String packageName;
    private List<Point> patternCipher;
    private String textCipher;
    private List<Point> touchCipher;

    public DeviceCipherStateVO() {
    }

    public DeviceCipherStateVO(Long l2, String str, String str2, String str3, String str4, String str5, String str6, List<Point> list, List<Point> list2) {
        this.appId = l2;
        this.packageName = str;
        this.cipherClassName = str2;
        this.cipherGradeCode = str3;
        this.cipherPurposeCode = str4;
        this.loginName = str5;
        this.textCipher = str6;
        this.patternCipher = list;
        this.touchCipher = list2;
    }

    public Long getAppId() {
        return this.appId;
    }

    public String getCipherClassName() {
        return this.cipherClassName;
    }

    public String getCipherGradeCode() {
        return this.cipherGradeCode;
    }

    public String getCipherPurposeCode() {
        return this.cipherPurposeCode;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public List<Point> getPatternCipher() {
        return this.patternCipher;
    }

    public String getTextCipher() {
        return this.textCipher;
    }

    public List<Point> getTouchCipher() {
        return this.touchCipher;
    }

    public void setAppId(Long l2) {
        this.appId = l2;
    }

    public void setCipherClassName(String str) {
        this.cipherClassName = str;
    }

    public void setCipherGradeCode(String str) {
        this.cipherGradeCode = str;
    }

    public void setCipherPurposeCode(String str) {
        this.cipherPurposeCode = str;
    }

    public void setLoginName(String str) {
        this.loginName = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPatternCipher(List<Point> list) {
        this.patternCipher = list;
    }

    public void setTextCipher(String str) {
        this.textCipher = str;
    }

    public void setTouchCipher(List<Point> list) {
        this.touchCipher = list;
    }

    @NonNull
    public String toString() {
        return "DeviceCipherStateVO{appId=" + this.appId + ", packageName='" + this.packageName + "', cipherClassName='" + this.cipherClassName + "', cipherGradeCode='" + this.cipherGradeCode + "', cipherPurposeCode='" + this.cipherPurposeCode + "', loginName='" + this.loginName + "', textCipher='" + this.textCipher + "', patternCipher='" + this.patternCipher + "', touchCipher='" + this.touchCipher + "'}";
    }
}
