package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class CallStateVO implements Serializable {
   private String callState;
   private String description;
   private Integer state;

   public CallStateVO() {
   }

   public CallStateVO(Integer var1, String var2, String var3) {
      this.state = var1;
      this.callState = var2;
      this.description = var3;
   }

   public String getCallState() {
      return this.callState;
   }

   public String getDescription() {
      return this.description;
   }

   public Integer getState() {
      return this.state;
   }

   public void setCallState(String var1) {
      this.callState = var1;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setState(Integer var1) {
      this.state = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CallStateVO{state=");
      var1.append(this.state);
      var1.append(", callState='");
      var1.append(this.callState);
      var1.append("', description='");
      return a.n(var1, this.description, "'}");
   }
}
