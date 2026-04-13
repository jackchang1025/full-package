package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class RespDownloadFileVO implements Serializable {
   private String filePathAndName;
   private String galleryUrl;

   public RespDownloadFileVO() {
   }

   public RespDownloadFileVO(String var1, String var2) {
      this.filePathAndName = var1;
      this.galleryUrl = var2;
   }

   public String getFilePathAndName() {
      return this.filePathAndName;
   }

   public String getGalleryUrl() {
      return this.galleryUrl;
   }

   public void setFilePathAndName(String var1) {
      this.filePathAndName = var1;
   }

   public void setGalleryUrl(String var1) {
      this.galleryUrl = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RespDownloadFileVO{filePathAndName='");
      var1.append(this.filePathAndName);
      var1.append("', galleryUrl='");
      return a.n(var1, this.galleryUrl, "'}");
   }
}
