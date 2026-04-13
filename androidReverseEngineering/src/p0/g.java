package p0;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class g {
   public static final g c = new g(new LinkedHashSet(new ArrayList()), null);
   public final Set a;
   public final a1.q b;

   public g(Set var1, a1.q var2) {
      this.a = var1;
      this.b = var2;
   }

   public static String b(X509Certificate var0) {
      if (var0 instanceof X509Certificate) {
         StringBuilder var1 = new StringBuilder("sha256/");
         a1.h var3 = a1.h.g(var0.getPublicKey().getEncoded());

         try {
            var4 = a1.h.g(MessageDigest.getInstance("SHA-256").digest(var3.a));
         } catch (NoSuchAlgorithmException var2) {
            throw new AssertionError(var2);
         }

         var1.append(var4.a());
         return var1.toString();
      } else {
         throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
      }
   }

   public final void a(String var1, List var2) {
      List var7 = Collections.emptyList();
      Iterator var6 = this.a.iterator();
      if (var6.hasNext()) {
         a.a.w(var6.next());
         throw null;
      } else if (!var7.isEmpty()) {
         a1.q var8 = this.b;
         List var15 = var2;
         if (var8 != null) {
            var15 = var8.f(var1, var2);
         }

         int var5 = var15.size();
         byte var4 = 0;

         for (int var3 = 0; var3 < var5; var3++) {
            X509Certificate var9 = (X509Certificate)var15.get(var3);
            if (var7.size() > 0) {
               a.a.w(var7.get(0));
               throw null;
            }
         }

         StringBuilder var16 = new StringBuilder("Certificate pinning failure!\n  Peer certificate chain:");
         var5 = var15.size();

         for (int var11 = 0; var11 < var5; var11++) {
            X509Certificate var10 = (X509Certificate)var15.get(var11);
            var16.append("\n    ");
            var16.append(b(var10));
            var16.append(": ");
            var16.append(var10.getSubjectDN().getName());
         }

         var16.append("\n  Pinned certificates for ");
         var16.append(var1);
         var16.append(":");
         var5 = var7.size();

         for (int var12 = var4; var12 < var5; var12++) {
            a.a.w(var7.get(var12));
            var16.append("\n    null");
         }

         throw new SSLPeerUnverifiedException(var16.toString());
      }
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else {
         if (var1 instanceof g) {
            var1 = var1;
            a1.q var3 = var1.b;
            if (Objects.equals(this.b, var3) && this.a.equals(var1.a)) {
               return var2;
            }
         }

         return false;
      }
   }

   @Override
   public final int hashCode() {
      int var1 = Objects.hashCode(this.b);
      return this.a.hashCode() + var1 * 31;
   }
}
