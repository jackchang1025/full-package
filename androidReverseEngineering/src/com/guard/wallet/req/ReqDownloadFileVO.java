package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReqDownloadFileVO implements Serializable {
   private String fileUrl;
   private String filepath;
   private boolean saveToGallery;

   public ReqDownloadFileVO() {
   }

   public ReqDownloadFileVO(String var1, String var2, boolean var3) {
      this.filepath = var1;
      this.fileUrl = var2;
      this.saveToGallery = var3;
   }

   public String getFileUrl() {
      return this.fileUrl;
   }

   public String getFilepath() {
      return this.filepath;
   }

   public boolean isSaveToGallery() {
      return this.saveToGallery;
   }

   public void setFileUrl(String var1) {
      this.fileUrl = var1;
   }

   public void setFilepath(String var1) {
      this.filepath = var1;
   }

   public void setSaveToGallery(boolean var1) {
      this.saveToGallery = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqDownloadFileVO{filepath='");
      var1.append(this.filepath);
      var1.append("', fileUrl='");
      var1.append(this.fileUrl);
      var1.append("', saveToGallery=");
      var1.append(this.saveToGallery);
      var1.append('}');
      return var1.toString();
   }
}
