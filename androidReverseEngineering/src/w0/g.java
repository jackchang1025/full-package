package w0;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLSocket;

public final class g extends i {
   public final Method c;
   public final Method d;
   public final Method e;
   public final Class f;
   public final Class g;

   public g(Method var1, Method var2, Method var3, Class var4, Class var5) {
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
   }

   @Override
   public final void a(SSLSocket var1) {
      try {
         this.e.invoke(null, var1);
         return;
      } catch (IllegalAccessException var2) {
         var4 = var2;
      } catch (InvocationTargetException var3) {
         var4 = var3;
      }

      throw new AssertionError("failed to remove ALPN", (Throwable)var4);
   }

   @Override
   public final void g(SSLSocket var1, String var2, List var3) {
      ArrayList var6 = i.b(var3);

      try {
         ClassLoader var5 = i.class.getClassLoader();
         Class var12 = this.f;
         Class var4 = this.g;
         f var10 = new f(var6);
         Object var11 = Proxy.newProxyInstance(var5, new Class[]{var12, var4}, var10);
         this.c.invoke(null, var1, var11);
         return;
      } catch (InvocationTargetException var7) {
         var9 = var7;
      } catch (IllegalAccessException var8) {
         var9 = var8;
      }

      throw new AssertionError("failed to set ALPN", (Throwable)var9);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final String j(SSLSocket var1) {
      InvocationTargetException var16;
      label66: {
         label58: {
            Method var4;
            try {
               var4 = this.d;
            } catch (InvocationTargetException var11) {
               var16 = var11;
               boolean var17 = false;
               break label66;
            } catch (IllegalAccessException var12) {
               var10000 = var12;
               boolean var10001 = false;
               break label58;
            }

            Object var3 = null;

            boolean var2;
            try {
               var13 = (f)Proxy.getInvocationHandler(var4.invoke(null, var1));
               var2 = var13.b;
            } catch (InvocationTargetException var9) {
               var16 = var9;
               boolean var19 = false;
               break label66;
            } catch (IllegalAccessException var10) {
               var10000 = var10;
               boolean var18 = false;
               break label58;
            }

            if (!var2) {
               try {
                  if (var13.c == null) {
                     i.a.m(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
                     return null;
                  }
               } catch (InvocationTargetException var7) {
                  var16 = var7;
                  boolean var21 = false;
                  break label66;
               } catch (IllegalAccessException var8) {
                  var10000 = var8;
                  boolean var20 = false;
                  break label58;
               }
            }

            if (var2) {
               return (String)var3;
            }

            try {
               return var13.c;
            } catch (InvocationTargetException var5) {
               var16 = var5;
               boolean var23 = false;
               break label66;
            } catch (IllegalAccessException var6) {
               var10000 = var6;
               boolean var22 = false;
            }
         }

         IllegalAccessException var14 = var10000;
         throw new AssertionError("failed to get ALPN selected protocol", var14);
      }

      InvocationTargetException var24 = var16;
      throw new AssertionError("failed to get ALPN selected protocol", var24);
   }
}
