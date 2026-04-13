package s0;

import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import p0.q;

public final class b {
   public final List a;
   public int b = 0;
   public boolean c;
   public boolean d;

   public b(List var1) {
      this.a = var1;
   }

   public final p0.k a(SSLSocket var1) {
      int var2 = this.b;
      List var5 = this.a;
      int var3 = var5.size();

      p0.k var6;
      while (true) {
         if (var2 >= var3) {
            var6 = null;
            break;
         }

         var6 = (p0.k)var5.get(var2);
         if (var6.a(var1)) {
            this.b = var2 + 1;
            break;
         }

         var2++;
      }

      if (var6 == null) {
         StringBuilder var22 = new StringBuilder("Unable to find acceptable protocols. isFallback=");
         var22.append(this.d);
         var22.append(", modes=");
         var22.append(var5);
         var22.append(", supported protocols=");
         var22.append(Arrays.toString((Object[])var1.getEnabledProtocols()));
         throw new UnknownServiceException(var22.toString());
      } else {
         var2 = this.b;

         boolean var4;
         while (true) {
            if (var2 >= var5.size()) {
               var4 = false;
               break;
            }

            if (((p0.k)var5.get(var2)).a(var1)) {
               var4 = true;
               break;
            }

            var2++;
         }

         this.c = var4;
         q var16 = q.c;
         var4 = this.d;
         var16.getClass();
         String[] var17 = var6.c;
         String[] var18;
         if (var17 != null) {
            var18 = q0.c.m(p0.i.b, var1.getEnabledCipherSuites(), var17);
         } else {
            var18 = var1.getEnabledCipherSuites();
         }

         String[] var7 = var6.d;
         if (var7 != null) {
            var7 = q0.c.m(q0.c.i, var1.getEnabledProtocols(), var7);
         } else {
            var7 = var1.getEnabledProtocols();
         }

         String[] var9 = var1.getSupportedCipherSuites();
         p0.h var8 = p0.i.b;
         byte[] var10 = q0.c.a;
         var3 = var9.length;
         var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var2 = -1;
               break;
            }

            if (var8.compare(var9[var2], "TLS_FALLBACK_SCSV") == 0) {
               break;
            }

            var2++;
         }

         String[] var25 = var18;
         if (var4) {
            var25 = var18;
            if (var2 != -1) {
               String var26 = var9[var2];
               var2 = var18.length + 1;
               var25 = new String[var2];
               System.arraycopy(var18, 0, var25, 0, var18.length);
               var25[var2 - 1] = var26;
            }
         }

         p0.j var19 = new p0.j(var6);
         var19.a(var25);
         var19.c(var7);
         p0.k var20 = new p0.k(var19);
         var7 = var20.d;
         if (var7 != null) {
            var1.setEnabledProtocols(var7);
         }

         String[] var21 = var20.c;
         if (var21 != null) {
            var1.setEnabledCipherSuites(var21);
         }

         return var6;
      }
   }
}
