package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class AppLocateValueVO implements Serializable {
   private String locateCode;
   private String locateValue;

   public AppLocateValueVO() {
   }

   public AppLocateValueVO(String var1, String var2) {
      this.locateCode = var1;
      this.locateValue = var2;
   }

   public String getLocateCode() {
      return this.locateCode;
   }

   public String getLocateValue() {
      return this.locateValue;
   }

   public void setLocateCode(String var1) {
      this.locateCode = var1;
   }

   public void setLocateValue(String var1) {
      this.locateValue = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("AppLocateValueVO{locateCode='");
      var1.append(this.locateCode);
      var1.append("', locateValue='");
      return a.n(var1, this.locateValue, "'}");
   }
}
