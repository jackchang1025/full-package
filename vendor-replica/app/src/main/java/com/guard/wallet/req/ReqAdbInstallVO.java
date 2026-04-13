package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqAdbInstallVO implements Serializable {
   private String fileName;
   private String fileUrl;
   private String logId;
   private String startCommand;

   public ReqAdbInstallVO() {
   }

   public ReqAdbInstallVO(String var1, String var2, String var3, String var4) {
      this.logId = var1;
      this.fileUrl = var2;
      this.fileName = var3;
      this.startCommand = var4;
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getFileUrl() {
      return this.fileUrl;
   }

   public String getLogId() {
      return this.logId;
   }

   public String getStartCommand() {
      return this.startCommand;
   }

   public void setFileName(String var1) {
      this.fileName = var1;
   }

   public void setFileUrl(String var1) {
      this.fileUrl = var1;
   }

   public void setLogId(String var1) {
      this.logId = var1;
   }

   public void setStartCommand(String var1) {
      this.startCommand = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqAdbPushVO{logId='");
      var1.append(this.logId);
      var1.append("'fileUrl='");
      var1.append(this.fileUrl);
      var1.append("', fileName='");
      var1.append(this.fileName);
      var1.append("', startCommand='");
      return var1.append(this.startCommand).append("'}").toString();
   }
}
