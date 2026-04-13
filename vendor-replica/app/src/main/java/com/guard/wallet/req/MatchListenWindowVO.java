package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class MatchListenWindowVO implements Serializable {
   private String delegateId;
   private List<ListenWindow> listenWindows;

   public MatchListenWindowVO() {
   }

   public MatchListenWindowVO(String var1, List<ListenWindow> var2) {
      this.delegateId = var1;
      this.listenWindows = var2;
   }

   public String getDelegateId() {
      return this.delegateId;
   }

   public List<ListenWindow> getListenWindows() {
      return this.listenWindows;
   }

   public void setDelegateId(String var1) {
      this.delegateId = var1;
   }

   public void setListenWindows(List<ListenWindow> var1) {
      this.listenWindows = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("MatchListenWindowVO{delegateId='");
      var1.append(this.delegateId);
      var1.append("', listenWindows=");
      var1.append(this.listenWindows);
      var1.append('}');
      return var1.toString();
   }
}
