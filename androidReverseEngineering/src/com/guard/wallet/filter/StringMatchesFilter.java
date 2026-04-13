package com.guard.wallet.filter;

import a.a;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import j.e;
import t.b;

public class StringMatchesFilter implements Filter {
   private b keyGetter;
   private String regex;

   public StringMatchesFilter(b var1, String var2) {
      this.keyGetter = var1;
      this.regex = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      String var3 = ((e)this.keyGetter).f(var1);
      boolean var2;
      if (var3 != null && var3.matches(this.regex)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public b getKeyGetter() {
      return this.keyGetter;
   }

   public String getRegex() {
      return this.regex;
   }

   public void setKeyGetter(b var1) {
      this.keyGetter = var1;
   }

   public void setRegex(String var1) {
      this.regex = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.keyGetter.toString());
      var1.append("Matches(\"");
      return a.n(var1, this.regex, "\")");
   }
}
