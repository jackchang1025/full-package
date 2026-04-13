package g1;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import l1.c;
import l1.d;
import l1.e;
import l1.f;

public abstract class a {
   public int a = 0;

   public static List b(l1.b var0) {
      StringBuilder var2 = new StringBuilder(100);
      String var1;
      if (var0 instanceof l1.a) {
         var2.append("GET ");
         var2.append(((c)((l1.a)var0)).b);
         var1 = " HTTP/1.1";
      } else {
         if (!(var0 instanceof f)) {
            throw new IllegalArgumentException("unknown role");
         }

         var2.append("HTTP/1.1 101 ");
         var1 = ((d)((f)var0)).b;
      }

      var2.append(var1);
      var2.append("\r\n");
      e var3 = (e)var0;

      for (String var4 : Collections.unmodifiableSet(var3.a.keySet())) {
         var1 = var3.a(var4);
         var2.append(var4);
         var2.append(": ");
         var2.append(var1);
         var2.append("\r\n");
      }

      var2.append("\r\n");
      var1 = var2.toString();
      CodingErrorAction var6 = o1.a.a;
      byte[] var10 = var1.getBytes(StandardCharsets.US_ASCII);
      ByteBuffer var7 = ByteBuffer.allocate(var10.length + 0);
      var7.put(var10);
      ((Buffer)var7).flip();
      return Collections.singletonList(var7);
   }

   public static String c(ByteBuffer var0) {
      ByteBuffer var5 = ByteBuffer.allocate(var0.remaining());
      int var2 = 48;

      Object var4;
      while (true) {
         boolean var3 = var0.hasRemaining();
         var4 = null;
         if (!var3) {
            ((Buffer)var0).position(var0.position() - var5.position());
            var0 = null;
            break;
         }

         byte var1 = var0.get();
         var5.put(var1);
         if (var2 == 13 && var1 == 10) {
            ((Buffer)var5).limit(var5.position() - 2);
            ((Buffer)var5).position(0);
            var0 = var5;
            break;
         }

         var2 = var1;
      }

      String var7;
      if (var0 == null) {
         var7 = (String)var4;
      } else {
         var4 = var0.array();
         var2 = var0.limit();
         CodingErrorAction var8 = o1.a.a;
         var7 = new String((byte[])var4, 0, var2, StandardCharsets.US_ASCII);
      }

      return var7;
   }

   public abstract b a();

   public final e d(ByteBuffer var1) {
      int var2 = this.a;
      String var3 = c(var1);
      if (var3 == null) {
         throw new i1.b(var1.capacity() + 128);
      } else {
         String[] var4 = var3.split(" ", 3);
         if (var4.length != 3) {
            throw new i1.e();
         } else {
            Object var7;
            if (var2 == 1) {
               if (!"101".equals(var4[1])) {
                  throw new i1.e(String.format("Invalid status code received: %s Status line: %s", var4[1], var3));
               }

               if (!"HTTP/1.1".equalsIgnoreCase(var4[0])) {
                  throw new i1.e(String.format("Invalid status line received: %s Status line: %s", var4[0], var3));
               }

               var7 = new d();
               Short.parseShort(var4[1]);
               ((d)var7).b = var4[2];
            } else {
               if (!"GET".equalsIgnoreCase(var4[0])) {
                  throw new i1.e(String.format("Invalid request method received: %s Status line: %s", var4[0], var3));
               }

               if (!"HTTP/1.1".equalsIgnoreCase(var4[2])) {
                  throw new i1.e(String.format("Invalid status line received: %s Status line: %s", var4[2], var3));
               }

               var7 = new c();
               String var8 = var4[1];
               if (var8 == null) {
                  throw new IllegalArgumentException("http resource descriptor must not be null");
               }

               ((c)var7).b = var8;
            }

            while (true) {
               String var9 = c(var1);
               if (var9 == null || var9.length() <= 0) {
                  if (var9 != null) {
                     return (e)var7;
                  } else {
                     throw new i1.b();
                  }
               }

               var4 = var9.split(":", 2);
               if (var4.length != 2) {
                  throw new i1.e("not an http header");
               }

               String var5 = var4[0];
               String var11;
               if (((e)var7).a.containsKey(var5)) {
                  var5 = var4[0];
                  StringBuilder var6 = new StringBuilder();
                  var6.append(((e)var7).a(var4[0]));
                  var6.append("; ");
                  var6.append(var4[1].replaceFirst("^ +", ""));
                  var11 = var6.toString();
               } else {
                  var5 = var4[0];
                  var11 = var4[1].replaceFirst("^ +", "");
               }

               ((e)var7).b(var5, var11);
            }
         }
      }
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }
}
