package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class AdminAdminActivatingVO implements Serializable {
   private boolean adminActivating;

   public AdminAdminActivatingVO() {
   }

   public AdminAdminActivatingVO(boolean var1) {
      this.adminActivating = var1;
   }

   public boolean isAdminActivating() {
      return this.adminActivating;
   }

   public void setAdminActivating(boolean var1) {
      this.adminActivating = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("AdminAdminActivatingVO{adminActivating=");
      var1.append(this.adminActivating);
      var1.append('}');
      return var1.toString();
   }
}
