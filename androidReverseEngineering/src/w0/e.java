package w0;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.conscrypt.Conscrypt;

public final class e extends i {
   @Override
   public final void f(SSLSocketFactory var1) {
      if (Conscrypt.isConscrypt(var1)) {
         Conscrypt.setUseEngineSocket(var1, true);
      }
   }

   @Override
   public final void g(SSLSocket var1, String var2, List var3) {
      if (Conscrypt.isConscrypt(var1)) {
         if (var2 != null) {
            Conscrypt.setUseSessionTickets(var1, true);
            Conscrypt.setHostname(var1, var2);
         }

         Conscrypt.setApplicationProtocols(var1, i.b(var3).toArray(new String[0]));
      }
   }

   @Override
   public final SSLContext i() {
      try {
         return SSLContext.getInstance("TLSv1.3", Conscrypt.newProviderBuilder().provideTrustManager().build());
      } catch (NoSuchAlgorithmException var4) {
         try {
            return SSLContext.getInstance("TLS", Conscrypt.newProviderBuilder().provideTrustManager().build());
         } catch (NoSuchAlgorithmException var3) {
            throw new IllegalStateException("No TLS provider", var4);
         }
      }
   }

   @Override
   public final String j(SSLSocket var1) {
      return Conscrypt.isConscrypt(var1) ? Conscrypt.getApplicationProtocol(var1) : null;
   }
}
