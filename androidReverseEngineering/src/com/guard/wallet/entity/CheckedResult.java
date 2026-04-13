package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class CheckedResult implements Serializable {
   private boolean checked;
   private boolean clicked;

   public CheckedResult() {
      this.checked = false;
      this.clicked = false;
   }

   public CheckedResult(boolean var1, boolean var2) {
      this.checked = var1;
      this.clicked = var2;
   }

   public boolean isChecked() {
      return this.checked;
   }

   public boolean isClicked() {
      return this.clicked;
   }

   public void setChecked(boolean var1) {
      this.checked = var1;
   }

   public void setClicked(boolean var1) {
      this.clicked = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CheckedResult{checked=");
      var1.append(this.checked);
      var1.append(", clicked=");
      var1.append(this.clicked);
      var1.append('}');
      return var1.toString();
   }
}
