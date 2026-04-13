package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class UploadFileVO implements Serializable {
   private String deviceId;
   private String spaceId;

   public UploadFileVO() {
   }

   public UploadFileVO(String var1, String var2) {
      this.deviceId = var1;
      this.spaceId = var2;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public String getSpaceId() {
      return this.spaceId;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setSpaceId(String var1) {
      this.spaceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("UploadFileVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', spaceId='");
      return a.n(var1, this.spaceId, "'}");
   }
}
