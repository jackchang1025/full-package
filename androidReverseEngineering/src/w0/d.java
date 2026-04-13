package w0;

import a1.q;
import android.os.Build.VERSION;
import android.util.Log;
import f0.t;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;

public class d extends i {
   public final Class c;
   public final Method d;
   public final Method e;
   public final Method f;
   public final Method g;
   public final t h;

   public d(Class var1, Method var2, Method var3, Method var4, Method var5) {
      Method var6;
      Method var7;
      Method var8;
      try {
         Class var10 = Class.forName("dalvik.system.CloseGuard");
         var6 = var10.getMethod("get");
         var8 = var10.getMethod("open", String.class);
         var7 = var10.getMethod("warnIfOpen");
      } catch (Exception var9) {
         var6 = null;
         var7 = null;
         var8 = null;
      }

      this.h = new t(var6, var8, var7, 6);
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
   }

   public static boolean o(String var0, Class var1, Object var2) {
      boolean var3 = true;

      try {
         return (Boolean)var1.getMethod("isCleartextTrafficPermitted", String.class).invoke(var2, var0);
      } catch (NoSuchMethodException var6) {
         boolean var4;
         try {
            var4 = (Boolean)var1.getMethod("isCleartextTrafficPermitted").invoke(var2);
         } catch (NoSuchMethodException var5) {
            return var3;
         }

         return var4;
      }
   }

   @Override
   public final q c(X509TrustManager var1) {
      try {
         Class var2 = Class.forName("android.net.http.X509TrustManagerExtensions");
         return new b(
            var2.getConstructor(X509TrustManager.class).newInstance(var1),
            var2.getMethod("checkServerTrusted", X509Certificate[].class, String.class, String.class)
         );
      } catch (Exception var3) {
         return super.c(var1);
      }
   }

   @Override
   public final z0.d d(X509TrustManager var1) {
      try {
         Method var2 = var1.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
         var2.setAccessible(true);
         return new c(var1, var2);
      } catch (NoSuchMethodException var3) {
         return new z0.b(var1.getAcceptedIssuers());
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public void g(SSLSocket var1, String var2, List var3) {
      if (this.c.isInstance(var1)) {
         IllegalAccessException var9;
         label41: {
            label31: {
               if (var2 != null) {
                  try {
                     this.d.invoke(var1, Boolean.TRUE);
                     this.e.invoke(var1, var2);
                  } catch (IllegalAccessException var6) {
                     var9 = var6;
                     boolean var10 = false;
                     break label41;
                  } catch (InvocationTargetException var7) {
                     var10000 = var7;
                     boolean var10001 = false;
                     break label31;
                  }
               }

               try {
                  this.g.invoke(var1, i.e(var3));
                  return;
               } catch (IllegalAccessException var4) {
                  var9 = var4;
                  boolean var12 = false;
                  break label41;
               } catch (InvocationTargetException var5) {
                  var10000 = var5;
                  boolean var11 = false;
               }
            }

            InvocationTargetException var8 = var10000;
            throw new AssertionError(var8);
         }

         IllegalAccessException var13 = var9;
         throw new AssertionError(var13);
      }
   }

   @Override
   public final void h(Socket var1, InetSocketAddress var2, int var3) {
      try {
         var1.connect(var2, var3);
      } catch (AssertionError var4) {
         if (q0.c.n(var4)) {
            throw new IOException(var4);
         } else {
            throw var4;
         }
      } catch (ClassCastException var5) {
         if (VERSION.SDK_INT == 26) {
            throw new IOException("Exception in connect", var5);
         } else {
            throw var5;
         }
      }
   }

   @Override
   public final SSLContext i() {
      try {
         return SSLContext.getInstance("TLS");
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException("No TLS provider", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public String j(SSLSocket var1) {
      boolean var2 = this.c.isInstance(var1);
      Object var3 = null;
      if (!var2) {
         return null;
      } else {
         IllegalAccessException var11;
         label47: {
            label39: {
               byte[] var4;
               try {
                  var4 = (byte[])this.f.invoke(var1);
               } catch (IllegalAccessException var7) {
                  var11 = var7;
                  boolean var12 = false;
                  break label47;
               } catch (InvocationTargetException var8) {
                  var10000 = var8;
                  boolean var10001 = false;
                  break label39;
               }

               if (var4 == null) {
                  return (String)var3;
               }

               try {
                  return new String(var4, StandardCharsets.UTF_8);
               } catch (IllegalAccessException var5) {
                  var11 = var5;
                  boolean var14 = false;
                  break label47;
               } catch (InvocationTargetException var6) {
                  var10000 = var6;
                  boolean var13 = false;
               }
            }

            InvocationTargetException var10 = var10000;
            throw new AssertionError(var10);
         }

         IllegalAccessException var15 = var11;
         throw new AssertionError(var15);
      }
   }

   @Override
   public final Object k() {
      t var3 = this.h;
      Object var4 = var3.f;
      Method var5 = (Method)var4;
      Object var2 = null;
      Object var1 = var2;
      if (var5 != null) {
         try {
            var1 = ((Method)var4).invoke(null);
            ((Method)var3.e).invoke(var1, "response.body().close()");
         } catch (Exception var6) {
            var1 = var2;
         }
      }

      return var1;
   }

   @Override
   public final boolean l(String var1) {
      try {
         Class var3 = Class.forName("android.security.NetworkSecurityPolicy");
         return o(var1, var3, var3.getMethod("getInstance").invoke(null));
      } catch (NoSuchMethodException | ClassNotFoundException var4) {
         return true;
      } catch (IllegalAccessException var5) {
         var8 = var5;
      } catch (IllegalArgumentException var6) {
         var8 = var6;
      } catch (InvocationTargetException var7) {
         var8 = var7;
      }

      throw new AssertionError("unable to determine cleartext support", (Throwable)var8);
   }

   @Override
   public final void m(int var1, String var2, Throwable var3) {
      byte var4 = 5;
      if (var1 != 5) {
         var4 = 3;
      }

      String var8 = var2;
      if (var3 != null) {
         StringBuilder var10 = new StringBuilder();
         var10.append(var2);
         var10.append('\n');
         var10.append(Log.getStackTraceString(var3));
         var8 = var10.toString();
      }

      int var6 = var8.length();
      var1 = 0;

      while (var1 < var6) {
         int var5 = var8.indexOf(10, var1);
         if (var5 == -1) {
            var5 = var6;
         }

         while (true) {
            int var7 = Math.min(var5, var1 + 4000);
            Log.println(var4, "OkHttp", var8.substring(var1, var7));
            if (var7 >= var5) {
               var1 = var7 + 1;
               break;
            }

            var1 = var7;
         }
      }
   }

   @Override
   public final void n(Object var1, String var2) {
      boolean var3;
      t var5 = this.h;
      boolean var4 = false;
      var3 = var4;
      label18:
      if (var1 != null) {
         try {
            ((Method)var5.g).invoke(var1);
         } catch (Exception var6) {
            var3 = var4;
            break label18;
         }

         var3 = true;
      }

      if (!var3) {
         this.m(5, var2, null);
      }
   }
}
