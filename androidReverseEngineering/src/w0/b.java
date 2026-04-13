package w0;

import a1.q;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class b extends q {
   public final Object o;
   public final Method p;

   public b(Object var1, Method var2) {
      this.o = var1;
      this.p = var2;
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof b;
   }

   @Override
   public final List f(String var1, List var2) {
      try {
         X509Certificate[] var6 = var2.toArray(new X509Certificate[var2.size()]);
         return (List)this.p.invoke(this.o, var6, "RSA", var1);
      } catch (InvocationTargetException var3) {
         SSLPeerUnverifiedException var5 = new SSLPeerUnverifiedException(var3.getMessage());
         var5.initCause(var3);
         throw var5;
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      }
   }

   @Override
   public final int hashCode() {
      return 0;
   }
}
