package com.guard.wallet.filter;

import a.a;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import j.e;
import java.util.Objects;
import t.b;

public class StringEqualsFilter implements Filter {
   private b keyGetter;
   private String value;

   public StringEqualsFilter(b var1, String var2) {
      this.keyGetter = var1;
      this.value = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      String var3 = ((e)this.keyGetter).f(var1);
      boolean var2;
      if (var3 != null) {
         var2 = var3.equalsIgnoreCase(this.value);
      } else {
         var2 = Objects.equals(this.value, "NULL");
      }

      return var2;
   }

   public b getKeyGetter() {
      return this.keyGetter;
   }

   public String getValue() {
      return this.value;
   }

   public void setKeyGetter(b var1) {
      this.keyGetter = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.keyGetter.toString());
      var1.append("(\"");
      return a.n(var1, this.value, "\")");
   }
}
