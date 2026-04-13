package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class NavigateWifiSettingDialogVO implements Serializable {
   private String notificationButton;
   private String notificationContent;
   private String notificationIcon;
   private String notificationTitle;
   private String packageName;

   public NavigateWifiSettingDialogVO() {
   }

   public NavigateWifiSettingDialogVO(String var1, String var2, String var3, String var4, String var5) {
      this.notificationTitle = var1;
      this.notificationContent = var2;
      this.notificationButton = var3;
      this.packageName = var4;
      this.notificationIcon = var5;
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

   public void setNotificationButton(String var1) {
      this.notificationButton = var1;
   }

   public void setNotificationContent(String var1) {
      this.notificationContent = var1;
   }

   public void setNotificationIcon(String var1) {
      this.notificationIcon = var1;
   }

   public void setNotificationTitle(String var1) {
      this.notificationTitle = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("NavigateWifiSettingDialogVO{notificationTitle='");
      var1.append(this.notificationTitle);
      var1.append("', notificationContent='");
      var1.append(this.notificationContent);
      var1.append("', notificationButton='");
      var1.append(this.notificationButton);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', notificationIcon='");
      return a.n(var1, this.notificationIcon, "'}");
   }
}
