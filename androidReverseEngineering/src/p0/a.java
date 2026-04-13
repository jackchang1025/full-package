package p0;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.Objects;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class a {
   public final u a;
   public final p b;
   public final SocketFactory c;
   public final b d;
   public final List e;
   public final List f;
   public final ProxySelector g;
   public final Proxy h;
   public final SSLSocketFactory i;
   public final HostnameVerifier j;
   public final g k;

   public a(
      String var1, int var2, m0.b var3, SocketFactory var4, SSLSocketFactory var5, z0.c var6, g var7, m0.b var8, List var9, List var10, ProxySelector var11
   ) {
      t var14 = new t();
      String var13 = "https";
      String var12;
      if (var5 != null) {
         var12 = "https";
      } else {
         var12 = "http";
      }

      if (var12.equalsIgnoreCase("http")) {
         var12 = "http";
      } else {
         if (!var12.equalsIgnoreCase("https")) {
            throw new IllegalArgumentException("unexpected scheme: ".concat(var12));
         }

         var12 = var13;
      }

      var14.e = var12;
      if (var1 != null) {
         var12 = q0.c.a(u.i(var1, 0, var1.length(), false));
         if (var12 != null) {
            var14.h = var12;
            if (var2 > 0 && var2 <= 65535) {
               var14.c = var2;
               this.a = var14.a();
               if (var3 != null) {
                  this.b = var3;
                  if (var4 != null) {
                     this.c = var4;
                     if (var8 != null) {
                        this.d = var8;
                        if (var9 != null) {
                           this.e = q0.c.k(var9);
                           if (var10 != null) {
                              this.f = q0.c.k(var10);
                              if (var11 != null) {
                                 this.g = var11;
                                 this.h = null;
                                 this.i = var5;
                                 this.j = var6;
                                 this.k = var7;
                              } else {
                                 throw new NullPointerException("proxySelector == null");
                              }
                           } else {
                              throw new NullPointerException("connectionSpecs == null");
                           }
                        } else {
                           throw new NullPointerException("protocols == null");
                        }
                     } else {
                        throw new NullPointerException("proxyAuthenticator == null");
                     }
                  } else {
                     throw new NullPointerException("socketFactory == null");
                  }
               } else {
                  throw new NullPointerException("dns == null");
               }
            } else {
               throw new IllegalArgumentException(a.a.g("unexpected port: ", var2));
            }
         } else {
            throw new IllegalArgumentException("unexpected host: ".concat(var1));
         }
      } else {
         throw new NullPointerException("host == null");
      }
   }

   public final boolean a(a var1) {
      p var3 = var1.b;
      boolean var2;
      if (this.b.equals(var3)
         && this.d.equals(var1.d)
         && this.e.equals(var1.e)
         && this.f.equals(var1.f)
         && this.g.equals(var1.g)
         && Objects.equals(this.h, var1.h)
         && Objects.equals(this.i, var1.i)
         && Objects.equals(this.j, var1.j)
         && Objects.equals(this.k, var1.k)
         && this.a.e == var1.a.e) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public final boolean equals(Object var1) {
      if (var1 instanceof a) {
         a var3 = (a)var1;
         var1 = var3.a;
         if (this.a.equals(var1) && this.a(var3)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public final int hashCode() {
      int var6 = this.a.hashCode();
      int var7 = this.b.hashCode();
      int var3 = this.d.hashCode();
      int var8 = this.e.hashCode();
      int var1 = this.f.hashCode();
      int var4 = this.g.hashCode();
      int var2 = Objects.hashCode(this.h);
      int var9 = Objects.hashCode(this.i);
      int var5 = Objects.hashCode(this.j);
      return Objects.hashCode(this.k)
         + (var5 + (var9 + (var2 + (var4 + (var1 + (var8 + (var3 + (var7 + (var6 + 527) * 31) * 31) * 31) * 31) * 31) * 31) * 31) * 31) * 31;
   }

   @Override
   public final String toString() {
      StringBuilder var2 = new StringBuilder("Address{");
      u var1 = this.a;
      var2.append(var1.d);
      var2.append(":");
      var2.append(var1.e);
      var1 = this.h;
      if (var1 != null) {
         var2.append(", proxy=");
      } else {
         var2.append(", proxySelector=");
         var1 = this.g;
      }

      var2.append(var1);
      var2.append("}");
      return var2.toString();
   }
}
