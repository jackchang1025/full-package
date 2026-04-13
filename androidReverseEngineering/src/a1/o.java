package a1;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class o implements g {
   public final e a = new e();
   public final t b;
   public boolean c;

   public o(t var1) {
      if (var1 != null) {
         this.b = var1;
      } else {
         throw new NullPointerException("source == null");
      }
   }

   @Override
   public final v a() {
      return this.b.a();
   }

   @Override
   public final int b(m var1) {
      if (this.c) {
         throw new IllegalStateException("closed");
      } else {
         e var3;
         do {
            var3 = this.a;
            int var2 = var3.F(var1, true);
            if (var2 == -1) {
               return -1;
            }

            if (var2 != -2) {
               var3.skip((long)var1.a[var2].j());
               return var2;
            }
         } while (this.b.u(var3, 8192L) != -1L);

         return -1;
      }
   }

   @Override
   public final void close() {
      if (!this.c) {
         this.c = true;
         this.b.close();
         this.a.x();
      }
   }

   @Override
   public final e f() {
      return this.a;
   }

   @Override
   public final h h(long var1) {
      this.r(var1);
      return this.a.h(var1);
   }

   @Override
   public final boolean isOpen() {
      return this.c ^ true;
   }

   @Override
   public final String l() {
      return this.q(Long.MAX_VALUE);
   }

   @Override
   public final byte[] m() {
      t var2 = this.b;
      e var1 = this.a;
      var1.d(var2);
      return var1.m();
   }

   @Override
   public final boolean n() {
      if (this.c) {
         throw new IllegalStateException("closed");
      } else {
         e var2 = this.a;
         boolean var1;
         if (var2.n() && this.b.u(var2, 8192L) == -1L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   @Override
   public final String q(long var1) {
      if (var1 >= 0L) {
         long var3;
         if (var1 == Long.MAX_VALUE) {
            var3 = Long.MAX_VALUE;
         } else {
            var3 = var1 + 1L;
         }

         long var5 = this.x((byte)10, 0L, var3);
         e var10 = this.a;
         if (var5 != -1L) {
            return var10.E(var5);
         } else if (var3 < Long.MAX_VALUE && this.z(var3) && var10.z(var3 - 1L) == 13 && this.z(1L + var3) && var10.z(var3) == 10) {
            return var10.E(var3);
         } else {
            e var8 = new e();
            var10.y(var8, 0L, Math.min(32L, var10.b));
            StringBuilder var9 = new StringBuilder("\\n not found: limit=");
            var9.append(Math.min(var10.b, var1));
            var9.append(" content=");
            var9.append(new h(var8.m()).f());
            var9.append('…');
            throw new EOFException(var9.toString());
         }
      } else {
         StringBuilder var7 = new StringBuilder("limit < 0: ");
         var7.append(var1);
         throw new IllegalArgumentException(var7.toString());
      }
   }

   @Override
   public final void r(long var1) {
      if (!this.z(var1)) {
         throw new EOFException();
      }
   }

   @Override
   public final int read(ByteBuffer var1) {
      e var2 = this.a;
      return var2.b == 0L && this.b.u(var2, 8192L) == -1L ? -1 : var2.read(var1);
   }

   @Override
   public final byte readByte() {
      this.r(1L);
      return this.a.readByte();
   }

   @Override
   public final int readInt() {
      this.r(4L);
      return this.a.readInt();
   }

   @Override
   public final short readShort() {
      this.r(2L);
      return this.a.readShort();
   }

   @Override
   public final void skip(long var1) {
      if (!this.c) {
         while (var1 > 0L) {
            e var5 = this.a;
            if (var5.b == 0L && this.b.u(var5, 8192L) == -1L) {
               throw new EOFException();
            }

            long var3 = Math.min(var1, var5.b);
            var5.skip(var3);
            var1 -= var3;
         }
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("buffer(");
      var1.append(this.b);
      var1.append(")");
      return var1.toString();
   }

   @Override
   public final long u(e var1, long var2) {
      if (var1 != null) {
         if (var2 >= 0L) {
            if (!this.c) {
               e var4 = this.a;
               return var4.b == 0L && this.b.u(var4, 8192L) == -1L ? -1L : var4.u(var1, Math.min(var2, var4.b));
            } else {
               throw new IllegalStateException("closed");
            }
         } else {
            StringBuilder var5 = new StringBuilder("byteCount < 0: ");
            var5.append(var2);
            throw new IllegalArgumentException(var5.toString());
         }
      } else {
         throw new IllegalArgumentException("sink == null");
      }
   }

   @Override
   public final long v() {
      this.r(1L);
      int var2 = 0;

      e var5;
      while (true) {
         int var3 = var2 + 1;
         boolean var4 = this.z((long)var3);
         var5 = this.a;
         if (!var4) {
            break;
         }

         byte var1 = var5.z((long)var2);
         if ((var1 < 48 || var1 > 57) && (var1 < 97 || var1 > 102) && (var1 < 65 || var1 > 70)) {
            if (var2 == 0) {
               throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", var1));
            }
            break;
         }

         var2 = var3;
      }

      return var5.v();
   }

   @Override
   public final String w(Charset var1) {
      if (var1 != null) {
         t var2 = this.b;
         e var3 = this.a;
         var3.d(var2);
         return var3.w(var1);
      } else {
         throw new IllegalArgumentException("charset == null");
      }
   }

   public final long x(byte var1, long var2, long var4) {
      if (this.c) {
         throw new IllegalStateException("closed");
      } else {
         var2 = 0L;
         if (var4 < 0L) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", 0L, var4));
         } else {
            while (var2 < var4) {
               long var6 = this.a.A(var1, var2, var4);
               if (var6 != -1L) {
                  return var6;
               }

               e var8 = this.a;
               var6 = var8.b;
               if (var6 >= var4 || this.b.u(var8, 8192L) == -1L) {
                  break;
               }

               var2 = Math.max(var2, var6);
            }

            return -1L;
         }
      }
   }

   public final void y(byte[] var1) {
      e var6 = this.a;
      int var2 = 0;
      int var3 = 0;

      try {
         this.r((long)var1.length);
      } catch (EOFException var8) {
         while (true) {
            long var4 = var6.b;
            if (var4 <= 0L) {
               throw var8;
            }

            var3 = var6.read(var1, var2, (int)var4);
            if (var3 == -1) {
               throw new AssertionError();
            }

            var2 += var3;
         }
      }

      var6.getClass();
      var2 = var3;

      while (var2 < var1.length) {
         var3 = var6.read(var1, var2, var1.length - var2);
         if (var3 == -1) {
            throw new EOFException();
         }

         var2 += var3;
      }
   }

   public final boolean z(long var1) {
      if (var1 >= 0L) {
         if (!this.c) {
            e var4;
            do {
               var4 = this.a;
               if (var4.b >= var1) {
                  return true;
               }
            } while (this.b.u(var4, 8192L) != -1L);

            return false;
         } else {
            throw new IllegalStateException("closed");
         }
      } else {
         StringBuilder var3 = new StringBuilder("byteCount < 0: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }
}
