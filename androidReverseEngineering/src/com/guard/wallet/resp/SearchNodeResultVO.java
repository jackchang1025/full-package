package com.guard.wallet.resp;

import android.support.annotation.NonNull;

public class SearchNodeResultVO {
   private UiObjectVO node;
   private String resUnique;

   public SearchNodeResultVO() {
   }

   public SearchNodeResultVO(String var1, UiObjectVO var2) {
      this.resUnique = var1;
      this.node = var2;
   }

   public UiObjectVO getNode() {
      return this.node;
   }

   public String getResUnique() {
      return this.resUnique;
   }

   public void setNode(UiObjectVO var1) {
      this.node = var1;
   }

   public void setResUnique(String var1) {
      this.resUnique = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("SearchNodeResultVO{resUnique='");
      var1.append(this.resUnique);
      var1.append("', node=");
      var1.append(this.node);
      var1.append('}');
      return var1.toString();
   }
}
