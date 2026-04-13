package com.guard.wallet.msg;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class BridgeMessage implements Serializable {
   private BridgeBody body;
   private final Integer type = 7;

   public BridgeMessage() {
   }

   public BridgeMessage(BridgeBody var1) {
      this.body = var1;
   }

   public BridgeBody getBody() {
      return this.body;
   }

   public Integer getType() {
      return this.type;
   }

   public void setBody(BridgeBody var1) {
      this.body = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BridgeMessage{type=");
      var1.append(this.type);
      var1.append(", body=");
      var1.append(this.body);
      var1.append('}');
      return var1.toString();
   }
}
