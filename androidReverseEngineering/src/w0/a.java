package w0;

import java.io.IOException;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

public final class a extends d {
   public a() {
      super(null, null, null, null, null);
   }

   @Override
   public final void g(SSLSocket var1, String var2, List var3) {
      try {
         if (a0.d.t(var1)) {
            a0.d.r(var1);
         }

         SSLParameters var5 = var1.getSSLParameters();
         a0.d.q(var5, i.b(var3).toArray(new String[0]));
         var1.setSSLParameters(var5);
      } catch (IllegalArgumentException var4) {
         throw new IOException("Android internal error", var4);
      }
   }

   @Override
   public final String j(SSLSocket var1) {
      String var2 = a0.d.n(var1);
      return var2 != null && !var2.isEmpty() ? var2 : null;
   }
}
