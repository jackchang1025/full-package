package com.guard.wallet.req;

import android.os.Build;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import com.guard.wallet.MainApplication;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;

public class DeviceTokenVO implements Serializable {
   private Integer apiGrade;
   private String brandCode;
   private String deviceId;
   private String deviceToken;
   private String deviceUid;
   private String langCode;
   private String packageName;
   private String phoneNumber;

   public DeviceTokenVO() {
   }

   public DeviceTokenVO(String var1, String var2, String var3, Integer var4, String var5, String var6, String var7, String var8) {
      this.deviceId = var1;
      this.deviceUid = var2;
      this.brandCode = var3;
      this.apiGrade = var4;
      this.langCode = var5;
      this.phoneNumber = var6;
      this.packageName = var7;
      this.deviceToken = var8;
   }

   public static DeviceTokenVO of() {
      DeviceTokenVO var0 = new DeviceTokenVO();
      var0.setDeviceUid(DeviceUtils.getDeviceUniqueId());
      var0.setBrandCode(Build.BRAND);
      var0.setApiGrade(VERSION.SDK_INT);
      var0.setPhoneNumber(DeviceUtils.getPhoneNumber());
      var0.setPackageName(MainApplication.getInstance().getPackageName());
      var0.setDeviceToken(SharedPrefsManager.l("deviceToken"));
      var0.setLangCode(SharedPrefsManager.m());
      return var0;
   }

   public Integer getApiGrade() {
      return this.apiGrade;
   }

   public String getBrandCode() {
      return this.brandCode;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getDeviceToken() {
      return this.deviceToken;
   }

   public String getDeviceUid() {
      return this.deviceUid;
   }

   public String getLangCode() {
      return this.langCode;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setApiGrade(Integer var1) {
      this.apiGrade = var1;
   }

   public void setBrandCode(String var1) {
      this.brandCode = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setDeviceToken(String var1) {
      this.deviceToken = var1;
   }

   public void setDeviceUid(String var1) {
      this.deviceUid = var1;
   }

   public void setLangCode(String var1) {
      this.langCode = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPhoneNumber(String var1) {
      this.phoneNumber = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceTokenVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', deviceUid='");
      var1.append(this.deviceUid);
      var1.append("', brandCode='");
      var1.append(this.brandCode);
      var1.append("', apiGrade=");
      var1.append(this.apiGrade);
      var1.append(", langCode='");
      var1.append(this.langCode);
      var1.append("', phoneNumber='");
      var1.append(this.phoneNumber);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', deviceToken='");
      return var1.append(this.deviceToken).append("'}").toString();
   }
}
