package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class AppInfo extends MessageBodyVO {
   private String appClassName;
   private String applicationLabel;
   private Integer externalApp;
   private Integer isEnable;
   private String mainAction;
   private String mainClassName;
   private String packageName;
   private String permission;
   private String processName;
   private Integer systemApp;
   private Integer uninstalled;

   public AppInfo() {
   }

   public AppInfo(
      String var1, String var2, String var3, String var4, String var5, String var6, String var7, Integer var8, Integer var9, Integer var10, Integer var11
   ) {
      this.packageName = var1;
      this.mainClassName = var2;
      this.mainAction = var3;
      this.permission = var4;
      this.processName = var5;
      this.appClassName = var6;
      this.applicationLabel = var7;
      this.isEnable = var8;
      this.uninstalled = var9;
      this.systemApp = var10;
      this.externalApp = var11;
   }

   public String getAppClassName() {
      return this.appClassName;
   }

   public String getApplicationLabel() {
      return this.applicationLabel;
   }

   public Integer getExternalApp() {
      return this.externalApp;
   }

   public Integer getIsEnable() {
      return this.isEnable;
   }

   public String getMainAction() {
      return this.mainAction;
   }

   public String getMainClassName() {
      return this.mainClassName;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getPermission() {
      return this.permission;
   }

   public String getProcessName() {
      return this.processName;
   }

   public Integer getSystemApp() {
      return this.systemApp;
   }

   public Integer getUninstalled() {
      return this.uninstalled;
   }

   public void setAppClassName(String var1) {
      this.appClassName = var1;
   }

   public void setApplicationLabel(String var1) {
      this.applicationLabel = var1;
   }

   public void setExternalApp(Integer var1) {
      this.externalApp = var1;
   }

   public void setIsEnable(Integer var1) {
      this.isEnable = var1;
   }

   public void setMainAction(String var1) {
      this.mainAction = var1;
   }

   public void setMainClassName(String var1) {
      this.mainClassName = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPermission(String var1) {
      this.permission = var1;
   }

   public void setProcessName(String var1) {
      this.processName = var1;
   }

   public void setSystemApp(Integer var1) {
      this.systemApp = var1;
   }

   public void setUninstalled(Integer var1) {
      this.uninstalled = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("AppInfo{packageName='");
      var1.append(this.packageName);
      var1.append("', mainClassName='");
      var1.append(this.mainClassName);
      var1.append("', mainAction='");
      var1.append(this.mainAction);
      var1.append("', permission='");
      var1.append(this.permission);
      var1.append("', processName='");
      var1.append(this.processName);
      var1.append("', appClassName='");
      var1.append(this.appClassName);
      var1.append("', applicationLabel='");
      var1.append(this.applicationLabel);
      var1.append("', isEnable=");
      var1.append(this.isEnable);
      var1.append("', uninstalled=");
      var1.append(this.uninstalled);
      var1.append("', systemApp=");
      var1.append(this.systemApp);
      var1.append("', externalApp=");
      var1.append(this.externalApp);
      var1.append('}');
      return var1.toString();
   }
}
