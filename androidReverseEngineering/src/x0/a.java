package x0;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public final class a extends ProxySelector {
   @Override
   public final void connectFailed(URI var1, SocketAddress var2, IOException var3) {
   }

   @Override
   public final List select(URI var1) {
      if (var1 != null) {
         return Collections.singletonList(Proxy.NO_PROXY);
      } else {
         throw new IllegalArgumentException("uri must not be null");
      }
   }
}
