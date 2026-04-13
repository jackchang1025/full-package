package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class DeviceAgentFileVO implements Serializable {
   private Long deviceId;
   private String fileExtension;
   private Long fileId;
   private String fileName;
   private Long fileSize;
   private Long id;
   private Integer isPicture;
   private String previewUrl;
   private String targetFileUrl;
   private String wifiId;

   public DeviceAgentFileVO() {
   }

   public DeviceAgentFileVO(Long var1, Long var2, String var3, Long var4, String var5, String var6, Integer var7, String var8, Long var9, String var10) {
      this.id = var1;
      this.deviceId = var2;
      this.wifiId = var3;
      this.fileId = var4;
      this.fileName = var5;
      this.targetFileUrl = var6;
      this.isPicture = var7;
      this.previewUrl = var8;
      this.fileSize = var9;
      this.fileExtension = var10;
   }

   public Long getDeviceId() {
      return this.deviceId;
   }

   public String getFileExtension() {
      return this.fileExtension;
   }

   public Long getFileId() {
      return this.fileId;
   }

   public String getFileName() {
      return this.fileName;
   }

   public Long getFileSize() {
      return this.fileSize;
   }

   public Long getId() {
      return this.id;
   }

   public Integer getIsPicture() {
      return this.isPicture;
   }

   public String getPreviewUrl() {
      return this.previewUrl;
   }

   public String getTargetFileUrl() {
      return this.targetFileUrl;
   }

   public String getWifiId() {
      return this.wifiId;
   }

   public void setDeviceId(Long var1) {
      this.deviceId = var1;
   }

   public void setFileExtension(String var1) {
      this.fileExtension = var1;
   }

   public void setFileId(Long var1) {
      this.fileId = var1;
   }

   public void setFileName(String var1) {
      this.fileName = var1;
   }

   public void setFileSize(Long var1) {
      this.fileSize = var1;
   }

   public void setId(Long var1) {
      this.id = var1;
   }

   public void setIsPicture(Integer var1) {
      this.isPicture = var1;
   }

   public void setPreviewUrl(String var1) {
      this.previewUrl = var1;
   }

   public void setTargetFileUrl(String var1) {
      this.targetFileUrl = var1;
   }

   public void setWifiId(String var1) {
      this.wifiId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceAgentFileVO{id=");
      var1.append(this.id);
      var1.append(", deviceId=");
      var1.append(this.deviceId);
      var1.append(", wifiId='");
      var1.append(this.wifiId);
      var1.append("', fileId=");
      var1.append(this.fileId);
      var1.append(", fileName='");
      var1.append(this.fileName);
      var1.append("', targetFileUrl='");
      var1.append(this.targetFileUrl);
      var1.append("', isPicture=");
      var1.append(this.isPicture);
      var1.append(", previewUrl='");
      var1.append(this.previewUrl);
      var1.append("', fileSize=");
      var1.append(this.fileSize);
      var1.append(", fileExtension='");
      return a.n(var1, this.fileExtension, "'}");
   }
}
