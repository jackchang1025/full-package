package t0;

import a1.n;
import a1.o;
import a1.t;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.logging.Logger;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.k0;
import p0.l0;
import p0.q;
import p0.w;
import s0.l;

public final class a implements w {
   public final boolean a;

   public a(boolean var1) {
      this.a = var1;
   }

   @Override
   public final j0 a(f var1) {
      s0.e var11 = var1.c;
      if (var11 != null) {
         b var12 = var11.d;
         q var13 = var11.b;
         f0 var14 = var1.e;
         long var7 = System.currentTimeMillis();

         try {
            var13.getClass();
            var12.e(var14);
         } catch (IOException var20) {
            var13.getClass();
            var11.c(var20);
            throw var20;
         }

         boolean var4;
         i0 var33;
         label92: {
            boolean var9 = a1.q.I(var14.b);
            boolean var3 = true;
            l var15 = var11.a;
            if (var9) {
               a1.q var10 = var14.d;
               if (var10 != null) {
                  boolean var2;
                  i0 var21;
                  if ("100-continue".equalsIgnoreCase(var14.a("Expect"))) {
                     try {
                        var12.d();
                     } catch (IOException var19) {
                        var13.getClass();
                        var11.c(var19);
                        throw var19;
                     }

                     var13.getClass();
                     var21 = var11.b(true);
                     var2 = true;
                  } else {
                     var21 = null;
                     var2 = false;
                  }

                  if (var21 == null) {
                     var11.e = false;
                     long var5 = var10.i();
                     var13.getClass();
                     s0.c var40 = new s0.c(var11, var12.b(var14, var5), var5);
                     Logger var16 = a1.l.a;
                     n var41 = new n(var40);
                     var10.V(var41);
                     var41.close();
                     var33 = var21;
                     var4 = var2;
                  } else {
                     var15.c(var11, true, false, null);
                     if (var11.a().h == null) {
                        var3 = false;
                     }

                     var33 = var21;
                     var4 = var2;
                     if (!var3) {
                        var12.h().h();
                        var33 = var21;
                        var4 = var2;
                     }
                  }
                  break label92;
               }
            }

            var15.c(var11, true, false, null);
            var33 = null;
            var4 = false;
         }

         try {
            var12.c();
         } catch (IOException var18) {
            var13.getClass();
            var11.c(var18);
            throw var18;
         }

         if (!var4) {
            var13.getClass();
         }

         i0 var22 = var33;
         if (var33 == null) {
            var22 = var11.b(false);
         }

         var22.a = var14;
         var22.e = var11.a().f;
         var22.k = var7;
         var22.l = System.currentTimeMillis();
         j0 var23 = var22.a();
         int var31 = var23.c;
         int var30 = var31;
         if (var31 == 100) {
            i0 var24 = var11.b(false);
            var24.a = var14;
            var24.e = var11.a().f;
            var24.k = var7;
            var24.l = System.currentTimeMillis();
            var23 = var24.a();
            var30 = var23.c;
         }

         var13.getClass();
         i0 var27;
         if (this.a && var30 == 101) {
            var27 = new i0(var23);
            var27.g = q0.c.d;
         } else {
            var33 = new i0(var23);

            try {
               String var36 = var23.x("Content-Type", null);
               long var32 = var12.f(var23);
               t var37 = var12.a(var23);
               s0.d var25 = new s0.d(var11, var37, var32);
               Logger var38 = a1.l.a;
               o var39 = new o(var25);
               var26 = new k0(var36, var32, var39);
            } catch (IOException var17) {
               var11.c(var17);
               throw var17;
            }

            var33.g = var26;
            var27 = var33;
         }

         j0 var28 = var27.a();
         if ("close".equalsIgnoreCase(var28.a.a("Connection")) || "close".equalsIgnoreCase(var28.x("Connection", null))) {
            var12.h().h();
         }

         if (var30 == 204 || var30 == 205) {
            l0 var35 = var28.g;
            if (((k0)var35).b > 0L) {
               StringBuilder var29 = a.a.q("HTTP ", var30, " had non-zero Content-Length: ");
               var29.append(((k0)var35).b);
               throw new ProtocolException(var29.toString());
            }
         }

         return var28;
      } else {
         throw new IllegalStateException();
      }
   }
}
