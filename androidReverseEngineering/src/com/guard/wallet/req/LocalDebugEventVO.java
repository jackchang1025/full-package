package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;

public class LocalDebugEventVO extends MessageBodyVO {
   private String message;
   private String topic;
   private String type;

   public LocalDebugEventVO() {
   }

   public LocalDebugEventVO(String var1, String var2, String var3) {
      this.topic = var1;
      this.type = var2;
      this.message = var3;
   }

   public String getMessage() {
      return this.message;
   }

   public String getTopic() {
      return this.topic;
   }

   public String getType() {
      return this.type;
   }

   public void setMessage(String var1) {
      this.message = var1;
   }

   public void setTopic(String var1) {
      this.topic = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("LocalDebugEventVO{topic='");
      var1.append(this.topic);
      var1.append("', type='");
      var1.append(this.type);
      var1.append("', message='");
      return a.n(var1, this.message, "'}");
   }
}
