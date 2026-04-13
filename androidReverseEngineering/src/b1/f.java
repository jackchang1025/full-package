package b1;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class f {
   public final int a;
   public final int b;
   public final int c;
   public final int d;
   public final int e;
   public final int f;
   public byte[] g;

   public f(ByteBuffer var1) {
      this.a = var1.getInt();
      this.b = var1.getInt();
      this.c = var1.getInt();
      this.d = var1.getInt();
      this.e = var1.getInt();
      this.f = var1.getInt();
   }

   public static f a(InputStream var0, int var1, int var2) {
      ByteBuffer var6 = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
      int var3 = 0;

      int var16;
      do {
         var16 = var0.read(var6.array(), var3, 24 - var3);
         if (var16 < 0) {
            throw new IOException("Stream closed");
         }

         var16 = var3 + var16;
         var3 = var16;
      } while (var16 < 24);

      f var18 = new f(var6);
      var3 = var18.f;
      var16 = var18.a;
      if (var16 != ~var3) {
         throw new StreamCorruptedException(String.format("Invalid header: Invalid magic 0x%x.", var3));
      } else if (var16 != 1129208147
         && var16 != 1314410051
         && var16 != 1313165391
         && var16 != 1497451343
         && var16 != 1163086915
         && var16 != 1163154007
         && var16 != 1213486401
         && var16 != 1397511251) {
         throw new StreamCorruptedException(String.format("Invalid header: Invalid command 0x%x.", var16));
      } else {
         int var5 = var18.d;
         if (var5 < 0 || var5 > var2) {
            throw new StreamCorruptedException(String.format("Invalid header: Invalid data length %d", var5));
         } else if (var5 == 0) {
            return var18;
         } else {
            var18.g = new byte[var5];
            var2 = 0;

            do {
               var3 = var0.read(var18.g, var2, var5 - var2);
               if (var3 < 0) {
                  throw new IOException("Stream closed");
               }

               var3 = var2 + var3;
               var2 = var3;
            } while (var3 < var5);

            if (var1 <= 16777216 || var16 == 1314410051 && var18.b <= 16777216) {
               byte[] var7 = var18.g;
               byte[] var8 = b1.g.a;
               var3 = var7.length;
               var1 = 0;

               for (var2 = 0; var1 < 0 + var3; var1++) {
                  var2 += var7[var1] & 255;
               }

               if (var2 != var18.e) {
                  throw new StreamCorruptedException("Invalid header: Checksum mismatched.");
               }
            }

            return var18;
         }
      }
   }

   @Override
   public final String toString() {
      String var1;
      switch (this.a) {
         case 1129208147:
            var1 = "SYNC";
            break;
         case 1163086915:
            var1 = "CLSE";
            break;
         case 1163154007:
            var1 = "WRTE";
            break;
         case 1213486401:
            var1 = "AUTH";
            break;
         case 1313165391:
            var1 = "OPEN";
            break;
         case 1314410051:
            var1 = "CNXN";
            break;
         case 1397511251:
            var1 = "STLS";
            break;
         case 1497451343:
            var1 = "OKAY";
            break;
         default:
            var1 = "????";
      }

      StringBuilder var2 = a.a.s("Message{command=", var1, ", arg0=0x");
      var2.append(Integer.toHexString(this.b));
      var2.append(", arg1=0x");
      var2.append(Integer.toHexString(this.c));
      var2.append(", payloadLength=");
      var2.append(this.d);
      var2.append(", checksum=");
      var2.append(this.e);
      var2.append(", magic=0x");
      var2.append(Integer.toHexString(this.f));
      var2.append(", payload=");
      var2.append(Arrays.toString(this.g));
      var2.append('}');
      return var2.toString();
   }
}
