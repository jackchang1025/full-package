package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;

public class PasswordEventBodyVO extends MessageBodyVO {
   private String lockBatchId;
   private String password;

   public PasswordEventBodyVO() {
   }

   public PasswordEventBodyVO(String var1, String var2) {
      this.password = var1;
      this.lockBatchId = var2;
   }

   public String getLockBatchId() {
      return this.lockBatchId;
   }

   public String getPassword() {
      return this.password;
   }

   public void setLockBatchId(String var1) {
      this.lockBatchId = var1;
   }

   public void setPassword(String var1) {
      this.password = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PasswordEventBodyVO{password='");
      var1.append(this.password);
      var1.append("', lockBatchId='");
      return a.n(var1, this.lockBatchId, "'}");
   }
}
