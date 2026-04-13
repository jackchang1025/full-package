package w0;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

public final class h extends i {
   public final Method c;
   public final Method d;

   public h(Method var1, Method var2) {
      this.c = var1;
      this.d = var2;
   }

   @Override
   public final void g(SSLSocket var1, String var2, List var3) {
      try {
         SSLParameters var7 = var1.getSSLParameters();
         ArrayList var8 = i.b(var3);
         this.c.invoke(var7, (Object)var8.toArray(new String[var8.size()]));
         var1.setSSLParameters(var7);
         return;
      } catch (IllegalAccessException var4) {
         var6 = var4;
      } catch (InvocationTargetException var5) {
         var6 = var5;
      }

      throw new AssertionError("failed to set SSL parameters", (Throwable)var6);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final String j(SSLSocket var1) {
      IllegalAccessException var10000;
      label40: {
         label46: {
            try {
               var7 = (String)this.d.invoke(var1);
            } catch (InvocationTargetException var5) {
               var10 = var5;
               boolean var11 = false;
               break label46;
            } catch (IllegalAccessException var6) {
               var10000 = var6;
               boolean var10001 = false;
               break label40;
            }

            if (var7 == null) {
               return null;
            }

            boolean var2;
            try {
               var2 = var7.equals("");
            } catch (InvocationTargetException var3) {
               var10 = var3;
               boolean var13 = false;
               break label46;
            } catch (IllegalAccessException var4) {
               var10000 = var4;
               boolean var12 = false;
               break label40;
            }

            if (!var2) {
               return var7;
            }

            return null;
         }

         InvocationTargetException var9 = var10;
         if (var9.getCause() instanceof UnsupportedOperationException) {
            return null;
         }

         throw new AssertionError("failed to get ALPN selected protocol", var9);
      }

      IllegalAccessException var8 = var10000;
      throw new AssertionError("failed to get ALPN selected protocol", var8);
   }
}
