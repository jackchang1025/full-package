package com.guard.wallet.resp;

import a.a;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiResult<T> {
   private Integer code;
   private Integer count;
   private T data;
   private String msg;
   private Boolean success = Boolean.TRUE;
   @SuppressLint({"SimpleDateFormat"})
   private String timestamp;

   public ApiResult() {
      this.code = 200;
      this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
   }

   public ApiResult(T var1, Integer var2, Boolean var3, String var4, Integer var5) {
      this.code = 200;
      this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      this.data = (T)var1;
      this.count = var2;
      this.success = var3;
      this.msg = var4;
      this.code = var5;
   }

   public Integer getCode() {
      return this.code;
   }

   public Integer getCount() {
      return this.count;
   }

   public T getData() {
      return this.data;
   }

   public String getMsg() {
      return this.msg;
   }

   public Boolean getSuccess() {
      return this.success;
   }

   public String getTimestamp() {
      return this.timestamp;
   }

   public void setCode(Integer var1) {
      this.code = var1;
   }

   public void setCount(Integer var1) {
      this.count = var1;
   }

   public void setData(T var1) {
      this.data = (T)var1;
   }

   public void setMsg(String var1) {
      this.msg = var1;
   }

   public void setSuccess(Boolean var1) {
      this.success = var1;
   }

   public void setTimestamp(String var1) {
      this.timestamp = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ApiResult{data=");
      var1.append(this.data);
      var1.append(", count=");
      var1.append(this.count);
      var1.append(", success=");
      var1.append(this.success);
      var1.append(", msg='");
      var1.append(this.msg);
      var1.append("', code=");
      var1.append(this.code);
      var1.append(", timestamp='");
      return a.n(var1, this.timestamp, "'}");
   }
}
