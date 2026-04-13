package com.guard.wallet.msg;

import androidx.annotation.NonNull;

public class BridgeBufferMessage {
   private BridgeBufferBody body;
   private final Integer type = 15;

   public BridgeBufferMessage() {
   }

   public BridgeBufferMessage(BridgeBufferBody var1) {
      this.body = var1;
   }

   public BridgeBufferBody getBody() {
      return this.body;
   }

   public Integer getType() {
      return this.type;
   }

   public void setBody(BridgeBufferBody var1) {
      this.body = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BridgeBufferMessage{type=");
      var1.append(this.type);
      var1.append(", body=");
      var1.append(this.body);
      var1.append('}');
      return var1.toString();
   }
}
