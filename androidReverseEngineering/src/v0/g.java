package v0;

import java.io.IOException;

public abstract class g {
   public static final a1.h a = a1.h.d("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
   public static final String[] b = new String[]{
      "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"
   };
   public static final String[] c = new String[64];
   public static final String[] d = new String[256];

   static {
      byte var2 = 0;
      int var0 = 0;

      while (true) {
         String[] var5 = d;
         if (var0 >= var5.length) {
            var5 = c;
            var5[0] = "";
            var5[1] = "END_STREAM";
            var5[8] = "PADDED";
            var5[1 | 8] = a.a.n(new StringBuilder(), var5[1], "|PADDED");
            var5[4] = "END_HEADERS";
            var5[32] = "PRIORITY";
            var5[36] = "END_HEADERS|PRIORITY";
            var0 = 0;

            while (true) {
               int var1 = var2;
               if (var0 >= 3) {
                  while (true) {
                     var5 = c;
                     if (var1 >= var5.length) {
                        return;
                     }

                     if (var5[var1] == null) {
                        var5[var1] = d[var1];
                     }

                     var1++;
                  }
               }

               int var3 = new int[]{4, 32, 36}[var0];
               var1 = new int[]{1}[0];
               var5 = c;
               int var4 = var1 | var3;
               StringBuilder var6 = new StringBuilder();
               var6.append(var5[var1]);
               var6.append('|');
               var6.append(var5[var3]);
               var5[var4] = var6.toString();
               var6 = new StringBuilder();
               var6.append(var5[var1]);
               var6.append('|');
               var5[var4 | 8] = a.a.n(var6, var5[var3], "|PADDED");
               var0++;
            }
         }

         var5[var0] = q0.c.i(new Object[]{Integer.toBinaryString(var0)}, "%8s").replace(' ', '0');
         var0++;
      }
   }

   public static String a(boolean var0, int var1, int var2, byte var3, byte var4) {
      String[] var5 = b;
      String var6;
      if (var3 < var5.length) {
         var6 = var5[var3];
      } else {
         var6 = q0.c.i(new Object[]{var3}, "0x%02x");
      }

      String var9;
      if (var4 == 0) {
         var9 = "";
      } else {
         label71: {
            var5 = d;
            if (var3 != 2 && var3 != 3) {
               if (var3 == 4 || var3 == 6) {
                  if (var4 == 1) {
                     var9 = "ACK";
                  } else {
                     var9 = var5[var4];
                  }
                  break label71;
               }

               if (var3 != 7 && var3 != 8) {
                  String[] var7 = c;
                  if (var4 < var7.length) {
                     var9 = var7[var4];
                  } else {
                     var9 = var5[var4];
                  }

                  String var8;
                  String var11;
                  if (var3 == 5 && (var4 & 4) != 0) {
                     var8 = "HEADERS";
                     var11 = "PUSH_PROMISE";
                  } else {
                     if (var3 != 0 || (var4 & 32) == 0) {
                        break label71;
                     }

                     var8 = "PRIORITY";
                     var11 = "COMPRESSED";
                  }

                  var9 = var9.replace(var8, var11);
                  break label71;
               }
            }

            var9 = var5[var4];
         }
      }

      String var12;
      if (var0) {
         var12 = "<<";
      } else {
         var12 = ">>";
      }

      return q0.c.i(new Object[]{var12, var1, var2, var6, var9}, "%s 0x%08x %5d %-13s %s");
   }

   public static void b(Object[] var0, String var1) {
      throw new IOException(q0.c.i(var0, var1));
   }
}
