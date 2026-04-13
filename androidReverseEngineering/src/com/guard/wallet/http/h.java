package com.guard.wallet.http;

import com.guard.wallet.entity.CookieVO;
import com.guard.wallet.entity.HostCookies;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import p0.m0;

public final class h implements p0.n, g0.a, g0.b, f0.v {
   public final int d;
   public Object e;

   public h(int var1) {
      this.d = var1;
      if (var1 != 4) {
         if (var1 != 8) {
            if (var1 != 9) {
               super();
               this.e = new Semaphore(0);
            } else {
               super();
               this.e = new LinkedHashSet();
            }
         } else {
            this(TimeUnit.MINUTES);
         }
      } else {
         super();
         this.e = new i0.c();
      }
   }

   public h(TimeUnit var1) {
      this.d = 8;
      super();
      this.e = new s0.h(var1);
   }

   @Override
   public final void a(Exception var1) {
      ((f0.q)this.e).c(var1);
   }

   @Override
   public final void b(f0.o var1, f0.m var2) {
      var2.c(((j0.b)((com.guard.wallet.thread.j)this.e).f).l);
   }

   @Override
   public final void c(Object var1) {
      byte[] var2 = (byte[])var1;
      var1 = ((k0.d)this.e).e;
      if (var1.e) {
         var1.h.l.update(var2, 0, var2.length);
      }

      ((k0.d)this.e).e.a();
   }

   @Override
   public final List d(p0.u var1) {
      String var2 = com.guard.wallet.utils.h.l("Cookies:".concat(var1.d));
      HostCookies var4;
      if (!a1.q.B(var2)) {
         var4 = (HostCookies)com.guard.wallet.utils.h.d(var2, HostCookies.class);
      } else {
         var4 = null;
      }

      HostCookies var3 = var4;
      if (var4 == null) {
         var3 = new HostCookies();
         var3.setHost(var1.d);
      }

      return var3.loadForRequest();
   }

   @Override
   public final void e(p0.u var1, List var2) {
      if (!var2.isEmpty()) {
         String var6 = "Cookies:".concat(var1.d);
         String var4 = com.guard.wallet.utils.h.l(var6);
         HostCookies var10;
         if (!a1.q.B(var4)) {
            var10 = (HostCookies)com.guard.wallet.utils.h.d(var4, HostCookies.class);
         } else {
            var10 = null;
         }

         HostCookies var5 = var10;
         if (var10 == null) {
            var5 = new HostCookies();
            var5.setHost(var1.d);
         }

         for (p0.m var8 : var2) {
            CookieVO var9 = new CookieVO(var8.a, var8.b, var8.c, var8.d, var8.e, var8.f, var8.g, var8.h, var8.i);
            int var3 = var5.getCookies().indexOf(var9);
            if (var3 >= 0) {
               var5.getCookies().set(var3, var9);
            } else {
               var5.getCookies().add(var9);
            }
         }

         com.guard.wallet.utils.h.D(com.guard.wallet.utils.h.N(var5), var6);
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void f(String var1, String var2) {
      String var6 = var1.toLowerCase(Locale.US);
      i0.e var5 = (i0.e)this.e;
      List var4 = (List)var5.get(var6);
      List var3 = var4;
      if (var4 == null) {
         var3 = var5.b();
         var5.put(var6, var3);
      }

      var3.add(var2);
      n0.e var9 = (n0.e)((i0.e)this.e).get(var6);
      synchronized (var9){} // $VF: monitorenter 

      try {
         if (var9.a == null) {
            var9.a = var1;
         }
      } finally {
         // $VF: monitorexit
      }
   }

   public final void g(String var1) {
      if (var1 != null) {
         String[] var2 = var1.trim().split(":", 2);
         if (var2.length == 2) {
            this.f(var2[0].trim(), var2[1].trim());
         } else {
            this.f(var2[0].trim(), "");
         }
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void h(m0 var1) {
      synchronized (this){} // $VF: monitorenter 

      try {
         ((Set)this.e).remove(var1);
      } finally {
         // $VF: monitorexit
      }
   }

   public final String i(String var1) {
      return ((i0.e)this.e).a(var1.toLowerCase(Locale.US));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final h0.h j(f0.q var1) {
      String var2 = var1.g();
      h0.h var24 = new b0.b(27).f(var1);
      f0.l var3 = new f0.l(new h0.e(this, var2));
      h0.h var26 = new h0.h();
      synchronized (var26){} // $VF: monitorenter 

      label143: {
         Throwable var10000;
         label144: {
            label136: {
               try {
                  if (var26.a) {
                     break label136;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  boolean var10001 = false;
                  break label144;
               }

               try {
                  var26.c = var24;
               } catch (Throwable var22) {
                  var10000 = var22;
                  boolean var27 = false;
                  break label144;
               }
            }

            label129:
            try {
               // $VF: monitorexit
               break label143;
            } catch (Throwable var21) {
               var10000 = var21;
               boolean var28 = false;
               break label129;
            }
         }

         while (true) {
            Throwable var25 = var10000;

            try {
               // $VF: monitorexit
               throw var25;
            } catch (Throwable var20) {
               var10000 = var20;
               boolean var29 = false;
               continue;
            }
         }
      }

      var24.f(null, new h0.e(var26, var3));
      return var26;
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void k(String var1, String var2) {
      if (var2 == null || !var2.contains("\n") && !var2.contains("\r")) {
         String var4 = var1.toLowerCase(Locale.US);
         i0.e var3 = (i0.e)this.e;
         List var5 = var3.b();
         var5.add(var2);
         var3.put(var4, var5);
         n0.e var8 = (n0.e)((i0.e)this.e).get(var4);
         synchronized (var8){} // $VF: monitorenter 

         try {
            if (var8.a == null) {
               var8.a = var1;
            }
         } finally {
            // $VF: monitorexit
         }
      } else {
         throw new IllegalArgumentException("value must not contain a new line or line feed");
      }
   }

   public final String l(String var1) {
      StringBuilder var3 = this.m();
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append("\r\n");
      return var3.insert(0, var2.toString()).toString();
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final StringBuilder m() {
      StringBuilder var1 = new StringBuilder(256);

      for (String var3 : ((i0.e)this.e).keySet()) {
         n0.e var9 = (n0.e)((i0.e)this.e).get(var3);

         for (String var4 : var9) {
            synchronized (var9){} // $VF: monitorenter 

            Object var5;
            try {
               var5 = var9.a;
            } finally {
               // $VF: monitorexit
            }

            var1.append((String)var5);
            var1.append(": ");
            var1.append(var4);
            var1.append("\r\n");
         }
      }

      var1.append("\r\n");
      return var1;
   }

   @Override
   public final String toString() {
      switch (this.d) {
         case 4:
            return this.m().toString();
         default:
            return super.toString();
      }
   }
}
