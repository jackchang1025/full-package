package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class DeviceContactNumberVO implements Serializable {
   private String label;
   private String number;
   private int numberType;

   public String getLabel() {
      String var2 = this.label;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getNumber() {
      String var2 = this.number;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public int getNumberType() {
      return this.numberType;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public void setNumber(String var1) {
      this.number = var1;
   }

   public void setNumberType(int var1) {
      this.numberType = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ContactsNumber{numberType=");
      var1.append(this.numberType);
      var1.append(", label='");
      var1.append(this.label);
      var1.append("', number='");
      return a.n(var1, this.number, "'}");
   }
}
