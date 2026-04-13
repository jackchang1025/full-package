package a1;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class h implements Serializable, Comparable {
   public static final char[] d = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   public static final h e = g();
   public final byte[] a;
   public transient int b;
   public transient String c;

   public h(byte[] var1) {
      this.a = var1;
   }

   public static h b(String var0) {
      if (var0.length() % 2 != 0) {
         throw new IllegalArgumentException("Unexpected hex string: ".concat(var0));
      } else {
         int var2 = var0.length() / 2;
         byte[] var5 = new byte[var2];

         for (int var1 = 0; var1 < var2; var1++) {
            int var4 = var1 * 2;
            int var3 = c(var0.charAt(var4));
            var5[var1] = (byte)(c(var0.charAt(var4 + 1)) + (var3 << 4));
         }

         return g(var5);
      }
   }

   public static int c(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else {
         byte var1 = 97;
         if (var0 < 'a' || var0 > 'f') {
            var1 = 65;
            if (var0 < 'A' || var0 > 'F') {
               StringBuilder var2 = new StringBuilder("Unexpected hex digit: ");
               var2.append(var0);
               throw new IllegalArgumentException(var2.toString());
            }
         }

         return var0 - var1 + 10;
      }
   }

   public static h d(String var0) {
      if (var0 != null) {
         h var1 = new h(var0.getBytes(w.a));
         var1.c = var0;
         return var1;
      } else {
         throw new IllegalArgumentException("s == null");
      }
   }

   public static h g(byte... var0) {
      if (var0 != null) {
         return new h((byte[])var0.clone());
      } else {
         throw new IllegalArgumentException("data == null");
      }
   }

   public String a() {
      byte[] var9 = q.h;
      byte[] var7 = this.a;
      byte[] var8 = new byte[(var7.length + 2) / 3 * 4];
      int var3 = var7.length - var7.length % 3;
      int var1 = 0;

      int var2;
      for (var2 = 0; var1 < var3; var1 += 3) {
         int var4 = var2 + 1;
         var8[var2] = var9[(var7[var1] & 255) >> 2];
         var2 = var4 + 1;
         byte var6 = var7[var1];
         int var5 = var1 + 1;
         var8[var4] = var9[(var6 & 3) << 4 | (var7[var5] & 255) >> 4];
         var4 = var2 + 1;
         var6 = var7[var5];
         var5 = var1 + 2;
         var8[var2] = var9[(var6 & 15) << 2 | (var7[var5] & 255) >> 6];
         var2 = var4 + 1;
         var8[var4] = var9[var7[var5] & 63];
      }

      var1 = var7.length % 3;
      if (var1 != 1) {
         if (var1 == 2) {
            var1 = var2 + 1;
            var8[var2] = var9[(var7[var3] & 255) >> 2];
            var2 = var1 + 1;
            byte var19 = var7[var3];
            var8[var1] = var9[(var7[++var3] & 255) >> 4 | (var19 & 3) << 4];
            var8[var2] = var9[(var7[var3] & 15) << 2];
            var8[var2 + 1] = 61;
         }
      } else {
         var1 = var2 + 1;
         var8[var2] = var9[(var7[var3] & 255) >> 2];
         var2 = var1 + 1;
         var8[var1] = var9[(var7[var3] & 3) << 4];
         var8[var2] = 61;
         var8[var2 + 1] = 61;
      }

      try {
         return new String(var8, "US-ASCII");
      } catch (UnsupportedEncodingException var10) {
         throw new AssertionError(var10);
      }
   }

   @Override
   public final int compareTo(Object var1) {
      var1 = var1;
      int var4 = this.j();
      int var6 = var1.j();
      int var5 = Math.min(var4, var6);
      byte var3 = 0;
      int var2 = 0;

      while (true) {
         if (var2 < var5) {
            int var7 = this.e(var2) & 255;
            int var8 = var1.e(var2) & 255;
            if (var7 == var8) {
               var2++;
               continue;
            }

            if (var7 < var8) {
               break;
            }
         } else {
            if (var4 == var6) {
               return var3;
            }

            if (var4 < var6) {
               break;
            }
         }

         return 1;
      }

      return -1;
   }

   public byte e(int var1) {
      return this.a[var1];
   }

   @Override
   public boolean equals(Object var1) {
      boolean var3 = true;
      if (var1 == this) {
         return true;
      } else {
         if (var1 instanceof h) {
            h var4 = (h)var1;
            int var2 = var4.j();
            var1 = this.a;
            if (var2 == var1.length && var4.h(0, 0, var1, var1.length)) {
               return var3;
            }
         }

         return false;
      }
   }

   public String f() {
      byte[] var6 = this.a;
      char[] var7 = new char[var6.length * 2];
      int var3 = var6.length;
      int var2 = 0;

      for (int var1 = 0; var2 < var3; var2++) {
         byte var4 = var6[var2];
         int var5 = var1 + 1;
         char[] var8 = d;
         var7[var1] = var8[var4 >> 4 & 15];
         var1 = var5 + 1;
         var7[var5] = var8[var4 & 15];
      }

      return new String(var7);
   }

   public boolean h(int var1, int var2, byte[] var3, int var4) {
      boolean var7 = false;
      boolean var6 = var7;
      if (var1 >= 0) {
         byte[] var9 = this.a;
         var6 = var7;
         if (var1 <= var9.length - var4) {
            var6 = var7;
            if (var2 >= 0) {
               var6 = var7;
               if (var2 <= var3.length - var4) {
                  Charset var8 = w.a;
                  int var5 = 0;

                  while (true) {
                     if (var5 >= var4) {
                        var10 = true;
                        break;
                     }

                     if (var9[var5 + var1] != var3[var5 + var2]) {
                        var10 = false;
                        break;
                     }

                     var5++;
                  }

                  var6 = var7;
                  if (var10) {
                     var6 = true;
                  }
               }
            }
         }
      }

      return var6;
   }

   @Override
   public int hashCode() {
      int var1 = this.b;
      if (var1 == 0) {
         var1 = Arrays.hashCode(this.a);
         this.b = var1;
      }

      return var1;
   }

   public boolean i(h var1, int var2) {
      return var1.h(0, 0, this.a, var2);
   }

   public int j() {
      return this.a.length;
   }

   public h k() {
      byte[] var1 = this.a;
      if (64 <= var1.length) {
         if (64 == var1.length) {
            return this;
         } else {
            byte[] var2 = new byte[64];
            System.arraycopy(var1, 0, var2, 0, 64);
            return new h(var2);
         }
      } else {
         throw new IllegalArgumentException(a.a.m(new StringBuilder("endIndex > length("), var1.length, ")"));
      }
   }

   public h l() {
      int var1 = 0;

      while (true) {
         byte[] var4 = this.a;
         if (var1 >= var4.length) {
            return this;
         }

         byte var3 = var4[var1];
         if (var3 >= 65 && var3 <= 90) {
            var4 = (byte[])var4.clone();
            int var2 = var1 + 1;
            var4[var1] = (byte)(var3 + 32);

            for (int var5 = var2; var5 < var4.length; var5++) {
               byte var6 = var4[var5];
               if (var6 >= 65 && var6 <= 90) {
                  var4[var5] = (byte)(var6 + 32);
               }
            }

            return new h(var4);
         }

         var1++;
      }
   }

   public String m() {
      String var1 = this.c;
      if (var1 == null) {
         var1 = new String(this.a, w.a);
         this.c = var1;
      }

      return var1;
   }

   public void n(e var1) {
      byte[] var2 = this.a;
      var1.I(var2, 0, var2.length);
   }

   @Override
   public String toString() {
      byte[] var5 = this.a;
      if (var5.length == 0) {
         return "[size=0]";
      } else {
         String var7 = this.m();
         int var3 = var7.length();
         int var1 = 0;
         int var2 = 0;

         while (true) {
            if (var1 < var3) {
               if (var2 == 64) {
                  break;
               }

               int var4 = var7.codePointAt(var1);
               if ((!Character.isISOControl(var4) || var4 == 10 || var4 == 13) && var4 != 65533) {
                  var2++;
                  var1 += Character.charCount(var4);
                  continue;
               }

               var1 = -1;
               break;
            }

            var1 = var7.length();
            break;
         }

         if (var1 == -1) {
            String var10;
            if (var5.length <= 64) {
               StringBuilder var9 = new StringBuilder("[hex=");
               var9.append(this.f());
               var9.append("]");
               var10 = var9.toString();
            } else {
               StringBuilder var11 = new StringBuilder("[size=");
               var11.append(var5.length);
               var11.append(" hex=");
               var11.append(this.k().f());
               var11.append("…]");
               var10 = var11.toString();
            }

            return var10;
         } else {
            String var6 = var7.substring(0, var1).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
            String var8;
            if (var1 < var7.length()) {
               StringBuilder var12 = new StringBuilder("[size=");
               var12.append(var5.length);
               var12.append(" text=");
               var12.append(var6);
               var12.append("…]");
               var8 = var12.toString();
            } else {
               var8 = a.a.l("[text=", var6, "]");
            }

            return var8;
         }
      }
   }
}
