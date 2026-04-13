package com.guard.wallet.stat;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class KeyboardEventVO implements Serializable {
   private String beforeText;
   private String editText;
   private String eventText;

   public KeyboardEventVO() {
   }

   public KeyboardEventVO(String var1, String var2, String var3) {
      this.beforeText = var1;
      this.editText = var2;
      this.eventText = var3;
   }

   public String getBeforeText() {
      return this.beforeText;
   }

   public String getEditText() {
      return this.editText;
   }

   public String getEventText() {
      return this.eventText;
   }

   public void setBeforeText(String var1) {
      this.beforeText = var1;
   }

   public void setEditText(String var1) {
      this.editText = var1;
   }

   public void setEventText(String var1) {
      this.eventText = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("KeyboardEventStatVO{beforeText='");
      var1.append(this.beforeText);
      var1.append("', editText='");
      var1.append(this.editText);
      var1.append("', eventText='");
      return a.n(var1, this.eventText, "'}");
   }
}
