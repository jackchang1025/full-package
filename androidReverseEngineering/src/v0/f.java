package v0;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class f {
   public static final c[] a;
   public static final Map b;

   static {
      c[] var1 = new c[61];
      c var2 = new c(c.i, "");
      int var0 = 0;
      var1[0] = var2;
      a1.h var3 = c.f;
      var1[1] = new c(var3, "GET");
      var1[2] = new c(var3, "POST");
      a1.h var4 = c.g;
      var1[3] = new c(var4, "/");
      var1[4] = new c(var4, "/index.html");
      a1.h var5 = c.h;
      var1[5] = new c(var5, "http");
      var1[6] = new c(var5, "https");
      a1.h var6 = c.e;
      var1[7] = new c(var6, "200");
      var1[8] = new c(var6, "204");
      var1[9] = new c(var6, "206");
      var1[10] = new c(var6, "304");
      var1[11] = new c(var6, "400");
      var1[12] = new c(var6, "404");
      var1[13] = new c(var6, "500");
      var1[14] = new c("accept-charset", "");
      var1[15] = new c("accept-encoding", "gzip, deflate");
      var1[16] = new c("accept-language", "");
      var1[17] = new c("accept-ranges", "");
      var1[18] = new c("accept", "");
      var1[19] = new c("access-control-allow-origin", "");
      var1[20] = new c("age", "");
      var1[21] = new c("allow", "");
      var1[22] = new c("authorization", "");
      var1[23] = new c("cache-control", "");
      var1[24] = new c("content-disposition", "");
      var1[25] = new c("content-encoding", "");
      var1[26] = new c("content-language", "");
      var1[27] = new c("content-length", "");
      var1[28] = new c("content-location", "");
      var1[29] = new c("content-range", "");
      var1[30] = new c("content-type", "");
      var1[31] = new c("cookie", "");
      var1[32] = new c("date", "");
      var1[33] = new c("etag", "");
      var1[34] = new c("expect", "");
      var1[35] = new c("expires", "");
      var1[36] = new c("from", "");
      var1[37] = new c("host", "");
      var1[38] = new c("if-match", "");
      var1[39] = new c("if-modified-since", "");
      var1[40] = new c("if-none-match", "");
      var1[41] = new c("if-range", "");
      var1[42] = new c("if-unmodified-since", "");
      var1[43] = new c("last-modified", "");
      var1[44] = new c("link", "");
      var1[45] = new c("location", "");
      var1[46] = new c("max-forwards", "");
      var1[47] = new c("proxy-authenticate", "");
      var1[48] = new c("proxy-authorization", "");
      var1[49] = new c("range", "");
      var1[50] = new c("referer", "");
      var1[51] = new c("refresh", "");
      var1[52] = new c("retry-after", "");
      var1[53] = new c("server", "");
      var1[54] = new c("set-cookie", "");
      var1[55] = new c("strict-transport-security", "");
      var1[56] = new c("transfer-encoding", "");
      var1[57] = new c("user-agent", "");
      var1[58] = new c("vary", "");
      var1[59] = new c("via", "");
      var1[60] = new c("www-authenticate", "");
      a = var1;

      for (var7 = new LinkedHashMap(var1.length); var0 < var1.length; var0++) {
         if (!var7.containsKey(var1[var0].a)) {
            var7.put(var1[var0].a, var0);
         }
      }

      b = Collections.unmodifiableMap(var7);
   }

   public static void a(a1.h var0) {
      int var2 = var0.j();

      for (int var1 = 0; var1 < var2; var1++) {
         byte var3 = var0.e(var1);
         if (var3 >= 65 && var3 <= 90) {
            StringBuilder var4 = new StringBuilder("PROTOCOL_ERROR response malformed: mixed case name: ");
            var4.append(var0.m());
            throw new IOException(var4.toString());
         }
      }
   }
}
