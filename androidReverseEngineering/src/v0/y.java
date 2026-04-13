package v0;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayDeque;

public final class y {
   public long a = 0L;
   public long b;
   public final int c;
   public final s d;
   public final ArrayDeque e;
   public boolean f;
   public final x g;
   public final w h;
   public final s0.j i;
   public final s0.j j;
   public b k;
   public IOException l;

   public y(int var1, s var2, boolean var3, boolean var4, p0.s var5) {
      ArrayDeque var6 = new ArrayDeque();
      this.e = var6;
      this.i = new s0.j(this, 1);
      this.j = new s0.j(this, 1);
      if (var2 != null) {
         this.c = var1;
         this.d = var2;
         this.b = (long)var2.s.d();
         x var8 = new x(this, (long)var2.r.d());
         this.g = var8;
         w var7 = new w(this);
         this.h = var7;
         var8.e = var4;
         var7.c = var3;
         if (var5 != null) {
            var6.add(var5);
         }

         if (this.f() && var5 != null) {
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
         } else if (!this.f() && var5 == null) {
            throw new IllegalStateException("remotely-initiated streams should have headers");
         }
      } else {
         throw new NullPointerException("connection == null");
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void a() {
      synchronized (this){} // $VF: monitorenter 

      boolean var1;
      boolean var2;
      label155: {
         Throwable var10000;
         label150: {
            label149: {
               label148: {
                  try {
                     x var3 = this.g;
                     if (!var3.e && var3.d) {
                        w var16 = this.h;
                        if (var16.c || var16.b) {
                           break label148;
                        }
                     }
                  } catch (Throwable var15) {
                     var10000 = var15;
                     boolean var10001 = false;
                     break label150;
                  }

                  var1 = false;
                  break label149;
               }

               var1 = true;
            }

            label136:
            try {
               var2 = this.g();
               // $VF: monitorexit
               break label155;
            } catch (Throwable var14) {
               var10000 = var14;
               boolean var18 = false;
               break label136;
            }
         }

         while (true) {
            Throwable var17 = var10000;

            try {
               // $VF: monitorexit
               throw var17;
            } catch (Throwable var13) {
               var10000 = var13;
               boolean var19 = false;
               continue;
            }
         }
      }

      if (var1) {
         this.c(v0.b.g, null);
      } else if (!var2) {
         this.d.B(this.c);
      }
   }

   public final void b() {
      w var1 = this.h;
      if (!var1.b) {
         if (!var1.c) {
            if (this.k != null) {
               var1 = this.l;
               if (var1 == null) {
                  var1 = new d0(this.k);
               }

               throw var1;
            }
         } else {
            throw new IOException("stream finished");
         }
      } else {
         throw new IOException("stream closed");
      }
   }

   public final void c(b var1, IOException var2) {
      if (this.d(var1, var2)) {
         this.d.u.C(this.c, var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean d(b var1, IOException var2) {
      synchronized (this){} // $VF: monitorenter 

      label161: {
         Throwable var10000;
         label162: {
            try {
               if (this.k != null) {
                  // $VF: monitorexit
                  return false;
               }
            } catch (Throwable var22) {
               var10000 = var22;
               boolean var10001 = false;
               break label162;
            }

            try {
               if (this.g.e && this.h.c) {
                  // $VF: monitorexit
                  return false;
               }
            } catch (Throwable var21) {
               var10000 = var21;
               boolean var24 = false;
               break label162;
            }

            label150:
            try {
               this.k = var1;
               this.l = var2;
               this.notifyAll();
               // $VF: monitorexit
               break label161;
            } catch (Throwable var20) {
               var10000 = var20;
               boolean var25 = false;
               break label150;
            }
         }

         while (true) {
            Throwable var23 = var10000;

            try {
               // $VF: monitorexit
               throw var23;
            } catch (Throwable var19) {
               var10000 = var19;
               boolean var26 = false;
               continue;
            }
         }
      }

      this.d.B(this.c);
      return true;
   }

   public final void e(b var1) {
      if (this.d(var1, null)) {
         this.d.F(this.c, var1);
      }
   }

   public final boolean f() {
      int var1 = this.c;
      boolean var3 = true;
      boolean var2;
      if ((var1 & 1) == 1) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (this.d.a == var2) {
         var2 = var3;
      } else {
         var2 = false;
      }

      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean g() {
      synchronized (this){} // $VF: monitorenter 

      label173: {
         boolean var1;
         label172: {
            Throwable var10000;
            label177: {
               b var2;
               try {
                  var2 = this.k;
               } catch (Throwable var22) {
                  var10000 = var22;
                  boolean var10001 = false;
                  break label177;
               }

               if (var2 != null) {
                  // $VF: monitorexit
                  return false;
               }

               try {
                  x var23 = this.g;
                  if (!var23.e && !var23.d) {
                     break label173;
                  }
               } catch (Throwable var21) {
                  var10000 = var21;
                  boolean var26 = false;
                  break label177;
               }

               try {
                  w var24 = this.h;
                  if (!var24.c && !var24.b) {
                     break label173;
                  }
               } catch (Throwable var20) {
                  var10000 = var20;
                  boolean var27 = false;
                  break label177;
               }

               label161:
               try {
                  var1 = this.f;
                  break label172;
               } catch (Throwable var19) {
                  var10000 = var19;
                  boolean var28 = false;
                  break label161;
               }
            }

            Throwable var25 = var10000;
            // $VF: monitorexit
            throw var25;
         }

         if (var1) {
            // $VF: monitorexit
            return false;
         }
      }

      // $VF: monitorexit
      return true;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void h(p0.s var1, boolean var2) {
      synchronized (this){} // $VF: monitorenter 

      label266: {
         Throwable var10000;
         label267: {
            label260: {
               label259: {
                  try {
                     if (!this.f) {
                        break label259;
                     }
                  } catch (Throwable var44) {
                     var10000 = var44;
                     boolean var10001 = false;
                     break label267;
                  }

                  if (var2) {
                     try {
                        this.g.getClass();
                        break label260;
                     } catch (Throwable var43) {
                        var10000 = var43;
                        boolean var47 = false;
                        break label267;
                     }
                  }
               }

               try {
                  this.f = true;
                  this.e.add(var1);
               } catch (Throwable var42) {
                  var10000 = var42;
                  boolean var48 = false;
                  break label267;
               }
            }

            if (var2) {
               try {
                  this.g.e = true;
               } catch (Throwable var41) {
                  var10000 = var41;
                  boolean var49 = false;
                  break label267;
               }
            }

            label246:
            try {
               var2 = this.g();
               this.notifyAll();
               // $VF: monitorexit
               break label266;
            } catch (Throwable var40) {
               var10000 = var40;
               boolean var50 = false;
               break label246;
            }
         }

         while (true) {
            Throwable var45 = var10000;

            try {
               // $VF: monitorexit
               throw var45;
            } catch (Throwable var39) {
               var10000 = var39;
               boolean var51 = false;
               continue;
            }
         }
      }

      if (!var2) {
         this.d.B(this.c);
      }
   }

   public final void i() {
      try {
         this.wait();
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
         throw new InterruptedIOException();
      }
   }
}
