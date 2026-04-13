package com.guard.wallet.msg;

import java.io.Serializable;

public class BaseMsgBody implements Serializable {
   private final Long timestamp = System.currentTimeMillis();

   public Long getTimestamp() {
      return this.timestamp;
   }
}
