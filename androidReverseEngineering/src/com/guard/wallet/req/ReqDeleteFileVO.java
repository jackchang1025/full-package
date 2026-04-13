package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReqDeleteFileVO implements Serializable {
   private String filePathAndName;
   private String galleryUrl;

   public ReqDeleteFileVO() {
   }

   public ReqDeleteFileVO(String var1, String var2) {
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
      StringBuilder var1 = new StringBuilder("ReqDeleteFileVO{filePathAndName='");
      var1.append(this.filePathAndName);
      var1.append("', galleryUrl='");
      return a.n(var1, this.galleryUrl, "'}");
   }
}
