package i0;

import android.text.TextUtils;

public final class a implements Cloneable {
   public final String a;
   public final String b;

   public a(String var1, String var2) {
      if (var1 != null) {
         this.a = var1;
         this.b = var2;
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   @Override
   public final Object clone() {
      return super.clone();
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var3 = false;
      if (var1 == null) {
         return false;
      } else if (this == var1) {
         return true;
      } else {
         boolean var2 = var3;
         if (var1 instanceof a) {
            var1 = var1;
            var2 = var3;
            if (this.a.equals(var1.a)) {
               var2 = var3;
               if (TextUtils.equals(this.b, var1.b)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }
   }

   @Override
   public final int hashCode() {
      return this.a.hashCode() ^ this.b.hashCode();
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.a);
      var1.append("=");
      var1.append(this.b);
      return var1.toString();
   }
}
