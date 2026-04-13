package l0;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

public abstract class k implements f0.p, g0.a {
   public final com.guard.wallet.http.h d;
   public long e;
   public final f0.k f;
   public final h g;
   public boolean h;
   public f0.p i;
   public g0.c j;
   public boolean k;
   public int l;
   public final String m;
   public g0.a n;

   public k(f0.k var1, h var2) {
      com.guard.wallet.http.h var4 = new com.guard.wallet.http.h(4);
      this.d = var4;
      this.e = -1L;
      this.h = false;
      this.l = 200;
      this.m = "HTTP/1.1";
      this.f = var1;
      this.g = var2;
      i0.h var5 = i0.h.b;
      String var6 = var2.j.i("Connection");
      boolean var3;
      if (var6 == null) {
         var3 = true;
      } else {
         var3 = "keep-alive".equalsIgnoreCase(var6);
      }

      if (var3) {
         var4.k("Connection", "Keep-Alive");
      }
   }

   @Override
   public final void a(Exception var1) {
      this.l();
   }

   @Override
   public final f0.j b() {
      return ((f0.b)this.f).f;
   }

   @Override
   public final void c(f0.m var1) {
      if (!this.h) {
         this.e();
      }

      if (var1.c != 0) {
         f0.p var2 = this.i;
         if (var2 != null) {
            var2.c(var1);
         }
      }
   }

   @Override
   public final void d(g0.c var1) {
      f0.p var2 = this.i;
      if (var2 != null) {
         var2.d(var1);
      } else {
         this.j = var1;
      }
   }

   public final void e() {
      if (!this.h) {
         this.h = true;
         com.guard.wallet.http.h var6 = this.d;
         String var4 = var6.i("Transfer-Encoding");
         if ("".equals(var4)) {
            List var5 = (List)((i0.e)var6.e).remove("Transfer-Encoding".toLowerCase(Locale.US));
         }

         boolean var1;
         if (("Chunked".equalsIgnoreCase(var4) || var4 == null) && !"close".equalsIgnoreCase(var6.i("Connection"))) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         if (this.e < 0L) {
            var4 = var6.i("Content-Length");
            if (!TextUtils.isEmpty(var4)) {
               this.e = Long.valueOf(var4);
            }
         }

         boolean var3;
         if (this.e < 0L && var1) {
            var6.k("Transfer-Encoding", "Chunked");
            var3 = true;
         } else {
            var3 = false;
         }

         Locale var7 = Locale.ENGLISH;
         String var8 = this.m;
         int var2 = this.l;
         var1 = this.l;
         String var13 = (String)l0.f.e.get(var1);
         var4 = var13;
         if (var13 == null) {
            var4 = "Unknown";
         }

         byte[] var14 = var6.l(String.format(var7, "%s %s %s", var8, var2, var4)).getBytes();
         j var12 = new j(this, var3);
         a1.q.T(this.f, var14, var12);
      }
   }

   @Override
   public final void f(g0.a var1) {
      f0.p var2 = this.i;
      if (var2 != null) {
         var2.f(var1);
      } else {
         this.n = var1;
      }
   }

   public abstract void g();

   public final void h(String var1) {
      String var3 = this.d.i("Content-Type");
      String var2 = var3;
      if (var3 == null) {
         var2 = "text/html; charset=utf-8";
      }

      try {
         byte[] var7 = var1.getBytes("UTF-8");
         f0.m var6 = new f0.m(var7);
         f0.j var8 = this.b();
         i var4 = new i(this, var6, var2, 0);
         var8.c(var4);
      } catch (UnsupportedEncodingException var5) {
         throw new AssertionError(var5);
      }
   }

   @Override
   public final g0.c i() {
      f0.p var1 = this.i;
      return var1 != null ? var1.i() : this.j;
   }

   @Override
   public final void l() {
      if (!this.k) {
         this.k = true;
         boolean var1 = this.h;
         if (!var1 || this.i != null) {
            if (!var1) {
               com.guard.wallet.http.h var2 = this.d;
               var2.getClass();
               Locale var3 = Locale.US;
               String var4 = "Transfer-Encoding".toLowerCase(var3);
               List var6 = (List)((i0.e)var2.e).remove(var4.toLowerCase(var3));
               if (var6 != null && var6.size() != 0) {
                  String var7 = (String)var6.get(0);
               }
            }

            f0.p var8 = this.i;
            if (var8 instanceof k0.b) {
               var8.l();
            } else {
               if (!this.h) {
                  if (!this.g.n.equalsIgnoreCase("HEAD")) {
                     try {
                        byte[] var10 = "".getBytes("UTF-8");
                        f0.m var9 = new f0.m(var10);
                        f0.j var11 = this.b();
                        i var12 = new i(this, var9, "text/html", 0);
                        var11.c(var12);
                        return;
                     } catch (UnsupportedEncodingException var5) {
                        throw new AssertionError(var5);
                     }
                  }

                  this.e();
               }

               this.g();
            }
         }
      }
   }

   @Override
   public final String toString() {
      com.guard.wallet.http.h var7 = this.d;
      if (var7 == null) {
         return super.toString();
      } else {
         Locale var6 = Locale.ENGLISH;
         String var5 = this.m;
         int var2 = this.l;
         int var1 = this.l;
         String var4 = (String)l0.f.e.get(var1);
         String var3 = var4;
         if (var4 == null) {
            var3 = "Unknown";
         }

         return var7.l(String.format(var6, "%s %s %s", var5, var2, var3));
      }
   }
}
