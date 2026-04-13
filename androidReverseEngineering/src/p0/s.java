package p0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class s {
   public final String[] a;

   public s(f var1) {
      ArrayList var2 = var1.a;
      this.a = var2.toArray(new String[var2.size()]);
   }

   public s(String[] var1) {
      this.a = var1;
   }

   public static void a(String var0) {
      if (var0 == null) {
         throw new NullPointerException("name == null");
      } else if (var0.isEmpty()) {
         throw new IllegalArgumentException("name is empty");
      } else {
         int var2 = var0.length();

         for (int var1 = 0; var1 < var2; var1++) {
            char var3 = var0.charAt(var1);
            if (var3 <= ' ' || var3 >= 127) {
               throw new IllegalArgumentException(q0.c.i(new Object[]{Integer.valueOf(var3), var1, var0}, "Unexpected char %#04x at %d in header name: %s"));
            }
         }
      }
   }

   public static void b(String var0, String var1) {
      if (var0 == null) {
         throw new NullPointerException(a.a.l("value for name ", var1, " == null"));
      } else {
         int var3 = var0.length();

         for (int var2 = 0; var2 < var3; var2++) {
            char var4 = var0.charAt(var2);
            if (var4 <= 31 && var4 != '\t' || var4 >= 127) {
               throw new IllegalArgumentException(q0.c.i(new Object[]{Integer.valueOf(var4), var2, var1, var0}, "Unexpected char %#04x at %d in %s value: %s"));
            }
         }
      }
   }

   public final String c(String var1) {
      String[] var4 = this.a;
      int var2 = var4.length;

      while (true) {
         int var3 = var2 - 2;
         if (var3 >= 0) {
            var2 = var3;
            if (!var1.equalsIgnoreCase(var4[var3])) {
               continue;
            }

            var1 = var4[var3 + 1];
            break;
         }

         var1 = null;
         break;
      }

      return var1;
   }

   public final String d(int var1) {
      return this.a[var1 * 2];
   }

   public final f e() {
      f var1 = new f();
      Collections.addAll(var1.a, this.a);
      return var1;
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof s && Arrays.equals((Object[])((s)var1).a, (Object[])this.a)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final String f(int var1) {
      return this.a[var1 * 2 + 1];
   }

   @Override
   public final int hashCode() {
      return Arrays.hashCode((Object[])this.a);
   }

   @Override
   public final String toString() {
      StringBuilder var3 = new StringBuilder();
      int var2 = this.a.length / 2;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.append(this.d(var1));
         var3.append(": ");
         var3.append(this.f(var1));
         var3.append("\n");
      }

      return var3.toString();
   }
}
