package com.guard.wallet.req;

import android.support.annotation.NonNull;

public class ApiRequest<T> {
   private T data;

   public ApiRequest() {
   }

   public ApiRequest(T var1) {
      this.data = (T)var1;
   }

   public T getData() {
      return this.data;
   }

   public void setData(T var1) {
      this.data = (T)var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ApiRequest{data=");
      var1.append(this.data);
      var1.append('}');
      return var1.toString();
   }
}
