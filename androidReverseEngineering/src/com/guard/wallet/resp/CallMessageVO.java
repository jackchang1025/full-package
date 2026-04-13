package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class CallMessageVO extends MessageBodyVO {
   private String callNumber;
   private String callState;
   private Integer callType;

   public CallMessageVO() {
   }

   public CallMessageVO(Integer var1, String var2, String var3) {
      this.callType = var1;
      this.callNumber = var2;
      this.callState = var3;
   }

   public String getCallNumber() {
      return this.callNumber;
   }

   public String getCallState() {
      return this.callState;
   }

   public Integer getCallType() {
      return this.callType;
   }

   public void setCallNumber(String var1) {
      this.callNumber = var1;
   }

   public void setCallState(String var1) {
      this.callState = var1;
   }

   public void setCallType(Integer var1) {
      this.callType = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CallMessageVO{callType=");
      var1.append(this.callType);
      var1.append(", callNumber='");
      var1.append(this.callNumber);
      var1.append("', callState='");
      return a.n(var1, this.callState, "'}");
   }
}
