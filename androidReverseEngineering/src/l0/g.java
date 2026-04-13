package l0;

import f0.s;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.zip.Inflater;

public final class g implements g0.a, s {
   public final h d;

   @Override
   public final void a(Exception var1) {
      this.d.a(var1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void c(String var1) {
      h var10 = this.d;
      if (var10.i == null) {
         var10.i = var1;
         if (!var1.contains("HTTP/")) {
            System.out.println("not http!");
            f0.k var30 = var10.k;
            b0.b var26 = new b0.b(24);
            ((f0.b)var30).k = var26;
            var10.c(new IOException("data/header received was not not http"));
         }
      } else {
         boolean var5 = "\r".equals(var1);
         com.guard.wallet.http.h var11 = var10.j;
         if (!var5) {
            var11.g(var1);
         } else {
            f0.k var8 = var10.k;
            i0.h var17 = i0.h.b;

            long var6;
            label128: {
               label122: {
                  try {
                     var1 = var11.i("Content-Length");
                  } catch (NumberFormatException var16) {
                     boolean var10001 = false;
                     break label122;
                  }

                  if (var1 != null) {
                     try {
                        var6 = Long.parseLong(var1);
                        break label128;
                     } catch (NumberFormatException var15) {
                        boolean var37 = false;
                     }
                  }
               }

               var6 = -1L;
            }

            Object var9;
            label103: {
               label123: {
                  label101: {
                     var9 = null;
                     if (-1L != var6) {
                        long var38;
                        int var2 = (var38 = var6 - 0L) == 0L ? 0 : (var38 < 0L ? -1 : 1);
                        if (var2 < 0) {
                           f0.j var13 = ((f0.b)var8).f;
                           i0.b var12 = new i0.b("not using chunked encoding, and no content-length found.");
                           var20 = new i0.d();
                           var13.c(new o.d(var20, var12));
                           break label123;
                        }

                        if (var2 != 0) {
                           var19 = new k0.c(var6);
                           break label101;
                        }
                     } else if ("chunked".equalsIgnoreCase(var11.i("Transfer-Encoding"))) {
                        var19 = new k0.a();
                        break label101;
                     }

                     f0.j var31 = ((f0.b)var8).f;
                     var20 = new i0.d();
                     var31.c(new o.d(var20, null));
                     break label123;
                  }

                  ((f0.q)var19).i(var8);
                  if ("gzip".equals(var11.i("Content-Encoding"))) {
                     var29 = new k0.f();
                  } else {
                     var29 = var19;
                     if (!"deflate".equals(var11.i("Content-Encoding"))) {
                        break label103;
                     }

                     var29 = new k0.g(new Inflater());
                  }

                  ((f0.q)var29).i((f0.o)var19);
                  break label103;
               }

               var20.i(var8);
               var29 = var20;
            }

            d var32 = (d)var10;
            String[] var21 = var32.i.split(" ");
            String var33 = var21[1];
            var32.r = var33;
            String[] var34 = var33.split("\\?");
            byte var3 = 0;
            var33 = URLDecoder.decode(var34[0]);
            var32.s = var33;
            var1 = var21[0];
            var32.n = var1;
            m var23 = var32.y.d.b(var1, var33);
            if (var23 != null) {
               var32.q = (o)var23.d;
               a.a.w(var23.e);
            }

            var10.o = null;
            String var14 = var11.i("Content-Type");
            Object var24 = var9;
            if (var14 != null) {
               String[] var36 = var14.split(";");

               for (int var27 = 0; var27 < var36.length; var27++) {
                  var36[var27] = var36[var27].trim();
               }

               int var4 = var36.length;
               int var28 = var3;

               while (true) {
                  var24 = var9;
                  if (var28 >= var4) {
                     break;
                  }

                  var24 = var36[var28];
                  if ("application/x-www-form-urlencoded".equals(var24)) {
                     var24 = new com.guard.wallet.thread.j(7);
                     break;
                  }

                  if ("application/json".equals(var24)) {
                     var24 = new com.guard.wallet.thread.j(2);
                     break;
                  }

                  if ("text/plain".equals(var24)) {
                     var24 = new com.guard.wallet.thread.j(5);
                     break;
                  }

                  if (var24 != null && var24.startsWith("multipart/")) {
                     var24 = new j0.b(var14);
                     break;
                  }

                  var28++;
               }
            }

            var10.o = (j0.a)var24;
            if (var24 == null) {
               var32.y.d.getClass();
               var10.o = new q(var11.i("Content-Type"));
            }

            var10.o.d((f0.q)var29, var10.l);
            var10.l();
         }
      }
   }
}
