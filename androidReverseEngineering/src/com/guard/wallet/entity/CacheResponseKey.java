package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

public class CacheResponseKey implements Serializable {
   private Long batchId;
   private String subscribeId;

   public CacheResponseKey() {
   }

   public CacheResponseKey(String var1, Long var2) {
      this.subscribeId = var1;
      this.batchId = var2;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!this.subscribeId.equals(var1.subscribeId) || !this.batchId.equals(var1.batchId)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public Long getBatchId() {
      return this.batchId;
   }

   public String getSubscribeId() {
      return this.subscribeId;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.subscribeId, this.batchId);
   }

   public void setBatchId(Long var1) {
      this.batchId = var1;
   }

   public void setSubscribeId(String var1) {
      this.subscribeId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CacheResponseKey{subscribeId='");
      var1.append(this.subscribeId);
      var1.append("', batchId='");
      var1.append(this.batchId);
      var1.append("'}");
      return var1.toString();
   }
}
