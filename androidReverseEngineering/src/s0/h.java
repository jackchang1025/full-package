package s0;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Proxy.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import p0.m0;
import p0.q;
import p0.u;

public final class h {
   public static final ThreadPoolExecutor g;
   public final int a;
   public final long b;
   public final o.a c = new o.a(this, 8);
   public final ArrayDeque d = new ArrayDeque();
   public final com.guard.wallet.http.h e = new com.guard.wallet.http.h(9);
   public boolean f;

   static {
      TimeUnit var1 = TimeUnit.SECONDS;
      SynchronousQueue var2 = new SynchronousQueue();
      byte[] var0 = q0.c.a;
      g = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, var1, var2, new q0.b("OkHttp ConnectionPool", true));
   }

   public h(TimeUnit var1) {
      this.a = 5;
      this.b = var1.toNanos(5L);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void a(m0 var1, IOException var2) {
      if (var1.b.type() != Type.DIRECT) {
         p0.a var3 = var1.a;
         var3.g.connectFailed(var3.a.n(), var1.b.address(), var2);
      }

      com.guard.wallet.http.h var6 = this.e;
      synchronized (var6){} // $VF: monitorenter 

      try {
         ((Set)var6.e).add(var1);
      } finally {
         // $VF: monitorexit
      }
   }

   public final int b(g var1, long var2) {
      ArrayList var5 = var1.p;
      int var4 = 0;

      while (var4 < var5.size()) {
         Reference var6 = (Reference)var5.get(var4);
         if (var6.get() != null) {
            var4++;
         } else {
            k var8 = (k)var6;
            StringBuilder var7 = new StringBuilder("A connection to ");
            var7.append(var1.c.a.a);
            var7.append(" was leaked. Did you forget to close a response body?");
            String var9 = var7.toString();
            w0.i.a.n(var8.a, var9);
            var5.remove(var4);
            var1.k = true;
            if (var5.isEmpty()) {
               var1.q = var2 - this.b;
               return 0;
            }
         }
      }

      return var5.size();
   }

   public final boolean c(p0.a var1, l var2, ArrayList var3, boolean var4) {
      Iterator var10 = this.d.iterator();

      g var9;
      int var15;
      do {
         boolean var6;
         do {
            boolean var8 = var10.hasNext();
            var6 = false;
            if (!var8) {
               return false;
            }

            var9 = (g)var10.next();
            if (!var4) {
               break;
            }

            if (var9.h != null) {
               var15 = (boolean)1;
            } else {
               var15 = (boolean)0;
            }
         } while (!var15);

         var15 = var6;
         if (var9.p.size() < var9.o) {
            if (var9.k) {
               var15 = var6;
            } else {
               q var13 = q.c;
               m0 var11 = var9.c;
               p0.a var12 = var11.a;
               var13.getClass();
               if (!var12.a(var1)) {
                  var15 = var6;
               } else {
                  u var19 = var1.a;
                  label84:
                  if (var19.d.equals(var11.a.a.d)) {
                     var15 = 1;
                  } else if (var9.h == null) {
                     var15 = var6;
                  } else {
                     var15 = var6;
                     if (var3 != null) {
                        int var7 = var3.size();
                        var15 = 0;

                        while (true) {
                           if (var15 >= var7) {
                              var17 = false;
                              break;
                           }

                           m0 var20 = (m0)var3.get(var15);
                           if (var20.b.type() == Type.DIRECT && var11.b.type() == Type.DIRECT && var11.c.equals(var20.c)) {
                              var17 = true;
                              break;
                           }

                           var15++;
                        }

                        if (!var17) {
                           var15 = var6;
                        } else {
                           z0.c var18 = z0.c.a;
                           if (var1.j != var18) {
                              var15 = var6;
                           } else if (!var9.j(var19)) {
                              var15 = var6;
                           } else {
                              try {
                                 var1.k.a(var19.d, var9.f.c);
                                 break label84;
                              } catch (SSLPeerUnverifiedException var14) {
                                 var15 = var6;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } while (!var15);

      if (var2.i == null) {
         var2.i = var9;
         var9.p.add(new k(var2, var2.f));
         return true;
      } else {
         throw new IllegalStateException();
      }
   }
}
