package z0;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public final class b implements d {
   public final LinkedHashMap a = new LinkedHashMap();

   public b(X509Certificate... var1) {
      for (X509Certificate var7 : var1) {
         X500Principal var6 = var7.getSubjectX500Principal();
         Set var5 = (Set)this.a.get(var6);
         Object var4 = var5;
         if (var5 == null) {
            var4 = new LinkedHashSet(1);
            this.a.put(var6, var4);
         }

         var4.add(var7);
      }
   }

   @Override
   public final X509Certificate a(X509Certificate var1) {
      X500Principal var2 = var1.getIssuerX500Principal();
      Set var6 = (Set)this.a.get(var2);
      if (var6 == null) {
         return null;
      } else {
         for (X509Certificate var4 : var6) {
            PublicKey var3 = var4.getPublicKey();

            try {
               var1.verify(var3);
               return var4;
            } catch (Exception var5) {
            }
         }

         return null;
      }
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else {
         if (!(var1 instanceof b) || !((b)var1).a.equals(this.a)) {
            var2 = false;
         }

         return var2;
      }
   }

   @Override
   public final int hashCode() {
      return this.a.hashCode();
   }
}
