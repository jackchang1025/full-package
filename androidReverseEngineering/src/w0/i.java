package w0;

import a1.q;
import android.os.Build.VERSION;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.AccessControlException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import org.conscrypt.Conscrypt;
import p0.b0;
import p0.c0;

public class i {
   public static final i a;
   public static final Logger b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   static {
      boolean var1 = "Dalvik".equals(System.getProperty("java.vm.name"));
      Object var3 = null;
      Object var4 = null;
      Object var2;
      if (var1) {
         label103: {
            label102:
            if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
               int var0;
               label99: {
                  try {
                     try {
                        var0 = VERSION.SDK_INT;
                        break label99;
                     } catch (NoClassDefFoundError var14) {
                     }
                  } catch (ReflectiveOperationException var15) {
                     boolean var10001 = false;
                     break label102;
                  }

                  var0 = 0;
               }

               if (var0 >= 29) {
                  try {
                     Class.forName("com.android.org.conscrypt.SSLParametersImpl");
                     var2 = new a();
                     break label103;
                  } catch (ReflectiveOperationException var13) {
                     boolean var29 = false;
                  }
               }
            }

            var2 = null;
         }

         if (var2 == null) {
            if (!"Dalvik".equals(System.getProperty("java.vm.name"))) {
               var2 = (Class)var4;
            } else {
               label134: {
                  try {
                     Class.forName("com.android.org.conscrypt.SSLParametersImpl");
                     var2 = Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
                  } catch (ClassNotFoundException var12) {
                     var2 = (Class)var4;
                     break label134;
                  }

                  try {
                     var2 = new d(
                        var2,
                        var2.getDeclaredMethod("setUseSessionTickets", boolean.class),
                        var2.getMethod("setHostname", String.class),
                        var2.getMethod("getAlpnSelectedProtocol"),
                        var2.getMethod("setAlpnProtocols", byte[].class)
                     );
                  } catch (NoSuchMethodException var11) {
                     StringBuilder var21 = new StringBuilder("Expected Android API level 21+ but was ");
                     var21.append(VERSION.SDK_INT);
                     throw new IllegalStateException(var21.toString());
                  }
               }
            }

            if (var2 == null) {
               throw new NullPointerException("No platform found on Android");
            }
         }
      } else {
         label133: {
            byte[] var22 = q0.c.a;

            label126: {
               label125: {
                  try {
                     var23 = System.getProperty("okhttp.platform");
                  } catch (AccessControlException var18) {
                     break label125;
                  }

                  if (var23 != null) {
                     break label126;
                  }
               }

               var23 = null;
            }

            if ("conscrypt".equals(var23)) {
               var1 = true;
            } else {
               var1 = "Conscrypt".equals(Security.getProviders()[0].getName());
            }

            if (var1) {
               label136: {
                  label114: {
                     try {
                        if (!Conscrypt.isAvailable()) {
                           break label114;
                        }
                     } catch (ClassNotFoundException var17) {
                        boolean var30 = false;
                        break label114;
                     }

                     try {
                        var2 = new e();
                        break label136;
                     } catch (ClassNotFoundException var16) {
                        boolean var31 = false;
                     }
                  }

                  var2 = null;
               }

               if (var2 != null) {
                  break label133;
               }
            }

            try {
               var4 = SSLParameters.class.getMethod("setApplicationProtocols", String[].class);
               Method var5 = SSLSocket.class.getMethod("getApplicationProtocol");
               var2 = new h((Method)var4, var5);
            } catch (NoSuchMethodException var10) {
               var2 = null;
            }

            if (var2 == null) {
               try {
                  var2 = Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
                  Class var6 = Class.forName("org.eclipse.jetty.alpn.ALPN$Provider", true, null);
                  Class var27 = Class.forName("org.eclipse.jetty.alpn.ALPN$ClientProvider", true, null);
                  var4 = Class.forName("org.eclipse.jetty.alpn.ALPN$ServerProvider", true, null);
                  Method var7 = var2.getMethod("put", SSLSocket.class, var6);
                  Method var28 = var2.getMethod("get", SSLSocket.class);
                  Method var8 = var2.getMethod("remove", SSLSocket.class);
                  var2 = new g(var7, var28, var8, var27, (Class)var4);
               } catch (NoSuchMethodException | ClassNotFoundException var9) {
                  var2 = (Class)var3;
               }

               if (var2 == null) {
                  var2 = new i();
               }
            }
         }
      }

      a = var2;
      b = Logger.getLogger(b0.class.getName());
   }

   public static ArrayList b(List var0) {
      ArrayList var4 = new ArrayList(var0.size());
      int var2 = var0.size();

      for (int var1 = 0; var1 < var2; var1++) {
         c0 var3 = (c0)var0.get(var1);
         if (var3 != c0.b) {
            var4.add(var3.a);
         }
      }

      return var4;
   }

   public static byte[] e(List var0) {
      a1.e var3 = new a1.e();
      int var2 = var0.size();

      for (int var1 = 0; var1 < var2; var1++) {
         c0 var4 = (c0)var0.get(var1);
         if (var4 != c0.b) {
            var3.J(var4.a.length());
            String var5 = var4.a;
            var3.O(var5, 0, var5.length());
         }
      }

      return var3.m();
   }

   public void a(SSLSocket var1) {
   }

   public q c(X509TrustManager var1) {
      return new z0.a(this.d(var1));
   }

   public z0.d d(X509TrustManager var1) {
      return new z0.b(var1.getAcceptedIssuers());
   }

   public void f(SSLSocketFactory var1) {
   }

   public void g(SSLSocket var1, String var2, List var3) {
   }

   public void h(Socket var1, InetSocketAddress var2, int var3) {
      var1.connect(var2, var3);
   }

   public SSLContext i() {
      try {
         return SSLContext.getInstance("TLS");
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException("No TLS provider", var2);
      }
   }

   public String j(SSLSocket var1) {
      return null;
   }

   public Object k() {
      return b.isLoggable(Level.FINE) ? new Throwable("response.body().close()") : null;
   }

   public boolean l(String var1) {
      return true;
   }

   public void m(int var1, String var2, Throwable var3) {
      Level var4;
      if (var1 == 5) {
         var4 = Level.WARNING;
      } else {
         var4 = Level.INFO;
      }

      b.log(var4, var2, var3);
   }

   public void n(Object var1, String var2) {
      String var3 = var2;
      if (var1 == null) {
         var3 = a.a.z(
            var2,
            " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);"
         );
      }

      this.m(5, var3, (Throwable)var1);
   }

   @Override
   public final String toString() {
      return this.getClass().getSimpleName();
   }
}
