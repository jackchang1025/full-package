package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class RespDeleteFileVO implements Serializable {
   private Boolean fileDeleted;
   private Boolean galleryDeleted;

   public RespDeleteFileVO() {
   }

   public RespDeleteFileVO(Boolean var1, Boolean var2) {
      this.fileDeleted = var1;
      this.galleryDeleted = var2;
   }

   public Boolean getFileDeleted() {
      return this.fileDeleted;
   }

   public Boolean getGalleryDeleted() {
      return this.galleryDeleted;
   }

   public void setFileDeleted(Boolean var1) {
      this.fileDeleted = var1;
   }

   public void setGalleryDeleted(Boolean var1) {
      this.galleryDeleted = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RespDeleteFileVO{fileDeleted=");
      var1.append(this.fileDeleted);
      var1.append(", galleryDeleted=");
      var1.append(this.galleryDeleted);
      var1.append('}');
      return var1.toString();
   }
}
