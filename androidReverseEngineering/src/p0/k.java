package p0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLSocket;

public final class k {
   public static final k e;
   public static final k f = new k(new j(false));
   public final boolean a;
   public final boolean b;
   public final String[] c;
   public final String[] d;

   static {
      i var2 = i.q;
      i var3 = i.r;
      i var5 = i.s;
      i var7 = i.k;
      i var6 = i.m;
      i var10 = i.l;
      i var8 = i.n;
      i var4 = i.p;
      i var9 = i.o;
      i[] var0 = new i[]{var2, var3, var5, var7, var6, var10, var8, var4, var9, i.i, i.j, i.g, i.h, i.e, i.f, i.d};
      j var1 = new j(true);
      var1.b(var2, var3, var5, var7, var6, var10, var8, var4, var9);
      n0 var14 = n0.b;
      n0 var13 = n0.c;
      var1.d(var14, var13);
      var1.d = true;
      new k(var1);
      var1 = new j(true);
      var1.b(var0);
      var1.d(var14, var13);
      var1.d = true;
      e = new k(var1);
      var1 = new j(true);
      var1.b(var0);
      var1.d(var14, var13, n0.d, n0.e);
      var1.d = true;
      new k(var1);
   }

   public k(j var1) {
      this.a = var1.a;
      this.c = var1.b;
      this.d = var1.c;
      this.b = var1.d;
   }

   public final boolean a(SSLSocket var1) {
      if (!this.a) {
         return false;
      } else {
         String[] var2 = this.d;
         if (var2 != null && !q0.c.o(q0.c.i, var2, var1.getEnabledProtocols())) {
            return false;
         } else {
            var2 = this.c;
            return var2 == null || q0.c.o(i.b, var2, var1.getEnabledCipherSuites());
         }
      }
   }

   @Override
   public final boolean equals(Object var1) {
      if (!(var1 instanceof k)) {
         return false;
      } else if (var1 == this) {
         return true;
      } else {
         var1 = var1;
         boolean var3 = var1.a;
         boolean var2 = this.a;
         if (var2 != var3) {
            return false;
         } else {
            if (var2) {
               if (!Arrays.equals((Object[])this.c, (Object[])var1.c)) {
                  return false;
               }

               if (!Arrays.equals((Object[])this.d, (Object[])var1.d)) {
                  return false;
               }

               if (this.b != var1.b) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public final int hashCode() {
      int var1;
      if (this.a) {
         var1 = ((527 + Arrays.hashCode((Object[])this.c)) * 31 + Arrays.hashCode((Object[])this.d)) * 31 + (this.b ^ 1);
      } else {
         var1 = 17;
      }

      return var1;
   }

   @Override
   public final String toString() {
      if (!this.a) {
         return "ConnectionSpec()";
      } else {
         StringBuilder var6 = new StringBuilder("ConnectionSpec(cipherSuites=");
         byte var2 = 0;
         Object var5 = null;
         String[] var7 = this.c;
         List var10;
         if (var7 != null) {
            ArrayList var4 = new ArrayList(var7.length);
            int var3 = var7.length;

            for (int var1 = 0; var1 < var3; var1++) {
               var4.add(i.a(var7[var1]));
            }

            var10 = Collections.unmodifiableList(var4);
         } else {
            var10 = null;
         }

         var6.append(Objects.toString(var10, "[all enabled]"));
         var6.append(", tlsVersions=");
         var7 = this.d;
         var10 = (List)var5;
         if (var7 != null) {
            ArrayList var12 = new ArrayList(var7.length);
            int var9 = var7.length;

            for (int var8 = var2; var8 < var9; var8++) {
               var12.add(n0.a(var7[var8]));
            }

            var10 = Collections.unmodifiableList(var12);
         }

         var6.append(Objects.toString(var10, "[all enabled]"));
         var6.append(", supportsTlsExtensions=");
         var6.append(this.b);
         var6.append(")");
         return var6.toString();
      }
   }
}
