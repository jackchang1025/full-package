package com.guard.wallet.req;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReqResetAccessibilityService implements Serializable {
   private String serviceName;

   public ReqResetAccessibilityService() {
   }

   public ReqResetAccessibilityService(String var1) {
      this.serviceName = var1;
   }

   public String getServiceName() {
      return this.serviceName;
   }

   public void setServiceName(String var1) {
      this.serviceName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      return a.n(new StringBuilder("ReqResetAccessibilityService{serviceName='"), this.serviceName, "'}");
   }
}
