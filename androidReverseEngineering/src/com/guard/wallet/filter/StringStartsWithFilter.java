package com.guard.wallet.filter;

import a.a;
import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import j.e;
import t.b;

public class StringStartsWithFilter implements Filter {
   private b keyGetter;
   private String prefix;

   public StringStartsWithFilter(b var1, String var2) {
      this.prefix = var2;
      this.keyGetter = var1;
   }

   @Override
   public Boolean filter(UiObject var1) {
      if (q.B(this.prefix)) {
         return Boolean.FALSE;
      } else {
         String var3 = ((e)this.keyGetter).f(var1);
         boolean var2;
         if (var3 != null && var3.toLowerCase().startsWith(this.prefix.toLowerCase())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   public b getKeyGetter() {
      return this.keyGetter;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setKeyGetter(b var1) {
      this.keyGetter = var1;
   }

   public void setPrefix(String var1) {
      this.prefix = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.keyGetter.toString());
      var1.append("StartsWith(\"");
      return a.n(var1, this.prefix, "\")");
   }
}
