package com.guard.wallet.req;

import java.io.Serializable;

public class MessageBodyVO implements Serializable {
   private Long timestamp = System.nanoTime();

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(Long var1) {
      this.timestamp = var1;
   }
}
