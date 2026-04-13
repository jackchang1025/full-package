package f0;

import android.util.Log;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public final class b implements k {
   public a0 d;
   public SelectionKey e;
   public j f;
   public final m g = new m();
   public n0.a h;
   public boolean i;
   public g0.c j;
   public g0.b k;
   public g0.a l;
   public boolean m;
   public Exception n;
   public g0.a o;
   public boolean p = false;

   public final void a() {
      m var6 = this.g;
      int var1 = var6.c;
      boolean var2 = true;
      boolean var13;
      if (var1 > 0) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      if (var13) {
         a1.q.p(this, var6);
      }

      if (!this.p) {
         n0.a var7 = this.h;
         ByteBuffer var16 = f0.m.g(Math.min(Math.max(var7.b, 4096), var7.a));

         long var4;
         label52: {
            try {
               var13 = this.d.read(var16);
            } catch (Exception var12) {
               this.e.cancel();

               try {
                  this.d.close();
               } catch (IOException var10) {
               }

               this.o(var12);
               this.n(var12);
               var4 = -1L;
               break label52;
            }

            var4 = (long)var13;
         }

         int var3;
         long var17;
         var3 = (var17 = var4 - 0L) == 0L ? 0 : (var17 < 0L ? -1 : 1);
         label48:
         if (var3 < 0) {
            this.e.cancel();

            try {
               this.d.close();
            } catch (IOException var11) {
               var15 = var2;
               break label48;
            }

            var15 = var2;
         } else {
            var15 = false;
         }

         if (var3 > 0) {
            this.h.b = (int)var4 * 2;
            ((Buffer)var16).flip();
            var6.a(var16);
            a1.q.p(this, var6);
         } else {
            f0.m.j(var16);
         }

         if (var15) {
            this.o(null);
            this.n(null);
         }
      }
   }

   @Override
   public final j b() {
      return this.f;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void c(m var1) {
      if (this.f.e != Thread.currentThread()) {
         this.f.e(new o.d(this, var1, 9));
      } else {
         a0 var5 = this.d;
         int var3 = var5.b;
         int var2 = 0;
         boolean var4;
         switch (var3) {
            case 0:
               var4 = false;
               break;
            default:
               var4 = ((SocketChannel)var5.c).isConnected();
         }

         if (var4) {
            IOException var10000;
            label97: {
               label105: {
                  a0 var27;
                  try {
                     var3 = var1.c;
                     n0.c var6 = var1.a;
                     var24 = (ByteBuffer[])var6.toArray(new ByteBuffer[var6.size()]);
                     var6.clear();
                     var1.c = 0;
                     var27 = this.d;
                     switch (var27.b) {
                        case 0:
                           break label105;
                     }
                  } catch (IOException var16) {
                     var10000 = var16;
                     boolean var10001 = false;
                     break label97;
                  }

                  try {
                     ((SocketChannel)var27.c).write(var24);
                     var3 = var24.length;
                  } catch (IOException var15) {
                     var10000 = var15;
                     boolean var28 = false;
                     break label97;
                  }

                  for (; var2 < var3; var2++) {
                     try {
                        var1.a(var24[var2]);
                     } catch (IOException var14) {
                        var10000 = var14;
                        boolean var29 = false;
                        break label97;
                     }
                  }

                  label107: {
                     try {
                        var2 = var1.c;
                        if (!this.e.isValid()) {
                           break label107;
                        }
                     } catch (IOException var13) {
                        var10000 = var13;
                        boolean var30 = false;
                        break label97;
                     }

                     SelectionKey var17;
                     if (var2 > 0) {
                        try {
                           var17 = this.e;
                           var2 = var17.interestOps() | 4;
                        } catch (IOException var12) {
                           var10000 = var12;
                           boolean var31 = false;
                           break label97;
                        }
                     } else {
                        try {
                           var17 = this.e;
                           var2 = var17.interestOps() & -5;
                        } catch (IOException var11) {
                           var10000 = var11;
                           boolean var32 = false;
                           break label97;
                        }
                     }

                     try {
                        var17.interestOps(var2);
                        this.f.getClass();
                        return;
                     } catch (IOException var10) {
                        var10000 = var10;
                        boolean var33 = false;
                        break label97;
                     }
                  }

                  try {
                     CancelledKeyException var25 = new CancelledKeyException();
                     IOException var18 = new IOException(var25);
                     throw var18;
                  } catch (IOException var9) {
                     var10000 = var9;
                     boolean var34 = false;
                     break label97;
                  }
               }

               try {
                  IOException var19 = new IOException("Can't write ServerSocketChannel");
                  throw var19;
               } catch (IOException var8) {
                  var10000 = var8;
                  boolean var35 = false;
               }
            }

            IOException var26 = var10000;
            this.e.cancel();

            try {
               this.d.close();
            } catch (IOException var7) {
            }

            this.o(var26);
            this.n(var26);
         }
      }
   }

   @Override
   public final void close() {
      this.e.cancel();

      try {
         this.d.close();
      } catch (IOException var2) {
      }

      this.n(null);
   }

   @Override
   public final void d(g0.c var1) {
      this.j = var1;
   }

   @Override
   public final boolean e() {
      return this.p;
   }

   @Override
   public final void f(g0.a var1) {
      this.l = var1;
   }

   @Override
   public final String g() {
      return "UTF-8";
   }

   @Override
   public final void h(g0.b var1) {
      this.k = var1;
   }

   @Override
   public final g0.c i() {
      return this.j;
   }

   @Override
   public final void j(g0.a var1) {
      this.o = var1;
   }

   @Override
   public final g0.b k() {
      return this.k;
   }

   @Override
   public final void l() {
      a0 var1 = this.d;
      switch (var1.b) {
         default:
            try {
               ((SocketChannel)var1.c).socket().shutdownOutput();
            } catch (Exception var2) {
            }
         case 0:
      }
   }

   public final void m() {
      if (this.f.e != Thread.currentThread()) {
         this.f.e(new a(this, 0));
      } else if (!this.p) {
         this.p = true;

         try {
            SelectionKey var1 = this.e;
            var1.interestOps(var1.interestOps() & -2);
         } catch (Exception var2) {
         }
      }
   }

   public final void n(Exception var1) {
      if (!this.i) {
         this.i = true;
         g0.a var2 = this.l;
         if (var2 != null) {
            var2.a(var1);
            this.l = null;
         }
      }
   }

   public final void o(Exception var1) {
      boolean var2;
      if (this.g.c > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         this.n = var1;
      } else {
         if (!this.m) {
            this.m = true;
            g0.a var3 = this.o;
            if (var3 != null) {
               var3.a(var1);
            } else if (var1 != null) {
               Log.e("NIO", "Unhandled exception", var1);
            }
         }
      }
   }

   public final void p() {
      e var5 = this.f.e;
      Thread var4 = Thread.currentThread();
      boolean var2 = true;
      if (var5 != var4) {
         this.f.e(new a(this, 1));
      } else if (this.p) {
         this.p = false;

         try {
            SelectionKey var8 = this.e;
            var8.interestOps(var8.interestOps() | 1);
         } catch (Exception var6) {
         }

         m var9 = this.g;
         boolean var1;
         if (var9.c > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            a1.q.p(this, var9);
         }

         a0 var10 = this.d;
         boolean var3;
         switch (var10.b) {
            case 0:
               var3 = false;
               break;
            default:
               var3 = ((SocketChannel)var10.c).isConnected();
         }

         if (var3 && this.e.isValid()) {
            var1 = var2;
         } else {
            var1 = false;
         }

         if (!var1) {
            this.o(this.n);
         }
      }
   }
}
