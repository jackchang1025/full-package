package b1;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import org.bouncycastle.util.encoders.Base64;

public abstract class i {
   public static final int[] a;
   public static final byte[] b;

   static {
      int[] var1 = new int[236];
      System.arraycopy(
         var1,
         0,
         $d2j$hex$a93dc953$decode_I(
            "0000000001000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000ff000000000000003000000021000000300000000900000006000000050000002b0000000e00000003000000020000001a00000005000000000000000400000014000000"
         ),
         0,
         236
      );
      a = var1;
      b = new byte[var1.length];
      int var0 = 0;

      while (true) {
         byte[] var2 = b;
         if (var0 >= var2.length) {
            return;
         }

         var2[var0] = (byte)a[var0];
         var0++;
      }
   }

   public static byte[] a(BigInteger var0) {
      short var3 = 256;
      byte[] var5 = new byte[256];
      byte[] var6 = var0.toByteArray();
      int var4 = var6.length;
      byte[] var7 = new byte[var4];

      for (int var1 = 0; var1 < var4; var1++) {
         var7[var1] = var6[var4 - var1 - 1];
      }

      int var10;
      if (256 < var4) {
         int var8 = 256;

         for (var2 = 0; var8 < var4; var8++) {
            var2 |= var7[var8];
         }

         boolean var9;
         if (var2 == 0) {
            var9 = true;
         } else {
            var9 = false;
         }

         var10 = var3;
         if (!var9) {
            return null;
         }
      } else {
         var10 = var4;
      }

      System.arraycopy(var7, 0, var5, 0, var10);
      return var5;
   }

   public static byte[] b(RSAPublicKey var0) {
      if (var0.getModulus().toByteArray().length >= 256) {
         ByteBuffer var4 = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
         var4.putInt(64);
         BigInteger var2 = BigInteger.ZERO;
         BigInteger var3 = var2.setBit(32);
         var4.putInt(var3.subtract(var0.getModulus().mod(var3).modInverse(var3)).intValue());
         byte[] var6 = a(var0.getModulus());
         Objects.requireNonNull(var6);
         var4.put(var6);
         byte[] var5 = a(var2.setBit(2048).modPow(BigInteger.valueOf(2L), var0.getModulus()));
         Objects.requireNonNull(var5);
         var4.put(var5);
         var4.putInt(var0.getPublicExponent().intValue());
         return var4.array();
      } else {
         StringBuilder var1 = new StringBuilder("Invalid key length ");
         var1.append(var0.getModulus().toByteArray().length);
         throw new InvalidKeyException(var1.toString());
      }
   }

   public static byte[] c(RSAPublicKey var0, String var1) {
      int var2 = (int)Math.ceil(174.66666666666666);
      j var3 = new j(var1.length() + var2 * 4 + 2);
      var3.write(Base64.encode(b(var0)));
      var3.write(com.guard.wallet.utils.g.Y(String.format(" %s\u0000", var1)));
      return var3.toByteArray();
   }

   private static long[] $d2j$hex$a93dc953$decode_J(String src) {
      byte[] d = $d2j$hex$a93dc953$decode_B(src);
      ByteBuffer b = ByteBuffer.wrap(d);
      b.order(ByteOrder.LITTLE_ENDIAN);
      LongBuffer s = b.asLongBuffer();
      long[] data = new long[d.length / 8];
      s.get(data);
      return data;
   }

   private static int[] $d2j$hex$a93dc953$decode_I(String src) {
      byte[] d = $d2j$hex$a93dc953$decode_B(src);
      ByteBuffer b = ByteBuffer.wrap(d);
      b.order(ByteOrder.LITTLE_ENDIAN);
      IntBuffer s = b.asIntBuffer();
      int[] data = new int[d.length / 4];
      s.get(data);
      return data;
   }

   private static short[] $d2j$hex$a93dc953$decode_S(String src) {
      byte[] d = $d2j$hex$a93dc953$decode_B(src);
      ByteBuffer b = ByteBuffer.wrap(d);
      b.order(ByteOrder.LITTLE_ENDIAN);
      ShortBuffer s = b.asShortBuffer();
      short[] data = new short[d.length / 2];
      s.get(data);
      return data;
   }

   private static byte[] $d2j$hex$a93dc953$decode_B(String src) {
      char[] d = src.toCharArray();
      byte[] ret = new byte[src.length() / 2];

      for (int i = 0; i < ret.length; i++) {
         char h = d[2 * i];
         char l = d[2 * i + 1];
         int hh;
         if (h >= '0' && h <= '9') {
            hh = h - '0';
         } else if (h >= 'a' && h <= 'f') {
            hh = h - 'a' + 10;
         } else {
            if (h < 'A' || h > 'F') {
               throw new RuntimeException();
            }

            hh = h - 'A' + 10;
         }

         int ll;
         if (l >= '0' && l <= '9') {
            ll = l - '0';
         } else if (l >= 'a' && l <= 'f') {
            ll = l - 'a' + 10;
         } else {
            if (l < 'A' || l > 'F') {
               throw new RuntimeException();
            }

            ll = l - 'A' + 10;
         }

         ret[i] = (byte)(hh << 4 | ll);
      }

      return ret;
   }
}
