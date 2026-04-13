package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class PackagesBodyVO implements Serializable {
   private String deviceId;
   private List<AppInfo> packages;

   public PackagesBodyVO() {
   }

   public PackagesBodyVO(String var1, List<AppInfo> var2) {
      this.deviceId = var1;
      this.packages = var2;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public List<AppInfo> getPackages() {
      return this.packages;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setPackages(List<AppInfo> var1) {
      this.packages = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PackagesBodyVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', packages=");
      var1.append(this.packages);
      var1.append('}');
      return var1.toString();
   }
}
