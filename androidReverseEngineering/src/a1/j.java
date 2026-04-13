package a1;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class j implements t {
   public int a = 0;
   public final o b;
   public final Inflater c;
   public final k d;
   public final CRC32 e = new CRC32();

   public j(t var1) {
      if (var1 != null) {
         Inflater var2 = new Inflater(true);
         this.c = var2;
         Logger var3 = l.a;
         o var4 = new o(var1);
         this.b = var4;
         this.d = new k(var4, var2);
      } else {
         throw new IllegalArgumentException("source == null");
      }
   }

   public static void x(String var0, int var1, int var2) {
      if (var2 != var1) {
         throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", var0, var2, var1));
      }
   }

   @Override
   public final v a() {
      return this.b.a();
   }

   @Override
   public final void close() {
      this.d.close();
   }

   @Override
   public final long u(e var1, long var2) {
      long var27;
      int var4 = (var27 = var2 - 0L) == 0L ? 0 : (var27 < 0L ? -1 : 1);
      if (var4 >= 0) {
         if (var4 == 0) {
            return 0L;
         } else {
            var4 = this.a;
            CRC32 var9 = this.e;
            o var10 = this.b;
            if (var4 == 0) {
               var10.r(10L);
               e var11 = var10.a;
               byte var5 = var11.z(3L);
               boolean var17;
               if ((var5 >> 1 & 1) == 1) {
                  var17 = 1;
               } else {
                  var17 = 0;
               }

               if (var17) {
                  this.y(var10.a, 0L, 10L);
               }

               x("ID1ID2", 8075, var10.readShort());
               var10.skip(8L);
               if ((var5 >> 2 & 1) == 1) {
                  var10.r(2L);
                  if (var17) {
                     this.y(var10.a, 0L, 2L);
                  }

                  int var6 = var11.readShort();
                  Charset var12 = w.a;
                  var6 &= 65535;
                  long var7 = (long)((short)((var6 & 0xFF) << 8 | (var6 & 0xFF00) >>> 8) & '\uffff');
                  var10.r(var7);
                  if (var17) {
                     this.y(var10.a, 0L, var7);
                  }

                  var10.skip(var7);
               }

               if ((var5 >> 3 & 1) == 1) {
                  long var23 = var10.x((byte)0, 0L, Long.MAX_VALUE);
                  if (var23 == -1L) {
                     throw new EOFException();
                  }

                  if (var17) {
                     this.y(var10.a, 0L, var23 + 1L);
                  }

                  var10.skip(var23 + 1L);
               }

               if ((var5 >> 4 & 1) == 1) {
                  long var24 = var10.x((byte)0, 0L, Long.MAX_VALUE);
                  if (var24 == -1L) {
                     throw new EOFException();
                  }

                  if (var17) {
                     this.y(var10.a, 0L, var24 + 1L);
                  }

                  var10.skip(var24 + 1L);
               }

               if (var17) {
                  var10.r(2L);
                  var17 = var11.readShort();
                  Charset var26 = w.a;
                  var17 &= 65535;
                  x("FHCRC", (short)((var17 & 0xFF) << 8 | (var17 & 0xFF00) >>> 8), (short)((int)var9.getValue()));
                  var9.reset();
               }

               this.a = 1;
            }

            if (this.a == 1) {
               long var25 = var1.b;
               var2 = this.d.u(var1, var2);
               if (var2 != -1L) {
                  this.y(var1, var25, var2);
                  return var2;
               }

               this.a = 2;
            }

            if (this.a == 2) {
               var10.r(4L);
               var4 = var10.a.readInt();
               Charset var14 = w.a;
               x("CRC", (var4 & 0xFF) << 24 | (var4 & 0xFF000000) >>> 24 | (var4 & 0xFF0000) >>> 8 | (var4 & 0xFF00) << 8, (int)var9.getValue());
               var10.r(4L);
               var4 = var10.a.readInt();
               x("ISIZE", (var4 & 0xFF) << 24 | (var4 & 0xFF000000) >>> 24 | (var4 & 0xFF0000) >>> 8 | (0xFF00 & var4) << 8, (int)this.c.getBytesWritten());
               this.a = 3;
               if (!var10.n()) {
                  throw new IOException("gzip finished without exhausting source");
               }
            }

            return -1L;
         }
      } else {
         StringBuilder var13 = new StringBuilder("byteCount < 0: ");
         var13.append(var2);
         throw new IllegalArgumentException(var13.toString());
      }
   }

   public final void y(e var1, long var2, long var4) {
      p var13 = var1.a;

      while (true) {
         int var6 = var13.c;
         int var7 = var13.b;
         p var12 = var13;
         long var8 = var2;
         long var10 = var4;
         if (var2 < (long)(var6 - var7)) {
            while (var10 > 0L) {
               var7 = (int)((long)var12.b + var8);
               var6 = (int)Math.min((long)(var12.c - var7), var10);
               this.e.update(var12.a, var7, var6);
               var10 -= (long)var6;
               var12 = var12.f;
               var8 = 0L;
            }

            return;
         }

         var2 -= (long)(var6 - var7);
         var13 = var13.f;
      }
   }
}
