package u0;

import a1.i;
import a1.s;
import a1.v;

public final class e implements s {
   public final i a;
   public boolean b;
   public final g c;

   public e(g var1) {
      this.c = var1;
      this.a = new i(var1.d.a());
   }

   @Override
   public final v a() {
      return this.a;
   }

   @Override
   public final void close() {
      if (!this.b) {
         this.b = true;
         g var3 = this.c;
         var3.getClass();
         i var1 = this.a;
         v var2 = var1.e;
         var1.e = v.d;
         var2.a();
         var2.b();
         var3.e = 3;
      }
   }

   @Override
   public final void flush() {
      if (!this.b) {
         this.c.d.flush();
      }
   }

   @Override
   public final void i(a1.e var1, long var2) {
      if (!this.b) {
         long var4 = var1.b;
         byte[] var6 = q0.c.a;
         if ((0L | var2) >= 0L && 0L <= var4 && var4 - 0L >= var2) {
            this.c.d.i(var1, var2);
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      } else {
         throw new IllegalStateException("closed");
      }
   }
}
