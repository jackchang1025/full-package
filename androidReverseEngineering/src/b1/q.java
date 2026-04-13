package b1;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509ExtendedKeyManager;

public final class q extends X509ExtendedKeyManager {
   public final k a;

   public q(k var1) {
      this.a = var1;
   }

   @Override
   public final String chooseClientAlias(String[] var1, Principal[] var2, Socket var3) {
      int var5 = var1.length;

      for (int var4 = 0; var4 < var5; var4++) {
         if (var1[var4].equals("RSA")) {
            return "key";
         }
      }

      return null;
   }

   @Override
   public final String chooseServerAlias(String var1, Principal[] var2, Socket var3) {
      return null;
   }

   @Override
   public final X509Certificate[] getCertificateChain(String var1) {
      return "key".equals(var1) ? new X509Certificate[]{(X509Certificate)this.a.b} : null;
   }

   @Override
   public final String[] getClientAliases(String var1, Principal[] var2) {
      return null;
   }

   @Override
   public final PrivateKey getPrivateKey(String var1) {
      return "key".equals(var1) ? this.a.a : null;
   }

   @Override
   public final String[] getServerAliases(String var1, Principal[] var2) {
      return null;
   }
}
