package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Arrays;

public class TakeScreenShotResult implements Serializable {
   private byte[] saveBytesResult;
   private String saveFileResult;

   public TakeScreenShotResult() {
   }

   public TakeScreenShotResult(String var1, byte[] var2) {
      this.saveFileResult = var1;
      this.saveBytesResult = var2;
   }

   public byte[] getSaveBytesResult() {
      return this.saveBytesResult;
   }

   public String getSaveFileResult() {
      return this.saveFileResult;
   }

   public void setSaveBytesResult(byte[] var1) {
      this.saveBytesResult = var1;
   }

   public void setSaveFileResult(String var1) {
      this.saveFileResult = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("TakeScreenShotResult{saveFileResult='");
      var1.append(this.saveFileResult);
      var1.append("', saveBytesResult=");
      var1.append(Arrays.toString(this.saveBytesResult));
      var1.append('}');
      return var1.toString();
   }
}
