package c1;

import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager.ResolveListener;
import com.guard.wallet.utils.g;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Iterator;

public final class c implements ResolveListener {
   public final d a;

   public c(d var1) {
      this.a = var1;
   }

   public final void onResolveFailed(NsdServiceInfo var1, int var2) {
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onServiceResolved(NsdServiceInfo var1) {
      d var5 = this.a;
      if (var5.g) {
         SocketException var10000;
         label68: {
            Iterator var6;
            try {
               var6 = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            } catch (SocketException var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label68;
            }

            label65:
            while (true) {
               Iterator var4;
               try {
                  if (!var6.hasNext()) {
                     return;
                  }

                  var4 = Collections.list(((NetworkInterface)var6.next()).getInetAddresses()).iterator();
               } catch (SocketException var11) {
                  var10000 = var11;
                  boolean var17 = false;
                  break;
               }

               while (true) {
                  int var3;
                  while (true) {
                     String var7;
                     try {
                        if (!var4.hasNext()) {
                           continue label65;
                        }

                        var7 = ((InetAddress)var4.next()).getHostAddress();
                     } catch (SocketException var10) {
                        var10000 = var10;
                        boolean var18 = false;
                        break label65;
                     }

                     if (var7 != null) {
                        try {
                           if (var7.equals(var1.getHost().getHostAddress())) {
                              var3 = var1.getPort();
                              break;
                           }
                        } catch (SocketException var12) {
                           var10000 = var12;
                           boolean var19 = false;
                           break label65;
                        }
                     }
                  }

                  boolean var2 = true;

                  label60: {
                     try {
                        ServerSocket var16 = new ServerSocket();
                        InetSocketAddress var8 = new InetSocketAddress(g.c0(var5.a), var3);
                        var16.bind(var8, 1);
                     } catch (IOException var13) {
                        break label60;
                     }

                     var2 = false;
                  }

                  if (var2) {
                     try {
                        var5.h = var1.getServiceName();
                        var5.c.a(var1.getHost(), var1.getPort());
                     } catch (SocketException var9) {
                        var10000 = var9;
                        boolean var20 = false;
                        break label65;
                     }
                  }
               }
            }
         }

         SocketException var15 = var10000;
         var15.printStackTrace();
      }
   }
}
