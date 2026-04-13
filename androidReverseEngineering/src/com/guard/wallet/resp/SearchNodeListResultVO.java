package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SearchNodeListResultVO implements Serializable {
   private List<UiObjectVO> nodes;
   private String resUnique;

   public SearchNodeListResultVO() {
   }

   public SearchNodeListResultVO(String var1, List<UiObjectVO> var2) {
      this.resUnique = var1;
      this.nodes = var2;
   }

   public void addNodes(List<UiObjectVO> var1) {
      if (var1 != null && !var1.isEmpty()) {
         if (this.nodes == null) {
            this.nodes = new LinkedList<>();
         }

         this.nodes.addAll(var1);
      }
   }

   public List<UiObjectVO> getNodes() {
      return this.nodes;
   }

   public String getResUnique() {
      return this.resUnique;
   }

   public void setNodes(List<UiObjectVO> var1) {
      this.nodes = var1;
   }

   public void setResUnique(String var1) {
      this.resUnique = var1;
   }

   public int size() {
      List var2 = this.nodes;
      int var1;
      if (var2 != null && !var2.isEmpty()) {
         var1 = this.nodes.size();
      } else {
         var1 = 0;
      }

      return var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("SearchNodeListResultVO{resUnique='");
      var1.append(this.resUnique);
      var1.append("', nodes=");
      var1.append(this.nodes);
      var1.append('}');
      return var1.toString();
   }
}
