package v0;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class z implements Closeable {
   public static final Logger g = Logger.getLogger(g.class.getName());
   public final a1.f a;
   public final boolean b;
   public final a1.e c;
   public int d;
   public boolean e;
   public final e f;

   public z(a1.f var1, boolean var2) {
      this.a = var1;
      this.b = var2;
      a1.e var3 = new a1.e();
      this.c = var3;
      this.f = new e(var3);
      this.d = 16384;
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void A(int var1, b var2, byte[] var3) {
      synchronized (this) {
         if (this.e) {
            IOException var7 = new IOException("closed");
            throw var7;
         }

         if (var2.a == -1) {
            a1.h var5 = v0.g.a;
            IllegalArgumentException var6 = new IllegalArgumentException(q0.c.i(new Object[0], "errorCode.httpCode == -1"));
            throw var6;
         }

         this.z(0, var3.length + 8, (byte)7, (byte)0);
         this.a.k(var1);
         this.a.k(var2.a);
         if (var3.length > 0) {
            this.a.p(var3);
         }

         this.a.flush();
      }

      // $VF: monitorexit
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void B(boolean var1, int var2, int var3) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label100: {
         label95: {
            try {
               if (!this.e) {
                  break label95;
               }
            } catch (Throwable var17) {
               var10000 = var17;
               boolean var10001 = false;
               break label100;
            }

            try {
               IOException var5 = new IOException("closed");
               throw var5;
            } catch (Throwable var16) {
               var10000 = var16;
               boolean var19 = false;
               break label100;
            }
         }

         byte var4;
         if (var1) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         try {
            this.z(0, 8, (byte)6, var4);
            this.a.k(var2);
            this.a.k(var3);
            this.a.flush();
         } catch (Throwable var15) {
            var10000 = var15;
            boolean var20 = false;
            break label100;
         }

         // $VF: monitorexit
         return;
      }

      Throwable var18 = var10000;
      // $VF: monitorexit
      throw var18;
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void C(int var1, b var2) {
      synchronized (this) {
         if (this.e) {
            IOException var5 = new IOException("closed");
            throw var5;
         }

         if (var2.a == -1) {
            IllegalArgumentException var4 = new IllegalArgumentException();
            throw var4;
         }

         this.z(var1, 4, (byte)3, (byte)0);
         this.a.k(var2.a);
         this.a.flush();
      }

      // $VF: monitorexit
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void D(int var1, long var2) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label143: {
         label138: {
            try {
               if (!this.e) {
                  break label138;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               boolean var10001 = false;
               break label143;
            }

            try {
               IOException var4 = new IOException("closed");
               throw var4;
            } catch (Throwable var23) {
               var10000 = var23;
               boolean var28 = false;
               break label143;
            }
         }

         label130:
         if (var2 != 0L && var2 <= 2147483647L) {
            try {
               this.z(var1, 4, (byte)8, (byte)0);
               this.a.k((int)var2);
               this.a.flush();
            } catch (Throwable var21) {
               var10000 = var21;
               boolean var30 = false;
               break label130;
            }

            // $VF: monitorexit
            return;
         } else {
            label127:
            try {
               a1.h var25 = v0.g.a;
               IllegalArgumentException var26 = new IllegalArgumentException(
                  q0.c.i(new Object[]{var2}, "windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s")
               );
               throw var26;
            } catch (Throwable var22) {
               var10000 = var22;
               boolean var29 = false;
               break label127;
            }
         }
      }

      Throwable var27 = var10000;
      // $VF: monitorexit
      throw var27;
   }

   public final void E(int var1, long var2) {
      while (var2 > 0L) {
         int var5 = (int)Math.min((long)this.d, var2);
         long var6 = (long)var5;
         var2 -= var6;
         byte var4;
         if (var2 == 0L) {
            var4 = 4;
         } else {
            var4 = 0;
         }

         this.z(var1, var5, (byte)9, var4);
         this.a.i(this.c, var6);
      }
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void close() {
      synchronized (this){} // $VF: monitorenter 

      try {
         this.e = true;
         this.a.close();
      } finally {
         // $VF: monitorexit
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void x(z.d var1) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label840: {
         int var2;
         int var3;
         label835: {
            try {
               if (!this.e) {
                  var2 = this.d;
                  var3 = var1.b;
                  break label835;
               }
            } catch (Throwable var186) {
               var10000 = var186;
               boolean var10001 = false;
               break label840;
            }

            try {
               IOException var187 = new IOException("closed");
               throw var187;
            } catch (Throwable var185) {
               var10000 = var185;
               boolean var194 = false;
               break label840;
            }
         }

         if ((var3 & 32) != 0) {
            try {
               var2 = ((int[])var1.c)[5];
            } catch (Throwable var184) {
               var10000 = var184;
               boolean var195 = false;
               break label840;
            }
         }

         try {
            this.d = var2;
         } catch (Throwable var183) {
            var10000 = var183;
            boolean var196 = false;
            break label840;
         }

         if ((var3 & 2) != 0) {
            try {
               var2 = ((int[])var1.c)[1];
            } catch (Throwable var182) {
               var10000 = var182;
               boolean var197 = false;
               break label840;
            }
         } else {
            var2 = -1;
         }

         if (var2 != -1) {
            e var4;
            try {
               var4 = this.f;
            } catch (Throwable var181) {
               var10000 = var181;
               boolean var198 = false;
               break label840;
            }

            if ((var3 & 2) != 0) {
               try {
                  var2 = ((int[])var1.c)[1];
               } catch (Throwable var180) {
                  var10000 = var180;
                  boolean var199 = false;
                  break label840;
               }
            } else {
               var2 = -1;
            }

            try {
               var4.getClass();
               var2 = Math.min(var2, 16384);
               var3 = var4.d;
            } catch (Throwable var179) {
               var10000 = var179;
               boolean var200 = false;
               break label840;
            }

            if (var3 != var2) {
               if (var2 < var3) {
                  try {
                     var4.b = Math.min(var4.b, var2);
                  } catch (Throwable var178) {
                     var10000 = var178;
                     boolean var201 = false;
                     break label840;
                  }
               }

               try {
                  var4.c = true;
                  var4.d = var2;
                  var3 = var4.h;
               } catch (Throwable var177) {
                  var10000 = var177;
                  boolean var202 = false;
                  break label840;
               }

               if (var2 < var3) {
                  if (var2 == 0) {
                     try {
                        Arrays.fill(var4.e, null);
                        var4.f = var4.e.length - 1;
                        var4.g = 0;
                        var4.h = 0;
                     } catch (Throwable var176) {
                        var10000 = var176;
                        boolean var203 = false;
                        break label840;
                     }
                  } else {
                     try {
                        var4.a(var3 - var2);
                     } catch (Throwable var175) {
                        var10000 = var175;
                        boolean var204 = false;
                        break label840;
                     }
                  }
               }
            }
         }

         try {
            this.z(0, 0, (byte)4, (byte)1);
            this.a.flush();
         } catch (Throwable var174) {
            var10000 = var174;
            boolean var205 = false;
            break label840;
         }

         // $VF: monitorexit
         return;
      }

      Throwable var188 = var10000;
      // $VF: monitorexit
      throw var188;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void y(boolean var1, int var2, a1.e var3, int var4) {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label145: {
         label140: {
            try {
               if (!this.e) {
                  break label140;
               }
            } catch (Throwable var25) {
               var10000 = var25;
               boolean var10001 = false;
               break label145;
            }

            try {
               IOException var26 = new IOException("closed");
               throw var26;
            } catch (Throwable var24) {
               var10000 = var24;
               boolean var28 = false;
               break label145;
            }
         }

         byte var5;
         if (var1) {
            var5 = (byte)1;
         } else {
            var5 = 0;
         }

         try {
            this.z(var2, var4, (byte)0, var5);
         } catch (Throwable var23) {
            var10000 = var23;
            boolean var29 = false;
            break label145;
         }

         if (var4 > 0) {
            try {
               this.a.i(var3, (long)var4);
            } catch (Throwable var22) {
               var10000 = var22;
               boolean var30 = false;
               break label145;
            }
         }

         // $VF: monitorexit
         return;
      }

      Throwable var27 = var10000;
      // $VF: monitorexit
      throw var27;
   }

   public final void z(int var1, int var2, byte var3, byte var4) {
      Level var7 = Level.FINE;
      Logger var6 = g;
      if (var6.isLoggable(var7)) {
         var6.fine(v0.g.a(false, var1, var2, var3, var4));
      }

      int var5 = this.d;
      if (var2 <= var5) {
         if ((-2147483648 & var1) == 0) {
            a1.f var10 = this.a;
            var10.o(var2 >>> 16 & 0xFF);
            var10.o(var2 >>> 8 & 0xFF);
            var10.o(var2 & 0xFF);
            var10.o(var3 & 255);
            var10.o(var4 & 255);
            var10.k(var1 & 2147483647);
         } else {
            a1.h var9 = v0.g.a;
            throw new IllegalArgumentException(q0.c.i(new Object[]{var1}, "reserved bit set: %s"));
         }
      } else {
         a1.h var8 = v0.g.a;
         throw new IllegalArgumentException(q0.c.i(new Object[]{var5, var2}, "FRAME_SIZE_ERROR length > %d: %d"));
      }
   }
}
