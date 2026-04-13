package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class PowerControlStateVO extends MessageBodyVO {
   private Boolean allowAllFullBackground;
   private Boolean allowAutoStart;
   private Boolean allowPopupInBackground;
   private Boolean allowRelateStart;
   private String deviceId;
   private String packageName;
   private int retryCount;

   public PowerControlStateVO() {
      Boolean var1 = Boolean.FALSE;
      this.allowAllFullBackground = var1;
      this.allowPopupInBackground = var1;
      this.allowAutoStart = var1;
      this.allowRelateStart = var1;
      this.retryCount = 0;
   }

   public PowerControlStateVO(String var1, String var2, Boolean var3, Boolean var4, Boolean var5, Boolean var6, int var7) {
      this.deviceId = var1;
      this.packageName = var2;
      this.allowAllFullBackground = var3;
      this.allowPopupInBackground = var4;
      this.allowAutoStart = var5;
      this.allowRelateStart = var6;
      this.retryCount = var7;
   }

   public Boolean getAllowAllFullBackground() {
      return this.allowAllFullBackground;
   }

   public Boolean getAllowAutoStart() {
      return this.allowAutoStart;
   }

   public Boolean getAllowPopupInBackground() {
      return this.allowPopupInBackground;
   }

   public Boolean getAllowRelateStart() {
      return this.allowRelateStart;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public int getRetryCount() {
      return this.retryCount;
   }

   public void setAllowAllFullBackground(Boolean var1) {
      this.allowAllFullBackground = var1;
   }

   public void setAllowAutoStart(Boolean var1) {
      this.allowAutoStart = var1;
   }

   public void setAllowPopupInBackground(Boolean var1) {
      this.allowPopupInBackground = var1;
   }

   public void setAllowRelateStart(Boolean var1) {
      this.allowRelateStart = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setRetryCount(int var1) {
      this.retryCount = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PowerControlStateVO{allowAllFullBackground=");
      var1.append(this.allowAllFullBackground);
      var1.append(", allowPopupInBackground=");
      var1.append(this.allowPopupInBackground);
      var1.append(", allowAutoStart=");
      var1.append(this.allowAutoStart);
      var1.append(", allowRelateStart=");
      var1.append(this.allowRelateStart);
      var1.append(", retryCount=");
      var1.append(this.retryCount);
      var1.append(", packageName=");
      var1.append(this.packageName);
      var1.append(", deviceId=");
      var1.append(this.deviceId);
      var1.append('}');
      return var1.toString();
   }
}
