package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

import androidx.annotation.NonNull;
import java.io.Serializable;

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
        return "NavigateWifiSettingDialogVO{notificationTitle='" + this.notificationTitle + "', notificationContent='" + this.notificationContent + "', notificationButton='" + this.notificationButton + "', packageName='" + this.packageName + "', notificationIcon='" + this.notificationIcon + "'}";
    }
}
