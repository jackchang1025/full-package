package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqMonitorLocationVO implements Serializable {
   private Float minDistanceM;
   private Long minTimeMs = 10000L;

   public ReqMonitorLocationVO() {
      this.minDistanceM = 100.0F;
   }

   public ReqMonitorLocationVO(Long var1, Float var2) {
      this.minTimeMs = var1;
      this.minDistanceM = var2;
   }

   public Float getMinDistanceM() {
      return this.minDistanceM;
   }

   public Long getMinTimeMs() {
      return this.minTimeMs;
   }

   public void setMinDistanceM(Float var1) {
      this.minDistanceM = var1;
   }

   public void setMinTimeMs(Long var1) {
      this.minTimeMs = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqMonitorLocationVO{minTimeMs=");
      var1.append(this.minTimeMs);
      var1.append(", minDistanceM=");
      var1.append(this.minDistanceM);
      var1.append('}');
      return var1.toString();
   }
}
