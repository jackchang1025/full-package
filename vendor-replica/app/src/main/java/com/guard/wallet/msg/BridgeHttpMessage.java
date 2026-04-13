package com.guard.wallet.msg;

import androidx.annotation.NonNull;

public class BridgeHttpMessage {
   private String body;
   private final Integer type = 17;

   public BridgeHttpMessage(String var1) {
      this.body = var1;
   }

   public String getBody() {
      return this.body;
   }

   public Integer getType() {
      return this.type;
   }

   public void setBody(String var1) {
      this.body = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BridgeHttpMessage{type=");
      var1.append(this.type);
      var1.append(", body='");
      return var1.append(this.body).append("'}").toString();
   }
}
