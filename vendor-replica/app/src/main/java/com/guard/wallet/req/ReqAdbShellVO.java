package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqAdbShellVO implements Serializable {
   private String command;

   public ReqAdbShellVO() {
   }

   public ReqAdbShellVO(String var1) {
      this.command = var1;
   }

   public String getCommand() {
      return this.command;
   }

   public void setCommand(String var1) {
      this.command = var1;
   }

   @NonNull
   @Override
   public String toString() {
      return new StringBuilder("ReqAdbShellVO{command='").append(this.command).append("'}").toString();
   }
}
