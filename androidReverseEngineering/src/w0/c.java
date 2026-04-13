package w0;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public final class c implements z0.d {
   public final X509TrustManager a;
   public final Method b;

   public c(X509TrustManager var1, Method var2) {
      this.b = var2;
      this.a = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final X509Certificate a(X509Certificate var1) {
      IllegalAccessException var10000;
      label45: {
         try {
            var6 = (TrustAnchor)this.b.invoke(this.a, var1);
         } catch (IllegalAccessException var4) {
            var10000 = var4;
            boolean var9 = false;
            break label45;
         } catch (InvocationTargetException var5) {
            boolean var10001 = false;
            return null;
         }

         if (var6 != null) {
            try {
               return var6.getTrustedCert();
            } catch (IllegalAccessException var2) {
               var10000 = var2;
               boolean var11 = false;
               break label45;
            } catch (InvocationTargetException var3) {
               boolean var10 = false;
            }
         }

         return null;
      }

      IllegalAccessException var8 = var10000;
      throw new AssertionError("unable to get issues and signature", var8);
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof c)) {
         return false;
      } else {
         var1 = var1;
         X509TrustManager var3 = var1.a;
         if (!this.a.equals(var3) || !this.b.equals(var1.b)) {
            var2 = false;
         }

         return var2;
      }
   }

   @Override
   public final int hashCode() {
      int var1 = this.a.hashCode();
      return this.b.hashCode() * 31 + var1;
   }
}
