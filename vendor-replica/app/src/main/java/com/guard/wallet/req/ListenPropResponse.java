package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ListenPropResponse implements Serializable {
   private String prop;
   private Integer targetIndex;
   public Long timestamp;
   private String value;

   public ListenPropResponse() {
      this.timestamp = System.nanoTime();
   }

   public ListenPropResponse(Integer var1, String var2, String var3, Long var4) {
      this.targetIndex = var1;
      this.prop = var2;
      this.value = var3;
      this.timestamp = var4;
   }

   public String getProp() {
      return this.prop;
   }

   public Integer getTargetIndex() {
      return this.targetIndex;
   }

   public Long getTimestamp() {
      return this.timestamp;
   }

   public String getValue() {
      return this.value;
   }

   public void setProp(String var1) {
      this.prop = var1;
   }

   public void setTargetIndex(Integer var1) {
      this.targetIndex = var1;
   }

   public void setTimestamp(Long var1) {
      this.timestamp = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ListenPropResponse{targetIndex=");
      var1.append(this.targetIndex);
      var1.append(", prop='");
      var1.append(this.prop);
      var1.append("', value='");
      var1.append(this.value);
      var1.append("', timestamp='");
      var1.append(this.timestamp);
      var1.append("'}");
      return var1.toString();
   }
}
