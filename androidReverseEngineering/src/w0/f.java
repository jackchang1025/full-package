package w0;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class f implements InvocationHandler {
   public final List a;
   public boolean b;
   public String c;

   public f(ArrayList var1) {
      this.a = var1;
   }

   @Override
   public final Object invoke(Object var1, Method var2, Object[] var3) {
      String var7 = var2.getName();
      Class var8 = var2.getReturnType();
      var1 = var3;
      if (var3 == null) {
         var1 = q0.c.b;
      }

      if (var7.equals("supports") && boolean.class == var8) {
         return Boolean.TRUE;
      } else if (var7.equals("unsupported") && void.class == var8) {
         this.b = true;
         return null;
      } else {
         boolean var6 = var7.equals("protocols");
         List var13 = this.a;
         if (var6 && ((Object[])var1).length == 0) {
            return var13;
         } else {
            if ((var7.equals("selectProtocol") || var7.equals("select")) && String.class == var8 && ((Object[])var1).length == 1) {
               var8 = (Class)((Object[])var1)[0];
               if (var8 instanceof List) {
                  var1 = (List)var8;
                  int var5 = var1.size();

                  for (int var4 = 0; var4 < var5; var4++) {
                     String var12 = (String)var1.get(var4);
                     if (var13.contains(var12)) {
                        this.c = var12;
                        return var12;
                     }
                  }

                  String var11 = (String)var13.get(0);
                  this.c = var11;
                  return var11;
               }
            }

            if ((var7.equals("protocolSelected") || var7.equals("selected")) && ((Object[])var1).length == 1) {
               this.c = (String)((Object[])var1)[0];
               return null;
            } else {
               return var2.invoke(this, (Object[])var1);
            }
         }
      }
   }
}
