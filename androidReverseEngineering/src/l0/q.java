package l0;

import java.net.ProtocolException;
import p0.c0;

public final class q implements j0.a {
   public final int d;
   public final int e;
   public final String f;
   public Object g;

   public q(String var1) {
      this.d = 0;
      super();
      this.e = -1;
      this.f = var1;
   }

   public q(c0 var1, int var2, String var3) {
      this.d = 1;
      super();
      this.g = var1;
      this.e = var2;
      this.f = var3;
   }

   public static q a(String var0) {
      boolean var4 = var0.startsWith("HTTP/1.");
      c0 var5 = c0.b;
      byte var1;
      if (var4) {
         int var2 = var0.length();
         var1 = 9;
         if (var2 < 9 || var0.charAt(8) != ' ') {
            throw new ProtocolException("Unexpected status line: ".concat(var0));
         }

         var2 = var0.charAt(7) - '0';
         if (var2 != 0) {
            if (var2 != 1) {
               throw new ProtocolException("Unexpected status line: ".concat(var0));
            }

            var5 = c0.c;
         }
      } else {
         if (!var0.startsWith("ICY ")) {
            throw new ProtocolException("Unexpected status line: ".concat(var0));
         }

         var1 = 4;
      }

      int var3 = var0.length();
      int var9 = var1 + 3;
      if (var3 >= var9) {
         try {
            var3 = Integer.parseInt(var0.substring(var1, var9));
         } catch (NumberFormatException var6) {
            throw new ProtocolException("Unexpected status line: ".concat(var0));
         }

         if (var0.length() > var9) {
            if (var0.charAt(var9) != ' ') {
               throw new ProtocolException("Unexpected status line: ".concat(var0));
            }

            var0 = var0.substring(var1 + 4);
         } else {
            var0 = "";
         }

         return new q(var5, var3, var0);
      } else {
         throw new ProtocolException("Unexpected status line: ".concat(var0));
      }
   }

   @Override
   public final void d(f0.q var1, g var2) {
      this.g = var1;
      var1.e = var2;
      var1.h(new b0.b(24));
   }

   @Override
   public final boolean f() {
      return false;
   }

   @Override
   public final int length() {
      return this.e;
   }

   @Override
   public final String toString() {
      switch (this.d) {
         case 1:
            StringBuilder var2 = new StringBuilder();
            String var1;
            if ((c0)this.g == c0.b) {
               var1 = "HTTP/1.0";
            } else {
               var1 = "HTTP/1.1";
            }

            var2.append(var1);
            var2.append(' ');
            var2.append(this.e);
            var1 = this.f;
            if (var1 != null) {
               var2.append(' ');
               var2.append(var1);
            }

            return var2.toString();
         default:
            return super.toString();
      }
   }
}
