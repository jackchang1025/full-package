package r0;

import a1.l;
import a1.o;
import com.guard.wallet.thread.j;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import l0.m;
import p0.c0;
import p0.d;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.k0;
import p0.n;
import p0.q;
import p0.s;
import p0.u;
import p0.w;
import p0.x;
import q0.c;
import t0.e;
import t0.f;

public final class a implements w {
   public final int a;
   public final Object b;

   public static boolean b(String var0) {
      boolean var1;
      if (!"Content-Length".equalsIgnoreCase(var0) && !"Content-Encoding".equalsIgnoreCase(var0) && !"Content-Type".equalsIgnoreCase(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean c(String var0) {
      boolean var1;
      if (!"Connection".equalsIgnoreCase(var0)
         && !"Keep-Alive".equalsIgnoreCase(var0)
         && !"Proxy-Authenticate".equalsIgnoreCase(var0)
         && !"Proxy-Authorization".equalsIgnoreCase(var0)
         && !"TE".equalsIgnoreCase(var0)
         && !"Trailers".equalsIgnoreCase(var0)
         && !"Transfer-Encoding".equalsIgnoreCase(var0)
         && !"Upgrade".equalsIgnoreCase(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static j0 d(j0 var0) {
      j0 var1 = var0;
      if (var0 != null) {
         var1 = var0;
         if (var0.g != null) {
            i0 var2 = new i0(var0);
            var2.g = null;
            var1 = var2.a();
         }
      }

      return var1;
   }

   @Override
   public final j0 a(f var1) {
      int var2 = this.a;
      n var10 = (n)this.b;
      switch (var2) {
         case 0:
            a.a.w(var10);
            System.currentTimeMillis();
            f0 var54 = var1.e;
            j var45 = new j(var54, null);
            j var27 = var45;
            if ((f0)var45.e != null) {
               d var35 = var54.f;
               if (var35 == null) {
                  var35 = d.a(var54.c);
                  var54.f = var35;
               }

               var27 = var45;
               if (var35.j) {
                  var27 = new j(null, null);
               }
            }

            f0 var36 = (f0)var27.e;
            j0 var28 = (j0)var27.f;
            a.a.w(var10);
            j0 var18;
            if (var36 == null && var28 == null) {
               i0 var33 = new i0();
               var33.a = var1.e;
               var33.b = c0.c;
               var33.c = 504;
               var33.d = "Unsatisfiable Request (only-if-cached)";
               var33.g = c.d;
               var33.k = -1L;
               var33.l = System.currentTimeMillis();
               var18 = var33.a();
            } else if (var36 == null) {
               var28.getClass();
               i0 var17 = new i0(var28);
               j0 var29 = d(var28);
               if (var29 != null) {
                  i0.b("cacheResponse", var29);
               }

               var17.i = var29;
               var18 = var17.a();
            } else {
               j0 var19 = var1.a(var36);
               if (var28 != null) {
                  if (var19.c == 304) {
                     i0 var38 = new i0(var28);
                     ArrayList var46 = new ArrayList(20);
                     s var57 = var28.f;
                     int var24 = var57.a.length / 2;
                     var2 = 0;

                     while (true) {
                        s var55 = var19.f;
                        if (var2 >= var24) {
                           var24 = var55.a.length / 2;

                           for (int var23 = 0; var23 < var24; var23++) {
                              String var61 = var55.d(var23);
                              if (!b(var61) && c(var61)) {
                                 q var60 = q.c;
                                 String var58 = var55.f(var23);
                                 var60.getClass();
                                 var46.add(var61);
                                 var46.add(var58.trim());
                              }
                           }

                           String[] var56 = var46.toArray(new String[var46.size()]);
                           p0.f var47 = new p0.f();
                           Collections.addAll(var47.a, var56);
                           var38.f = var47;
                           var38.k = var19.k;
                           var38.l = var19.l;
                           j0 var31 = d(var28);
                           if (var31 != null) {
                              i0.b("cacheResponse", var31);
                           }

                           var38.i = var31;
                           j0 var32 = d(var19);
                           if (var32 != null) {
                              i0.b("networkResponse", var32);
                           }

                           var38.h = var32;
                           var38.a();
                           var19.g.close();
                           a.a.w(var10);
                           throw null;
                        }

                        String var14 = var57.d(var2);
                        String var59 = var57.f(var2);
                        if ((!"Warning".equalsIgnoreCase(var14) || !var59.startsWith("1")) && (b(var14) || !c(var14) || var55.c(var14) == null)) {
                           q.c.getClass();
                           var46.add(var14);
                           var46.add(var59.trim());
                        }

                        var2++;
                     }
                  }

                  c.c(var28.g);
               }

               i0 var37 = new i0(var19);
               j0 var30 = d(var28);
               if (var30 != null) {
                  i0.b("cacheResponse", var30);
               }

               var37.i = var30;
               j0 var20 = d(var19);
               if (var20 != null) {
                  i0.b("networkResponse", var20);
               }

               var37.h = var20;
               var18 = var37.a();
               a.a.w(var10);
            }

            return var18;
         default:
            f0 var7 = var1.e;
            var7.getClass();
            m var8 = new m(var7);
            a1.q var9 = var7.d;
            if (var9 != null) {
               x var11 = var9.j();
               if (var11 != null) {
                  ((p0.f)var8.c).c("Content-Type", var11.a);
               }

               long var5 = var9.i();
               if (var5 != -1L) {
                  String var39 = Long.toString(var5);
                  ((p0.f)var8.c).c("Content-Length", var39);
                  var8.c("Transfer-Encoding");
               } else {
                  ((p0.f)var8.c).c("Transfer-Encoding", "chunked");
                  var8.c("Content-Length");
               }
            }

            String var50 = var7.a("Host");
            u var40 = var7.a;
            if (var50 == null) {
               var50 = c.j(var40, false);
               ((p0.f)var8.c).c("Host", var50);
            }

            if (var7.a("Connection") == null) {
               ((p0.f)var8.c).c("Connection", "Keep-Alive");
            }

            boolean var21;
            if (var7.a("Accept-Encoding") == null && var7.a("Range") == null) {
               ((p0.f)var8.c).c("Accept-Encoding", "gzip");
               var21 = true;
            } else {
               var21 = false;
            }

            var10 = var10;
            List var52 = var10.d(var40);
            if (!var52.isEmpty()) {
               StringBuilder var12 = new StringBuilder();
               int var4 = var52.size();

               for (int var3 = 0; var3 < var4; var3++) {
                  if (var3 > 0) {
                     var12.append("; ");
                  }

                  p0.m var13 = (p0.m)var52.get(var3);
                  var12.append(var13.a);
                  var12.append('=');
                  var12.append(var13.b);
               }

               var50 = var12.toString();
               ((p0.f)var8.c).c("Cookie", var50);
            }

            if (var7.a("User-Agent") == null) {
               ((p0.f)var8.c).c("User-Agent", "android okhttp3");
            }

            j0 var15 = var1.a(var8.a());
            e.d(var10, var40, var15.f);
            i0 var34 = new i0(var15);
            var34.a = var7;
            if (var21 && "gzip".equalsIgnoreCase(var15.x("Content-Encoding", null)) && e.b(var15)) {
               a1.j var26 = new a1.j(((k0)var15.g).c);
               p0.f var41 = var15.f.e();
               var41.b("Content-Encoding");
               var41.b("Content-Length");
               ArrayList var42 = var41.a;
               String[] var49 = var42.toArray(new String[var42.size()]);
               p0.f var43 = new p0.f();
               Collections.addAll(var43.a, var49);
               var34.f = var43;
               String var44 = var15.x("Content-Type", null);
               Logger var16 = l.a;
               var34.g = new k0(var44, -1L, new o(var26));
            }

            return var34.a();
      }
   }
}
