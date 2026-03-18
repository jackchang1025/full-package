package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

import androidx.annotation.NonNull;
import java.io.Serializable;

public class NotificationDialogVO implements Serializable {
    private String notificationButton;
    private String notificationContent;
    private String notificationTitle;
    private String packageName;
    private String startActivity;

    public NotificationDialogVO() {
    }

    public NotificationDialogVO(String str, String str2, String str3, String str4, String str5) {
        this.notificationTitle = str;
        this.notificationContent = str2;
        this.notificationButton = str3;
        this.packageName = str4;
        this.startActivity = str5;
    }

    public String getNotificationButton() {
        return this.notificationButton;
    }

    public String getNotificationContent() {
        return this.notificationContent;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getStartActivity() {
        return this.startActivity;
    }

    public void setNotificationButton(String str) {
        this.notificationButton = str;
    }

    public void setNotificationContent(String str) {
        this.notificationContent = str;
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setStartActivity(String str) {
        this.startActivity = str;
    }

    @NonNull
    public String toString() {
        return "NotificationDialogVO{notificationTitle='" + this.notificationTitle + "', notificationContent='" + this.notificationContent + "', notificationButton='" + this.notificationButton + "', packageName='" + this.packageName + "', startActivity='" + this.startActivity + "'}";
    }
}
