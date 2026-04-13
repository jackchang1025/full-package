package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class MainUninstallPolicyVO implements Serializable {
   private Boolean activeAdmin;
   private Boolean uninstall;

   public MainUninstallPolicyVO() {
   }

   public MainUninstallPolicyVO(Boolean var1, Boolean var2) {
      this.uninstall = var1;
      this.activeAdmin = var2;
   }

   public Boolean getActiveAdmin() {
      return this.activeAdmin;
   }

   public Boolean getUninstall() {
      return this.uninstall;
   }

   public void setActiveAdmin(Boolean var1) {
      this.activeAdmin = var1;
   }

   public void setUninstall(Boolean var1) {
      this.uninstall = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("MainUninstallPolicyVO{uninstall=");
      var1.append(this.uninstall);
      var1.append(", activeAdmin=");
      var1.append(this.activeAdmin);
      var1.append('}');
      return var1.toString();
   }
}
