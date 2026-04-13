package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityNodeInfo;
import java.io.Serializable;

public class RootInActiveWindowResult implements Serializable {
   private AccessibilityNodeInfo curRoot;
   private boolean isComplete;

   public RootInActiveWindowResult(AccessibilityNodeInfo var1, boolean var2) {
      this.curRoot = var1;
      this.isComplete = var2;
   }

   public AccessibilityNodeInfo getCurRoot() {
      return this.curRoot;
   }

   public boolean isComplete() {
      return this.isComplete;
   }

   public void setComplete(boolean var1) {
      this.isComplete = var1;
   }

   public void setCurRoot(AccessibilityNodeInfo var1) {
      this.curRoot = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RootInActiveWindowResult{curRoot=");
      var1.append(this.curRoot);
      var1.append(", isComplete=");
      var1.append(this.isComplete);
      var1.append('}');
      return var1.toString();
   }
}
