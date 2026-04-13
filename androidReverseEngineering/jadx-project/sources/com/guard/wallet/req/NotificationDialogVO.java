package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
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
        StringBuilder sb = new StringBuilder("NotificationDialogVO{notificationTitle='");
        sb.append(this.notificationTitle);
        sb.append("', notificationContent='");
        sb.append(this.notificationContent);
        sb.append("', notificationButton='");
        sb.append(this.notificationButton);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', startActivity='");
        return AbstractC0000a.m18n(sb, this.startActivity, "'}");
    }
}
