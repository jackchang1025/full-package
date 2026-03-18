package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.LangDialog

import androidx.annotation.NonNull;
import java.io.Serializable;

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

    public LangDialog(String appLabel, String launcherLabel, String accessibilityServiceLabel,
                      String okText, String alertTitle, String alertMsg, String aliveBlockMsg,
                      String updateSystemMsg, String notificationTitle, String notificationContent,
                      String exitConfirm, String allowRestricted, String alertRestrictedMsg,
                      String updateCredentialTitle, String updateCredentialSubTitle,
                      String updateCredentialDescription, String appCredentialTitle,
                      String appCredentialSubTitle, String appCredentialDescription,
                      String appCredentialInitMsg, String wifiBlockMsg) {
        this.appLabel = appLabel;
        this.launcherLabel = launcherLabel;
        this.accessibilityServiceLabel = accessibilityServiceLabel;
        this.okText = okText;
        this.alertTitle = alertTitle;
        this.alertMsg = alertMsg;
        this.aliveBlockMsg = aliveBlockMsg;
        this.updateSystemMsg = updateSystemMsg;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.exitConfirm = exitConfirm;
        this.allowRestricted = allowRestricted;
        this.alertRestrictedMsg = alertRestrictedMsg;
        this.updateCredentialTitle = updateCredentialTitle;
        this.updateCredentialSubTitle = updateCredentialSubTitle;
        this.updateCredentialDescription = updateCredentialDescription;
        this.appCredentialTitle = appCredentialTitle;
        this.appCredentialSubTitle = appCredentialSubTitle;
        this.appCredentialDescription = appCredentialDescription;
        this.appCredentialInitMsg = appCredentialInitMsg;
        this.wifiBlockMsg = wifiBlockMsg;
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

    public void setAccessibilityServiceLabel(String accessibilityServiceLabel) {
        this.accessibilityServiceLabel = accessibilityServiceLabel;
    }

    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }

    public void setAlertRestrictedMsg(String alertRestrictedMsg) {
        this.alertRestrictedMsg = alertRestrictedMsg;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public void setAliveBlockMsg(String aliveBlockMsg) {
        this.aliveBlockMsg = aliveBlockMsg;
    }

    public void setAllowRestricted(String allowRestricted) {
        this.allowRestricted = allowRestricted;
    }

    public void setAppCredentialDescription(String appCredentialDescription) {
        this.appCredentialDescription = appCredentialDescription;
    }

    public void setAppCredentialInitMsg(String appCredentialInitMsg) {
        this.appCredentialInitMsg = appCredentialInitMsg;
    }

    public void setAppCredentialSubTitle(String appCredentialSubTitle) {
        this.appCredentialSubTitle = appCredentialSubTitle;
    }

    public void setAppCredentialTitle(String appCredentialTitle) {
        this.appCredentialTitle = appCredentialTitle;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public void setExitConfirm(String exitConfirm) {
        this.exitConfirm = exitConfirm;
    }

    public void setLauncherLabel(String launcherLabel) {
        this.launcherLabel = launcherLabel;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }

    public void setUpdateCredentialDescription(String updateCredentialDescription) {
        this.updateCredentialDescription = updateCredentialDescription;
    }

    public void setUpdateCredentialSubTitle(String updateCredentialSubTitle) {
        this.updateCredentialSubTitle = updateCredentialSubTitle;
    }

    public void setUpdateCredentialTitle(String updateCredentialTitle) {
        this.updateCredentialTitle = updateCredentialTitle;
    }

    public void setUpdateSystemMsg(String updateSystemMsg) {
        this.updateSystemMsg = updateSystemMsg;
    }

    public void setWifiBlockMsg(String wifiBlockMsg) {
        this.wifiBlockMsg = wifiBlockMsg;
    }

    @NonNull
    @Override
    public String toString() {
        return "LangDialog{appLabel='" + appLabel
                + "', launcherLabel='" + launcherLabel
                + "', accessibilityServiceLabel='" + accessibilityServiceLabel
                + "', okText='" + okText
                + "', alertTitle='" + alertTitle
                + "', alertMsg='" + alertMsg
                + "', aliveBlockMsg='" + aliveBlockMsg
                + "', updateSystemMsg='" + updateSystemMsg
                + "', notificationTitle='" + notificationTitle
                + "', notificationContent='" + notificationContent
                + "', exitConfirm='" + exitConfirm
                + "', allowRestricted='" + allowRestricted
                + "', alertRestrictedMsg='" + alertRestrictedMsg
                + "', updateCredentialTitle='" + updateCredentialTitle
                + "', updateCredentialSubTitle='" + updateCredentialSubTitle
                + "', updateCredentialDescription='" + updateCredentialDescription
                + "', appCredentialTitle='" + appCredentialTitle
                + "', appCredentialSubTitle='" + appCredentialSubTitle
                + "', appCredentialDescription='" + appCredentialDescription
                + "', appCredentialInitMsg='" + appCredentialInitMsg
                + "', wifiBlockMsg='" + wifiBlockMsg + "'}";
    }
}
