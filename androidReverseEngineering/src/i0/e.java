package i0;

import android.net.Uri;
import android.text.TextUtils;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class e extends LinkedHashMap implements Iterable {
   public static final b0.b a = new b0.b(25);
   public static final b0.b b = new b0.b(26);

   public e() {
   }

   public e(e var1) {
      this.putAll(var1);
   }

   public static e c(String var0, String var1, boolean var2, b0.b var3) {
      e var8 = new e();
      if (var0 != null) {
         String[] var9 = var0.split(var1);
         int var5 = var9.length;

         for (int var4 = 0; var4 < var5; var4++) {
            String[] var10 = var9[var4].split("=", 2);
            String var7 = var10[0].trim();
            if (!TextUtils.isEmpty(var7)) {
               if (var10.length > 1) {
                  var1 = var10[1];
               } else {
                  var1 = null;
               }

               var0 = var1;
               if (var1 != null) {
                  var0 = var1;
                  if (var2) {
                     var0 = var1;
                     if (var1.endsWith("\"")) {
                        var0 = var1;
                        if (var1.startsWith("\"")) {
                           var0 = var1.substring(1, var1.length() - 1);
                        }
                     }
                  }
               }

               var1 = var0;
               String var6 = var7;
               if (var0 != null) {
                  var1 = var0;
                  var6 = var7;
                  if (var3 != null) {
                     switch (var3.d) {
                        case 25:
                           var6 = Uri.decode(var7);
                           break;
                        default:
                           var6 = URLDecoder.decode(var7);
                     }

                     switch (var3.d) {
                        case 25:
                           var1 = Uri.decode(var0);
                           break;
                        default:
                           var1 = URLDecoder.decode(var0);
                     }
                  }
               }

               List var15 = (List)var8.get(var6);
               List var12 = var15;
               if (var15 == null) {
                  var12 = var8.b();
                  var8.put(var6, var12);
               }

               var12.add(var1);
            }
         }
      }

      return var8;
   }

   public final String a(String var1) {
      List var2 = (List)this.get(var1);
      return var2 != null && var2.size() != 0 ? (String)var2.get(0) : null;
   }

   public List b() {
      return new ArrayList();
   }

   @Override
   public final Iterator iterator() {
      ArrayList var1 = new ArrayList();

      for (String var3 : this.keySet()) {
         Iterator var4 = ((List)this.get(var3)).iterator();

         while (var4.hasNext()) {
            var1.add(new a(var3, (String)var4.next()));
         }
      }

      return var1.iterator();
   }
}
