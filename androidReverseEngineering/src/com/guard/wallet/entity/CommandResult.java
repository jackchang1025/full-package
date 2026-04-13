package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class CommandResult implements Serializable {
   private List<String> errorMsgLines;
   private int result;
   private List<String> successMsgLines;

   public CommandResult(int var1) {
      this.result = var1;
   }

   public CommandResult(int var1, List<String> var2, List<String> var3) {
      this.result = var1;
      this.successMsgLines = var2;
      this.errorMsgLines = var3;
   }

   public List<String> getErrorMsgLines() {
      return this.errorMsgLines;
   }

   public int getResult() {
      return this.result;
   }

   public List<String> getSuccessMsgLines() {
      return this.successMsgLines;
   }

   public void setErrorMsgLines(List<String> var1) {
      this.errorMsgLines = var1;
   }

   public void setResult(int var1) {
      this.result = var1;
   }

   public void setSuccessMsgLines(List<String> var1) {
      this.successMsgLines = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CommandResult{result=");
      var1.append(this.result);
      var1.append(", successMsgLines=");
      var1.append(this.successMsgLines);
      var1.append(", errorMsgLines=");
      var1.append(this.errorMsgLines);
      var1.append('}');
      return var1.toString();
   }
}
