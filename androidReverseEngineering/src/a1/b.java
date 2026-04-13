package a1;

import java.io.IOException;
import java.io.InputStream;

public final class b implements t {
   public final int a;
   public final Object b;
   public final Object c;

   public b(v var1, InputStream var2) {
      this.a = 1;
      this.b = var1;
      this.c = var2;
      super();
   }

   public b(s0.j var1, b var2) {
      this.a = 0;
      this.c = var1;
      this.b = var2;
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
      Object var4 = this.c;
      switch (var1) {
         case 0:
            d var2 = (d)var4;
            var2.i();
            boolean var7 = false /* VF: Semaphore variable */;

            try {
               var7 = true;
               ((t)this.b).close();
               var7 = false;
            } catch (IOException var8) {
               throw ((d)var4).j(var8);
            } finally {
               if (var7) {
                  var2.k(false);
               }
            }

            var2.k(true);
            return;
         default:
            ((InputStream)var4).close();
      }
   }

   @Override
   public final String toString() {
      switch (this.a) {
         case 0:
            StringBuilder var2 = new StringBuilder("AsyncTimeout.source(");
            var2.append((t)this.b);
            var2.append(")");
            return var2.toString();
         default:
            StringBuilder var1 = new StringBuilder("source(");
            var1.append((InputStream)this.c);
            var1.append(")");
            return var1.toString();
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final long u(e var1, long var2) {
      boolean var5 = false;
      int var4 = this.a;
      Object var10 = this.b;
      Object var8 = this.c;
      switch (var4) {
         case 0:
            d var44 = (d)var8;
            var44.i();

            label160: {
               Throwable var45;
               label159: {
                  try {
                     try {
                        var2 = ((t)var10).u(var1, var2);
                        break label160;
                     } catch (IOException var26) {
                        var34 = var26;
                     }
                  } catch (Throwable var27) {
                     var45 = var27;
                     boolean var49 = false;
                     break label159;
                  }

                  label154:
                  try {
                     throw ((d)var8).j(var34);
                  } catch (Throwable var25) {
                     var45 = var25;
                     boolean var50 = false;
                     break label154;
                  }
               }

               Throwable var35 = var45;
               var44.k(false);
               throw var35;
            }

            var44.k(true);
            return var2;
         default:
            long var6 = 0L;
            long var51;
            var4 = (var51 = var2 - 0L) == 0L ? 0 : (var51 < 0L ? -1 : 1);
            if (var4 < 0) {
               StringBuilder var33 = new StringBuilder("byteCount < 0: ");
               var33.append(var2);
               throw new IllegalArgumentException(var33.toString());
            } else if (var4 != 0) {
               AssertionError var10000;
               label180: {
                  p var9;
                  try {
                     ((v)var10).f();
                     var9 = var1.G(1);
                     var4 = (int)Math.min(var2, (long)(8192 - var9.c));
                     var4 = ((InputStream)var8).read(var9.a, var9.c, var4);
                  } catch (AssertionError var31) {
                     var10000 = var31;
                     boolean var10001 = false;
                     break label180;
                  }

                  label177:
                  if (var4 == -1) {
                     try {
                        if (var9.b == var9.c) {
                           var1.a = var9.a();
                           q.L(var9);
                        }
                     } catch (AssertionError var28) {
                        var10000 = var28;
                        boolean var46 = false;
                        break label177;
                     }

                     return -1L;
                  } else {
                     label190: {
                        try {
                           var9.c += var4;
                           var6 = var1.b;
                        } catch (AssertionError var30) {
                           var10000 = var30;
                           boolean var47 = false;
                           break label190;
                        }

                        var2 = (long)var4;

                        try {
                           var1.b = var6 + var2;
                           return var2;
                        } catch (AssertionError var29) {
                           var10000 = var29;
                           boolean var48 = false;
                        }
                     }
                  }
               }

               AssertionError var32 = var10000;
               var8 = l.a;
               boolean var41 = var5;
               if (var32.getCause() != null) {
                  var41 = var5;
                  if (var32.getMessage() != null) {
                     var41 = var5;
                     if (var32.getMessage().contains("getsockname failed")) {
                        var41 = true;
                     }
                  }
               }

               if (var41) {
                  throw new IOException(var32);
               } else {
                  throw var32;
               }
            } else {
               return var6;
            }
      }
   }
}
