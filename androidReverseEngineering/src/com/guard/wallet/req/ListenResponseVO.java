package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.util.List;

public class ListenResponseVO extends MessageBodyVO {
   private String batchId;
   private String delegateId;
   private String listenId;
   private List<ListenPropResponse> responses;
   private String subscribeId;

   public ListenResponseVO() {
   }

   public ListenResponseVO(String var1, String var2, String var3, String var4, List<ListenPropResponse> var5) {
      this.batchId = var1;
      this.listenId = var2;
      this.subscribeId = var3;
      this.delegateId = var4;
      this.responses = var5;
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

   public List<ListenPropResponse> getResponses() {
      return this.responses;
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

   public void setResponses(List<ListenPropResponse> var1) {
      this.responses = var1;
   }

   public void setSubscribeId(String var1) {
      this.subscribeId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ListenResponseVO{listenId='");
      var1.append(this.listenId);
      var1.append("', subscribeId='");
      var1.append(this.subscribeId);
      var1.append("', batchId='");
      var1.append(this.batchId);
      var1.append("', delegateId='");
      var1.append(this.delegateId);
      var1.append("', responses=");
      var1.append(this.responses);
      var1.append('}');
      return var1.toString();
   }
}
