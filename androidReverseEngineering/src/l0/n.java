package l0;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;

public abstract class n implements p {
   public final ArrayList a = new ArrayList();

   static {
      Hashtable var0 = new Hashtable();
      var0.put("js", "application/javascript");
      var0.put("json", "application/json");
      var0.put("png", "image/png");
      var0.put("jpg", "image/jpeg");
      var0.put("jpeg", "image/jpeg");
      var0.put("html", "text/html");
      var0.put("css", "text/css");
      var0.put("mp4", "video/mp4");
      var0.put("mov", "video/quicktime");
      var0.put("wmv", "video/x-ms-wmv");
      var0.put("txt", "text/plain");
      new Hashtable();
   }

   public final void a(String param1, o param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: new l0/l
      // 03: dup
      // 04: invokespecial l0/l.<init> ()V
      // 07: astore 3
      // 08: aload 3
      // 09: ldc "^[\\d\\D]*"
      // 0b: invokestatic java/util/regex/Pattern.compile (Ljava/lang/String;)Ljava/util/regex/Pattern;
      // 0e: putfield l0/l.b Ljava/util/regex/Pattern;
      // 11: aload 3
      // 12: aload 2
      // 13: putfield l0/l.c Ll0/o;
      // 16: aload 3
      // 17: aload 1
      // 18: putfield l0/l.a Ljava/lang/String;
      // 1b: aload 0
      // 1c: getfield l0/n.a Ljava/util/ArrayList;
      // 1f: astore 1
      // 20: aload 1
      // 21: monitorenter
      // 22: aload 0
      // 23: getfield l0/n.a Ljava/util/ArrayList;
      // 26: aload 3
      // 27: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 2a: pop
      // 2b: aload 1
      // 2c: monitorexit
      // 2d: return
      // 2e: astore 2
      // 2f: aload 1
      // 30: monitorexit
      // 31: aload 2
      // 32: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final m b(String var1, String var2) {
      ArrayList var3 = this.a;
      synchronized (var3){} // $VF: monitorenter 

      Throwable var10000;
      label339: {
         Iterator var6;
         try {
            var6 = this.a.iterator();
         } catch (Throwable var46) {
            var10000 = var46;
            boolean var10001 = false;
            break label339;
         }

         label338:
         while (true) {
            while (true) {
               l var5;
               try {
                  if (!var6.hasNext()) {
                     break;
                  }

                  var5 = (l)var6.next();
                  if (!TextUtils.equals(var1, var5.a) && var5.a != null) {
                     continue;
                  }
               } catch (Throwable var48) {
                  var10000 = var48;
                  boolean var54 = false;
                  break label338;
               }

               Matcher var4;
               try {
                  var4 = var5.b.matcher(var2);
                  if (!var4.matches()) {
                     continue;
                  }

                  var53 = var5.c;
                  if (var53 instanceof p) {
                     var2 = var4.group(1);
                     m var49 = ((n)((p)var5.c)).b(var1, var2);
                     // $VF: monitorexit
                     return var49;
                  }
               } catch (Throwable var47) {
                  var10000 = var47;
                  boolean var55 = false;
                  break label338;
               }

               try {
                  m var52 = new m(var1, var2, var4, var53);
                  // $VF: monitorexit
                  return var52;
               } catch (Throwable var44) {
                  var10000 = var44;
                  boolean var56 = false;
                  break label338;
               }
            }

            try {
               // $VF: monitorexit
               return null;
            } catch (Throwable var45) {
               var10000 = var45;
               boolean var57 = false;
               break;
            }
         }
      }

      while (true) {
         Throwable var50 = var10000;

         try {
            // $VF: monitorexit
            throw var50;
         } catch (Throwable var43) {
            var10000 = var43;
            boolean var58 = false;
            continue;
         }
      }
   }
}
