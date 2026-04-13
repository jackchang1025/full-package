package a1;

import java.io.IOException;
import java.io.OutputStream;

public final class a implements s {
   public final int a;
   public final Object b;
   public final Object c;

   public a(s0.j var1, a var2) {
      this.a = 0;
      this.c = var1;
      this.b = var2;
      super();
   }

   public a(s0.j var1, OutputStream var2) {
      this.a = 1;
      this.b = var1;
      this.c = var2;
      super();
   }

   @Override
   public final v a() {
      switch (this.a) {
         case 0:
            return (d)this.c;
         default:
            return (v)this.b;
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void close() {
      int var1 = this.a;
      Object var3 = this.c;
      switch (var1) {
         case 0:
            d var2 = (d)var3;
            var2.i();
            boolean var7 = false /* VF: Semaphore variable */;

            try {
               var7 = true;
               ((s)this.b).close();
               var7 = false;
            } catch (IOException var8) {
               throw ((d)var3).j(var8);
            } finally {
               if (var7) {
                  var2.k(false);
               }
            }

            var2.k(true);
            return;
         default:
            ((OutputStream)var3).close();
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void flush() {
      int var1 = this.a;
      Object var3 = this.c;
      switch (var1) {
         case 0:
            d var2 = (d)var3;
            var2.i();
            boolean var7 = false /* VF: Semaphore variable */;

            try {
               var7 = true;
               ((s)this.b).flush();
               var7 = false;
            } catch (IOException var8) {
               throw ((d)var3).j(var8);
            } finally {
               if (var7) {
                  var2.k(false);
               }
            }

            var2.k(true);
            return;
         default:
            ((OutputStream)var3).flush();
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void i(e var1, long var2) {
      int var4 = this.a;
      Object var12 = this.b;
      Object var11 = this.c;
      switch (var4) {
         case 0:
            w.a(var1.b, 0L, var2);

            while (var2 > 0L) {
               p var21 = var1.a;
               long var19 = 0L;

               long var20;
               while (true) {
                  var20 = var19;
                  if (var19 >= 65536L) {
                     break;
                  }

                  var19 += (long)(var21.c - var21.b);
                  if (var19 >= var2) {
                     var20 = var2;
                     break;
                  }

                  var21 = var21.f;
               }

               d var22 = (d)var11;
               var22.i();
               boolean var15 = false /* VF: Semaphore variable */;

               try {
                  var15 = true;
                  ((s)var12).i(var1, var20);
                  var15 = false;
               } catch (IOException var16) {
                  throw ((d)var11).j(var16);
               } finally {
                  if (var15) {
                     var22.k(false);
                  }
               }

               var2 -= var20;
               var22.k(true);
            }

            return;
         default:
            w.a(var1.b, 0L, var2);

            while (var2 > 0L) {
               ((v)var12).f();
               p var10 = var1.a;
               var4 = (int)Math.min(var2, (long)(var10.c - var10.b));
               ((OutputStream)var11).write(var10.a, var10.b, var4);
               int var5 = var10.b + var4;
               var10.b = var5;
               long var8 = (long)var4;
               long var6 = var2 - var8;
               var1.b -= var8;
               var2 = var6;
               if (var5 == var10.c) {
                  var1.a = var10.a();
                  q.L(var10);
                  var2 = var6;
               }
            }
      }
   }

   @Override
   public final String toString() {
      switch (this.a) {
         case 0:
            StringBuilder var2 = new StringBuilder("AsyncTimeout.sink(");
            var2.append((s)this.b);
            var2.append(")");
            return var2.toString();
         default:
            StringBuilder var1 = new StringBuilder("sink(");
            var1.append((OutputStream)this.c);
            var1.append(")");
            return var1.toString();
      }
   }
}
