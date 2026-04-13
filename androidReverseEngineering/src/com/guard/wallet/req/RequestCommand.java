package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class RequestCommand implements Serializable {
   private List<String> commands;

   public RequestCommand() {
   }

   public RequestCommand(List<String> var1) {
      this.commands = var1;
   }

   public List<String> getCommands() {
      return this.commands;
   }

   public void setCommands(List<String> var1) {
      this.commands = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RequestCommand{commands=");
      var1.append(this.commands);
      var1.append('}');
      return var1.toString();
   }
}
