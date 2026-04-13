package com.guard.wallet.entity;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class AdbShellResult implements Serializable {
   private String output;
   private boolean success;

   public AdbShellResult() {
   }

   public AdbShellResult(boolean var1, String var2) {
      this.success = var1;
      this.output = var2;
   }

   public String getOutput() {
      return this.output;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public void setOutput(String var1) {
      this.output = var1;
   }

   public void setSuccess(boolean var1) {
      this.success = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("AdbShellResult{success=");
      var1.append(this.success);
      var1.append(", output='");
      return a.n(var1, this.output, "'}");
   }
}
