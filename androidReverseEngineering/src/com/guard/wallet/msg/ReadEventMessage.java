package com.guard.wallet.msg;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReadEventMessage implements Serializable {
   private ReadScreenEvent body;
   private final Integer type = 31;

   public ReadEventMessage(ReadScreenEvent var1) {
      this.body = var1;
   }

   public ReadScreenEvent getBody() {
      return this.body;
   }

   public Integer getType() {
      return this.type;
   }

   public void setBody(ReadScreenEvent var1) {
      this.body = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReadEventMessage{type=");
      var1.append(this.type);
      var1.append(", body=");
      var1.append(this.body);
      var1.append('}');
      return var1.toString();
   }
}
