package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class AttachFileVO implements Serializable {
   private String contentType;
   private String fileExtension;
   private String fileName;
   private Long id;
   private Integer isPicture;
   private String previewUrl;
   private Long spaceId;
   private String spaceName;
   private String targetFileUrl;

   public AttachFileVO() {
   }

   public AttachFileVO(Long var1, Long var2, String var3, String var4, String var5, Integer var6, String var7, String var8, String var9) {
      this.id = var1;
      this.spaceId = var2;
      this.spaceName = var3;
      this.fileName = var4;
      this.targetFileUrl = var5;
      this.isPicture = var6;
      this.previewUrl = var7;
      this.fileExtension = var8;
      this.contentType = var9;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getFileExtension() {
      return this.fileExtension;
   }

   public String getFileName() {
      return this.fileName;
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

   public Long getSpaceId() {
      return this.spaceId;
   }

   public String getSpaceName() {
      return this.spaceName;
   }

   public String getTargetFileUrl() {
      return this.targetFileUrl;
   }

   public void setContentType(String var1) {
      this.contentType = var1;
   }

   public void setFileExtension(String var1) {
      this.fileExtension = var1;
   }

   public void setFileName(String var1) {
      this.fileName = var1;
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

   public void setSpaceId(Long var1) {
      this.spaceId = var1;
   }

   public void setSpaceName(String var1) {
      this.spaceName = var1;
   }

   public void setTargetFileUrl(String var1) {
      this.targetFileUrl = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("AttachFileVO{id=");
      var1.append(this.id);
      var1.append(", spaceId=");
      var1.append(this.spaceId);
      var1.append(", spaceName='");
      var1.append(this.spaceName);
      var1.append("', fileName='");
      var1.append(this.fileName);
      var1.append("', targetFileUrl='");
      var1.append(this.targetFileUrl);
      var1.append("', isPicture=");
      var1.append(this.isPicture);
      var1.append(", previewUrl='");
      var1.append(this.previewUrl);
      var1.append("', fileExtension='");
      var1.append(this.fileExtension);
      var1.append("', contentType='");
      return a.n(var1, this.contentType, "'}");
   }
}
