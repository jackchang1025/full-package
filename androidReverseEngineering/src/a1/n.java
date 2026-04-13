package a1;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class n implements f {
   public final e a = new e();
   public final s b;
   public boolean c;

   public n(s var1) {
      this.b = var1;
   }

   @Override
   public final v a() {
      return this.b.a();
   }

   @Override
   public final f c(byte[] var1, int var2, int var3) {
      if (!this.c) {
         this.a.I(var1, var2, var3);
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void close() {
      s var4 = this.b;
      if (!this.c) {
         Throwable var18;
         label127: {
            label126: {
               Throwable var10000;
               label132: {
                  long var1;
                  try {
                     var3 = this.a;
                     var1 = var3.b;
                  } catch (Throwable var17) {
                     var10000 = var17;
                     boolean var10001 = false;
                     break label132;
                  }

                  if (var1 <= 0L) {
                     break label126;
                  }

                  label121:
                  try {
                     var4.i(var3, var1);
                     break label126;
                  } catch (Throwable var16) {
                     var10000 = var16;
                     boolean var21 = false;
                     break label121;
                  }
               }

               var18 = var10000;
               break label127;
            }

            var18 = null;
         }

         label115: {
            try {
               var4.close();
            } catch (Throwable var15) {
               var20 = var18;
               if (var18 == null) {
                  var20 = var15;
               }
               break label115;
            }

            var20 = var18;
         }

         this.c = true;
         if (var20 != null) {
            Charset var19 = w.a;
            throw var20;
         }
      }
   }

   @Override
   public final long d(t var1) {
      long var2 = 0L;

      while (true) {
         long var4 = ((b)var1).u(this.a, 8192L);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
         this.x();
      }
   }

   @Override
   public final f e(long var1) {
      if (!this.c) {
         this.a.L(var1);
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final void flush() {
      if (!this.c) {
         e var4 = this.a;
         long var1 = var4.b;
         s var3 = this.b;
         if (var1 > 0L) {
            var3.i(var4, var1);
         }

         var3.flush();
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final f g(h var1) {
      if (!this.c) {
         this.a.H(var1);
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final void i(e var1, long var2) {
      if (!this.c) {
         this.a.i(var1, var2);
         this.x();
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final boolean isOpen() {
      return this.c ^ true;
   }

   @Override
   public final f j(int var1) {
      if (!this.c) {
         this.a.N(var1);
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final f k(int var1) {
      if (!this.c) {
         this.a.M(var1);
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final f o(int var1) {
      if (!this.c) {
         this.a.J(var1);
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final f p(byte[] var1) {
      if (!this.c) {
         e var2 = this.a;
         var2.getClass();
         if (var1 != null) {
            var2.I(var1, 0, var1.length);
            this.x();
            return this;
         } else {
            throw new IllegalArgumentException("source == null");
         }
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final f s(String var1) {
      if (!this.c) {
         e var2 = this.a;
         var2.getClass();
         var2.O(var1, 0, var1.length());
         this.x();
         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   @Override
   public final f t(long var1) {
      if (!this.c) {
         this.a.K(var1);
         this.x();
         return this;
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
   public final int write(ByteBuffer var1) {
      if (!this.c) {
         int var2 = this.a.write(var1);
         this.x();
         return var2;
      } else {
         throw new IllegalStateException("closed");
      }
   }

   public final f x() {
      if (!this.c) {
         e var7 = this.a;
         long var4 = var7.b;
         long var2;
         if (var4 == 0L) {
            var2 = 0L;
         } else {
            p var6 = var7.a.g;
            int var1 = var6.c;
            var2 = var4;
            if (var1 < 8192) {
               var2 = var4;
               if (var6.e) {
                  var2 = var4 - (long)(var1 - var6.b);
               }
            }
         }

         if (var2 > 0L) {
            this.b.i(var7, var2);
         }

         return this;
      } else {
         throw new IllegalStateException("closed");
      }
   }
}
