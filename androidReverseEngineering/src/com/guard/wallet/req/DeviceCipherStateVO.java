package com.guard.wallet.req;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import java.io.Serializable;
import java.util.List;

public class DeviceCipherStateVO implements Serializable {
   private Long appId;
   private String cipherClassName;
   private String cipherGradeCode;
   private String cipherPurposeCode;
   private String loginName;
   private String packageName;
   private List<Point> patternCipher;
   private String textCipher;
   private List<Point> touchCipher;

   public DeviceCipherStateVO() {
   }

   public DeviceCipherStateVO(Long var1, String var2, String var3, String var4, String var5, String var6, String var7, List<Point> var8, List<Point> var9) {
      this.appId = var1;
      this.packageName = var2;
      this.cipherClassName = var3;
      this.cipherGradeCode = var4;
      this.cipherPurposeCode = var5;
      this.loginName = var6;
      this.textCipher = var7;
      this.patternCipher = var8;
      this.touchCipher = var9;
   }

   public Long getAppId() {
      return this.appId;
   }

   public String getCipherClassName() {
      return this.cipherClassName;
   }

   public String getCipherGradeCode() {
      return this.cipherGradeCode;
   }

   public String getCipherPurposeCode() {
      return this.cipherPurposeCode;
   }

   public String getLoginName() {
      return this.loginName;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public List<Point> getPatternCipher() {
      return this.patternCipher;
   }

   public String getTextCipher() {
      return this.textCipher;
   }

   public List<Point> getTouchCipher() {
      return this.touchCipher;
   }

   public void setAppId(Long var1) {
      this.appId = var1;
   }

   public void setCipherClassName(String var1) {
      this.cipherClassName = var1;
   }

   public void setCipherGradeCode(String var1) {
      this.cipherGradeCode = var1;
   }

   public void setCipherPurposeCode(String var1) {
      this.cipherPurposeCode = var1;
   }

   public void setLoginName(String var1) {
      this.loginName = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPatternCipher(List<Point> var1) {
      this.patternCipher = var1;
   }

   public void setTextCipher(String var1) {
      this.textCipher = var1;
   }

   public void setTouchCipher(List<Point> var1) {
      this.touchCipher = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceCipherStateVO{appId=");
      var1.append(this.appId);
      var1.append(", packageName='");
      var1.append(this.packageName);
      var1.append("', cipherClassName='");
      var1.append(this.cipherClassName);
      var1.append("', cipherGradeCode='");
      var1.append(this.cipherGradeCode);
      var1.append("', cipherPurposeCode='");
      var1.append(this.cipherPurposeCode);
      var1.append("', loginName='");
      var1.append(this.loginName);
      var1.append("', textCipher='");
      var1.append(this.textCipher);
      var1.append("', patternCipher='");
      var1.append(this.patternCipher);
      var1.append("', touchCipher='");
      var1.append(this.touchCipher);
      var1.append("'}");
      return var1.toString();
   }
}
