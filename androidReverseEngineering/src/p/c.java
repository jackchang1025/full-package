package p;

import a1.q;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public final class c implements Callable {
   public final String a;
   public final String b;
   public final long c;
   public final long d;

   public c(String var1, long var2, long var4, String var6) {
      this.a = var1;
      this.b = var6;
      this.c = var2;
      this.d = var4;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final Object call() {
      String var10 = this.a;
      if (!q.B(var10)) {
         long var4 = this.d;
         long var6 = this.c;
         if (var4 >= var6) {
            String var8 = this.b;
            if (!q.B(var8)) {
               Exception var10000;
               label58: {
                  RandomAccessFile var11;
                  HttpURLConnection var20;
                  try {
                     URL var9 = new URL(var10);
                     var20 = (HttpURLConnection)var9.openConnection();
                     var20.setConnectTimeout(5000);
                     var20.setRequestMethod("GET");
                     StringBuilder var21 = new StringBuilder("bytes=");
                     var21.append(var6);
                     var21.append("-");
                     var21.append(var4);
                     var20.setRequestProperty("Range", var21.toString());
                     var20.setRequestProperty(
                        "User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"
                     );
                     var20.setRequestProperty("Connection", "Keep-Alive");
                     var22 = var20.getInputStream();
                     var11 = new RandomAccessFile(var8, "rw");
                     var11.seek(var6);
                     var17 = new byte[4096];
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var10001 = false;
                     break label58;
                  }

                  long var2 = 0L;

                  while (true) {
                     int var1;
                     try {
                        var1 = var22.read(var17);
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var23 = false;
                        break;
                     }

                     if (var1 == -1) {
                        try {
                           var22.close();
                           var11.close();
                           var20.disconnect();
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var25 = false;
                           break;
                        }

                        if (var2 != var4 - var6 + 1L) {
                           return Boolean.FALSE;
                        }

                        try {
                           return Boolean.TRUE;
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var26 = false;
                           break;
                        }
                     }

                     try {
                        var11.write(var17, 0, var1);
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var24 = false;
                        break;
                     }

                     var2 += (long)var1;
                  }
               }

               Exception var18 = var10000;
               q.s("SliceDownloadCallable", var18);
            }
         }
      }

      return Boolean.FALSE;
   }
}
