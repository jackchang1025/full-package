package p;

import a1.q;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class b {
   public static final ConcurrentHashMap a = new ConcurrentHashMap();

   public static boolean a(String var0, String var1) {
      boolean var14 = q.B(var0);
      boolean var13 = false;
      if (!var14) {
         ConcurrentHashMap var16 = a;
         if (!var16.containsKey(var0) && !q.B(var1)) {
            long var5;
            label81: {
               label80:
               if (!q.B(var0)) {
                  try {
                     URL var15 = new URL(var0);
                     HttpURLConnection var28 = (HttpURLConnection)var15.openConnection();
                     var28.setConnectTimeout(5000);
                     var28.setRequestMethod("HEAD");
                     var5 = var28.getHeaderFieldLong("Content-Length", 0L);
                     var14 = Objects.equals(var28.getHeaderField("Accept-Ranges"), "bytes");
                     var28.disconnect();
                  } catch (Exception var19) {
                     q.s("DownloadUtils", var19);
                     var16.remove(var0);
                     break label80;
                  }

                  if (var5 > 0L && var14) {
                     break label81;
                  }
               }

               var5 = 0L;
            }

            if (var5 <= 0L) {
               return b(var0, var1);
            }

            q.n(var1);
            long var7 = var5 / 2097152L;
            long var3 = var7;
            if (var5 % 2097152L > 0L) {
               var3 = var7 + 1L;
            }

            ExecutorService var17 = Executors.newFixedThreadPool((int)var3);
            LinkedList var29 = new LinkedList();
            int var2 = 0;

            while (true) {
               var7 = (long)var2;
               if (var7 >= var3) {
                  var7 = 0L;

                  while (!var29.isEmpty()) {
                     ListIterator var20 = var29.listIterator();
                     var5 = var7;

                     while (true) {
                        var7 = var5;
                        if (!var20.hasNext()) {
                           break;
                        }

                        Future var30 = (Future)var20.next();
                        if (var30.isDone()) {
                           label61: {
                              try {
                                 var14 = (Boolean)var30.get();
                              } catch (Exception var18) {
                                 q.s("DownloadUtils", var18);
                                 var7 = var5;
                                 break label61;
                              }

                              var7 = var5;
                              if (var14) {
                                 var7 = var5 + 1L;
                              }
                           }

                           var20.remove();
                           var5 = var7;
                        }
                     }
                  }

                  var16.remove(var0);
                  if (var7 == var3) {
                     var13 = true;
                  }

                  return var13;
               }

               long var11 = var7 * 2097152L;
               long var9 = var11 + 2097152L - 1L;
               var7 = var5 - 1L;
               if (var9 <= var7) {
                  var7 = var9;
               }

               var29.add(var17.submit(new c(var0, var11, var7, var1)));
               var2++;
            }
         }
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean b(String var0, String var1) {
      if (!q.B(var0)) {
         ConcurrentHashMap var4 = a;
         if (!var4.containsKey(var0) && !q.B(var1)) {
            Exception var10000;
            label79: {
               InputStream var5;
               label72: {
                  FileOutputStream var15;
                  label80: {
                     try {
                        var4.put(var0, System.currentTimeMillis());
                        URL var3 = new URL(var0);
                        var5 = var3.openStream();
                        if (q.w(var1)) {
                           var15 = new FileOutputStream(var1, false);
                           break label80;
                        }
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var10001 = false;
                        break label79;
                     }

                     try {
                        if (q.l(var1)) {
                           var13 = new FileOutputStream(var1, true);
                           break label72;
                        }
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var17 = false;
                        break label79;
                     }

                     var13 = null;
                     break label72;
                  }

                  var13 = var15;
               }

               if (var13 != null) {
                  byte[] var16;
                  try {
                     var16 = new byte[1024];
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var18 = false;
                     break label79;
                  }

                  while (true) {
                     int var2;
                     try {
                        var2 = var5.read(var16);
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var19 = false;
                        break label79;
                     }

                     if (var2 <= 0) {
                        try {
                           var13.flush();
                           var13.close();
                           break;
                        } catch (Exception var7) {
                           var10000 = var7;
                           boolean var21 = false;
                           break label79;
                        }
                     }

                     try {
                        var13.write(var16, 0, var2);
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var20 = false;
                        break label79;
                     }
                  }
               }

               try {
                  var4.remove(var0);
                  return true;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var22 = false;
               }
            }

            Exception var14 = var10000;
            q.s("DownloadUtils", var14);
            var4.remove(var0);
         }
      }

      return false;
   }
}
