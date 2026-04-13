package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.CommandResult;
import java.io.Serializable;

public class PushResponseVO implements Serializable {
   private String deviceId;
   private Integer downloadResult;
   private String fileUrl;
   private Integer installMethod;
   private Integer installResult;
   private String logId;
   private CommandResult pushCommandResult;
   private CommandResult runCommandResult;
   private Integer startResult;

   public PushResponseVO() {
   }

   public PushResponseVO(String var1, String var2, String var3, Integer var4, Integer var5, Integer var6, CommandResult var7, CommandResult var8) {
      this.logId = var1;
      this.deviceId = var2;
      this.fileUrl = var3;
      this.installMethod = var4;
      this.installResult = var5;
      this.startResult = var6;
      this.pushCommandResult = var7;
      this.runCommandResult = var8;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public Integer getDownloadResult() {
      return this.downloadResult;
   }

   public String getFileUrl() {
      return this.fileUrl;
   }

   public Integer getInstallMethod() {
      return this.installMethod;
   }

   public Integer getInstallResult() {
      return this.installResult;
   }

   public String getLogId() {
      return this.logId;
   }

   public CommandResult getPushCommandResult() {
      return this.pushCommandResult;
   }

   public CommandResult getRunCommandResult() {
      return this.runCommandResult;
   }

   public Integer getStartResult() {
      return this.startResult;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setDownloadResult(Integer var1) {
      this.downloadResult = var1;
   }

   public void setFileUrl(String var1) {
      this.fileUrl = var1;
   }

   public void setInstallMethod(Integer var1) {
      this.installMethod = var1;
   }

   public void setInstallResult(Integer var1) {
      this.installResult = var1;
   }

   public void setLogId(String var1) {
      this.logId = var1;
   }

   public void setPushCommandResult(CommandResult var1) {
      this.pushCommandResult = var1;
   }

   public void setRunCommandResult(CommandResult var1) {
      this.runCommandResult = var1;
   }

   public void setStartResult(Integer var1) {
      this.startResult = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PushResponseVO{logId='");
      var1.append(this.logId);
      var1.append("', deviceId='");
      var1.append(this.deviceId);
      var1.append("', fileUrl='");
      var1.append(this.fileUrl);
      var1.append("', installMethod=");
      var1.append(this.installMethod);
      var1.append(", installResult=");
      var1.append(this.installResult);
      var1.append(", startResult=");
      var1.append(this.startResult);
      var1.append(", pushCommandResult=");
      var1.append(this.pushCommandResult);
      var1.append(", runCommandResult=");
      var1.append(this.runCommandResult);
      var1.append('}');
      return var1.toString();
   }
}
