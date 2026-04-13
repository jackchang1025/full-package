package z0;

import a1.q;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class a extends q {
   public final d o;

   public a(d var1) {
      this.o = var1;
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else {
         if (!(var1 instanceof a) || !((a)var1).o.equals(this.o)) {
            var2 = false;
         }

         return var2;
      }
   }

   @Override
   public final List f(String var1, List var2) {
      ArrayDeque var6 = new ArrayDeque(var2);
      ArrayList var12 = new ArrayList();
      var12.add((Certificate)var6.removeFirst());
      int var3 = 0;

      for (boolean var4 = false; var3 < 9; var3++) {
         X509Certificate var14 = (X509Certificate)var12.get(var12.size() - 1);
         X509Certificate var7 = this.o.a(var14);
         if (var7 != null) {
            if (var12.size() > 1 || !var14.equals(var7)) {
               var12.add(var7);
            }

            label48: {
               label47:
               if (var7.getIssuerDN().equals(var7.getSubjectDN())) {
                  try {
                     var7.verify(var7.getPublicKey());
                  } catch (GeneralSecurityException var10) {
                     break label47;
                  }

                  var4 = true;
                  break label48;
               }

               var4 = false;
            }

            if (var4) {
               return var12;
            }

            var4 = true;
         } else {
            Iterator var17 = var6.iterator();

            boolean var5;
            X509Certificate var8;
            do {
               if (!var17.hasNext()) {
                  if (var4) {
                     return var12;
                  }

                  StringBuilder var13 = new StringBuilder("Failed to find a trusted cert that signed ");
                  var13.append(var14);
                  throw new SSLPeerUnverifiedException(var13.toString());
               }

               var8 = (X509Certificate)var17.next();
               label63:
               if (var14.getIssuerDN().equals(var8.getSubjectDN())) {
                  try {
                     var14.verify(var8.getPublicKey());
                  } catch (GeneralSecurityException var11) {
                     break label63;
                  }

                  var5 = true;
                  continue;
               }

               var5 = false;
            } while (!var5);

            var17.remove();
            var12.add(var8);
         }
      }

      StringBuilder var15 = new StringBuilder("Certificate chain too long: ");
      var15.append(var12);
      throw new SSLPeerUnverifiedException(var15.toString());
   }

   @Override
   public final int hashCode() {
      return this.o.hashCode();
   }
}
