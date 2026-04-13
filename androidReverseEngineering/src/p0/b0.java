package p0;

import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class b0 implements Cloneable {
   public static final List A = q0.c.l(p0.k.e, p0.k.f);
   public static final List z = q0.c.l(c0.e, c0.c);
   public final o a;
   public final List b;
   public final List c;
   public final List d;
   public final List e;
   public final f0.l f;
   public final ProxySelector g;
   public final n h;
   public final SocketFactory i;
   public final SSLSocketFactory j;
   public final a1.q k;
   public final z0.c l;
   public final g m;
   public final m0.b n;
   public final m0.b o;
   public final com.guard.wallet.http.h p;
   public final m0.b q;
   public final boolean r;
   public final boolean s;
   public final boolean t;
   public final int u;
   public final int v;
   public final int w;
   public final int x;
   public final int y;

   static {
      p0.q.c = new q();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public b0(a0 var1) {
      this.a = var1.a;
      this.b = var1.b;
      List var3 = var1.c;
      this.c = var3;
      this.d = q0.c.k(var1.d);
      this.e = q0.c.k(var1.e);
      this.f = var1.f;
      this.g = var1.g;
      this.h = var1.h;
      this.i = var1.i;
      Iterator var4 = var3.iterator();

      label77:
      while (true) {
         boolean var2;
         for (var2 = false; var4.hasNext(); var2 = true) {
            k var14 = (k)var4.next();
            if (!var2 && !var14.a) {
               continue label77;
            }
         }

         GeneralSecurityException var10000;
         label84: {
            if (!var2) {
               this.j = null;
               this.k = null;
            } else {
               label64: {
                  label82: {
                     try {
                        TrustManagerFactory var15 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                        var15.init(null);
                        var16 = var15.getTrustManagers();
                        if (var16.length != 1) {
                           break label82;
                        }
                     } catch (GeneralSecurityException var9) {
                        var10000 = var9;
                        boolean var10001 = false;
                        break label84;
                     }

                     TrustManager var20 = var16[0];

                     try {
                        if (var20 instanceof X509TrustManager) {
                           var21 = (X509TrustManager)var20;
                           break label64;
                        }
                     } catch (GeneralSecurityException var8) {
                        var10000 = var8;
                        boolean var25 = false;
                        break label84;
                     }
                  }

                  try {
                     StringBuilder var11 = new StringBuilder("Unexpected default trust managers:");
                     var11.append(Arrays.toString((Object[])var16));
                     IllegalStateException var22 = new IllegalStateException(var11.toString());
                     throw var22;
                  } catch (GeneralSecurityException var7) {
                     var10000 = var7;
                     boolean var26 = false;
                     break label84;
                  }
               }

               SSLSocketFactory var24;
               try {
                  var17 = w0.i.a;
                  SSLContext var5 = var17.i();
                  var5.init(null, new TrustManager[]{var21}, null);
                  var24 = var5.getSocketFactory();
               } catch (GeneralSecurityException var6) {
                  throw new AssertionError("No System TLS", var6);
               }

               this.j = var24;
               this.k = var17.c(var21);
            }

            SSLSocketFactory var18 = this.j;
            if (var18 != null) {
               w0.i.a.f(var18);
            }

            this.l = var1.j;
            a1.q var23 = this.k;
            g var19 = var1.k;
            if (!Objects.equals(var19.b, var23)) {
               var19 = new g(var19.a, var23);
            }

            this.m = var19;
            this.n = var1.l;
            this.o = var1.m;
            this.p = var1.n;
            this.q = var1.o;
            this.r = var1.p;
            this.s = var1.q;
            this.t = var1.r;
            this.u = var1.s;
            this.v = var1.t;
            this.w = var1.u;
            this.x = var1.v;
            this.y = var1.w;
            if (!this.d.contains(null)) {
               if (!this.e.contains(null)) {
                  return;
               }

               StringBuilder var13 = new StringBuilder("Null network interceptor: ");
               var13.append(this.e);
               throw new IllegalStateException(var13.toString());
            }

            StringBuilder var12 = new StringBuilder("Null interceptor: ");
            var12.append(this.d);
            throw new IllegalStateException(var12.toString());
         }

         GeneralSecurityException var10 = var10000;
         throw new AssertionError("No System TLS", var10);
      }
   }
}
