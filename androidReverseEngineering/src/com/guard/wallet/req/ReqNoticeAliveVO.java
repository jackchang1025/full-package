package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReqNoticeAliveVO implements Serializable {
   private String packageName;
   private Long timestamp = System.currentTimeMillis();

   public ReqNoticeAliveVO() {
   }

   public ReqNoticeAliveVO(String var1) {
      this.packageName = var1;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setTimestamp(Long var1) {
      this.timestamp = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqNoticeAliveVO{packageName='");
      var1.append(this.packageName);
      var1.append("', timestamp=");
      var1.append(this.timestamp);
      var1.append('}');
      return var1.toString();
   }
}
