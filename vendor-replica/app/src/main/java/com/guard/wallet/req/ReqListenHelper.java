package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqListenHelper implements Serializable {
   private String batchId;
   private String delegateId;
   private String listenId;
   private Integer listenType;
   private String prop;
   private Integer screenState;
   private String subscribeId;

   public ReqListenHelper() {
      this.prop = "GESTURE_POINTS";
   }

   public ReqListenHelper(Integer var1, String var2, Integer var3) {
      this.prop = "GESTURE_POINTS";
      this.listenType = var1;
      this.subscribeId = var2;
      this.screenState = var3;
   }

   public ReqListenHelper(Integer var1, String var2, String var3, String var4, String var5, String var6, Integer var7) {
      this.listenType = var1;
      this.batchId = var2;
      this.listenId = var3;
      this.subscribeId = var4;
      this.delegateId = var5;
      this.prop = var6;
      this.screenState = var7;
   }

   public ReqListenHelper(String var1, Integer var2) {
      this.prop = "GESTURE_POINTS";
      this.subscribeId = var1;
      this.screenState = var2;
   }

   public static ReqListenHelper clone(ReqListenHelper var0) {
      if (var0 == null) {
         return null;
      } else {
         ReqListenHelper var1 = new ReqListenHelper();
         var1.setListenType(var0.getListenType());
         var1.setListenId(var0.getListenId());
         var1.setSubscribeId(var0.getSubscribeId());
         var1.setDelegateId(var0.getDelegateId());
         var1.setBatchId(var0.getBatchId());
         var1.setProp(var0.getProp());
         var1.setScreenState(var0.getScreenState());
         return var1;
      }
   }

   public String getBatchId() {
      return this.batchId;
   }

   public String getDelegateId() {
      return this.delegateId;
   }

   public String getListenId() {
      return this.listenId;
   }

   public Integer getListenType() {
      return this.listenType;
   }

   public String getProp() {
      return this.prop;
   }

   public Integer getScreenState() {
      return this.screenState;
   }

   public String getSubscribeId() {
      return this.subscribeId;
   }

   public void setBatchId(String var1) {
      this.batchId = var1;
   }

   public void setDelegateId(String var1) {
      this.delegateId = var1;
   }

   public void setListenId(String var1) {
      this.listenId = var1;
   }

   public void setListenType(Integer var1) {
      this.listenType = var1;
   }

   public void setProp(String var1) {
      this.prop = var1;
   }

   public void setScreenState(Integer var1) {
      this.screenState = var1;
   }

   public void setSubscribeId(String var1) {
      this.subscribeId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqListenHelper{subscribeId='");
      var1.append(this.subscribeId);
      var1.append("', listenId='");
      var1.append(this.listenId);
      var1.append("', delegateId='");
      var1.append(this.delegateId);
      var1.append("', prop='");
      var1.append(this.prop);
      var1.append("', screenState=");
      var1.append(this.screenState);
      var1.append("', batchId=");
      var1.append(this.batchId);
      var1.append("', listenType=");
      var1.append(this.listenType);
      var1.append("'}");
      return var1.toString();
   }
}
