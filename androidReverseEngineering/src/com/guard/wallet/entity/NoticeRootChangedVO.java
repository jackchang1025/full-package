package com.guard.wallet.entity;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class NoticeRootChangedVO implements Serializable {
   private UiObject activeFastRoot;
   private String activePackageName;
   private String activeWindowClassName;

   public NoticeRootChangedVO(UiObject var1, String var2, String var3) {
      this.activeFastRoot = var1;
      this.activePackageName = var2;
      this.activeWindowClassName = var3;
   }

   public UiObject getActiveFastRoot() {
      return this.activeFastRoot;
   }

   public String getActivePackageName() {
      return this.activePackageName;
   }

   public String getActiveWindowClassName() {
      return this.activeWindowClassName;
   }

   public void setActiveFastRoot(UiObject var1) {
      this.activeFastRoot = var1;
   }

   public void setActivePackageName(String var1) {
      this.activePackageName = var1;
   }

   public void setActiveWindowClassName(String var1) {
      this.activeWindowClassName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("NoticeRootChangedVO{activeFastRoot=");
      var1.append(this.activeFastRoot);
      var1.append(", activePackageName='");
      var1.append(this.activePackageName);
      var1.append("', activeWindowClassName='");
      return a.n(var1, this.activeWindowClassName, "'}");
   }
}
