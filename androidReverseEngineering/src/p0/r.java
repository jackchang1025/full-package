package p0;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public final class r {
   public final n0 a;
   public final i b;
   public final List c;
   public final List d;

   public r(n0 var1, i var2, List var3, List var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public static r a(SSLSession var0) {
      String var1 = var0.getCipherSuite();
      if (var1 != null) {
         if (!"SSL_NULL_WITH_NULL_NULL".equals(var1)) {
            i var2 = i.a(var1);
            var1 = var0.getProtocol();
            if (var1 != null) {
               if (!"NONE".equals(var1)) {
                  n0 var3 = n0.a(var1);

                  try {
                     var8 = var0.getPeerCertificates();
                  } catch (SSLPeerUnverifiedException var4) {
                     var8 = null;
                  }

                  List var9;
                  if (var8 != null) {
                     var9 = q0.c.l(var8);
                  } else {
                     var9 = Collections.emptyList();
                  }

                  Certificate[] var5 = var0.getLocalCertificates();
                  List var6;
                  if (var5 != null) {
                     var6 = q0.c.l(var5);
                  } else {
                     var6 = Collections.emptyList();
                  }

                  return new r(var3, var2, var9, var6);
               } else {
                  throw new IOException("tlsVersion == NONE");
               }
            } else {
               throw new IllegalStateException("tlsVersion == null");
            }
         } else {
            throw new IOException("cipherSuite == SSL_NULL_WITH_NULL_NULL");
         }
      } else {
         throw new IllegalStateException("cipherSuite == null");
      }
   }

   public static ArrayList b(List var0) {
      ArrayList var1 = new ArrayList();

      for (Certificate var3 : var0) {
         String var4;
         if (var3 instanceof X509Certificate) {
            var4 = String.valueOf(((X509Certificate)var3).getSubjectDN());
         } else {
            var4 = var3.getType();
         }

         var1.add(var4);
      }

      return var1;
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = var1 instanceof r;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         var1 = var1;
         n0 var4 = var1.a;
         var2 = var3;
         if (this.a.equals(var4)) {
            var2 = var3;
            if (this.b.equals(var1.b)) {
               var2 = var3;
               if (this.c.equals(var1.c)) {
                  var2 = var3;
                  if (this.d.equals(var1.d)) {
                     var2 = true;
                  }
               }
            }
         }

         return var2;
      }
   }

   @Override
   public final int hashCode() {
      int var2 = this.a.hashCode();
      int var1 = this.b.hashCode();
      int var3 = this.c.hashCode();
      return this.d.hashCode() + (var3 + (var1 + (var2 + 527) * 31) * 31) * 31;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("Handshake{tlsVersion=");
      var1.append(this.a);
      var1.append(" cipherSuite=");
      var1.append(this.b);
      var1.append(" peerCertificates=");
      var1.append(b(this.c));
      var1.append(" localCertificates=");
      var1.append(b(this.d));
      var1.append('}');
      return var1.toString();
   }
}
