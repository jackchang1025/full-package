package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class NavigateWifiSettingDialogVO implements Serializable {
    private String notificationButton;
    private String notificationContent;
    private String notificationIcon;
    private String notificationTitle;
    private String packageName;

    public NavigateWifiSettingDialogVO() {
    }

    public NavigateWifiSettingDialogVO(String str, String str2, String str3, String str4, String str5) {
        this.notificationTitle = str;
        this.notificationContent = str2;
        this.notificationButton = str3;
        this.packageName = str4;
        this.notificationIcon = str5;
    }

    public String getNotificationButton() {
        return this.notificationButton;
    }

    public String getNotificationContent() {
        return this.notificationContent;
    }

    public String getNotificationIcon() {
        return this.notificationIcon;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setNotificationButton(String str) {
        this.notificationButton = str;
    }

    public void setNotificationContent(String str) {
        this.notificationContent = str;
    }

    public void setNotificationIcon(String str) {
        this.notificationIcon = str;
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("NavigateWifiSettingDialogVO{notificationTitle='");
        sb.append(this.notificationTitle);
        sb.append("', notificationContent='");
        sb.append(this.notificationContent);
        sb.append("', notificationButton='");
        sb.append(this.notificationButton);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', notificationIcon='");
        return AbstractC0000a.m18n(sb, this.notificationIcon, "'}");
    }
}
