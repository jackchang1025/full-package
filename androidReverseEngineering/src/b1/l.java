package b1;

import android.os.Build;
import java.io.UnsupportedEncodingException;

public abstract class l {
   public static final int a = 0;

   static {
      StringBuilder var2 = new StringBuilder();
      String var0 = Build.FINGERPRINT;
      if (var0 != null) {
         var2.append(var0);
      }

      var0 = null;

      label23: {
         String var1;
         try {
            var1 = (String)Build.class.getField("SERIAL").get(null);
         } catch (Exception var4) {
            break label23;
         }

         var0 = var1;
      }

      if (var0 != null) {
         var2.append(var0);
      }

      try {
         var2.toString().getBytes("UTF-8");
      } catch (UnsupportedEncodingException var3) {
         throw new RuntimeException("UTF-8 encoding not supported");
      }
   }
}
