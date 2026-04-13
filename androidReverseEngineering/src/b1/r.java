package b1;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public final class r implements X509TrustManager {
   @Override
   public final void checkClientTrusted(X509Certificate[] var1, String var2) {
   }

   @Override
   public final void checkServerTrusted(X509Certificate[] var1, String var2) {
   }

   @Override
   public final X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
   }
}
