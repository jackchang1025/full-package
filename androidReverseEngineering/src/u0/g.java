package u0;

import a1.s;
import a1.t;
import java.io.EOFException;
import java.io.IOException;
import java.net.Proxy.Type;
import l0.q;
import p0.b0;
import p0.c0;
import p0.f0;
import p0.i0;
import p0.j0;
import p0.u;

public final class g implements t0.b {
   public final b0 a;
   public final s0.g b;
   public final a1.g c;
   public final a1.f d;
   public int e = 0;
   public long f = 262144L;

   public g(b0 var1, s0.g var2, a1.g var3, a1.f var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   @Override
   public final t a(j0 var1) {
      if (!t0.e.b(var1)) {
         return this.i(0L);
      } else if ("chunked".equalsIgnoreCase(var1.x("Transfer-Encoding", null))) {
         u var5 = var1.a.a;
         if (this.e == 4) {
            this.e = 5;
            return new c(this, var5);
         } else {
            StringBuilder var6 = new StringBuilder("state: ");
            var6.append(this.e);
            throw new IllegalStateException(var6.toString());
         }
      } else {
         long var2 = t0.e.a(var1);
         if (var2 != -1L) {
            return this.i(var2);
         } else if (this.e == 4) {
            this.e = 5;
            this.b.h();
            return new f(this);
         } else {
            StringBuilder var4 = new StringBuilder("state: ");
            var4.append(this.e);
            throw new IllegalStateException(var4.toString());
         }
      }
   }

   @Override
   public final s b(f0 var1, long var2) {
      if ("chunked".equalsIgnoreCase(var1.a("Transfer-Encoding"))) {
         if (this.e == 1) {
            this.e = 2;
            return new b(this);
         } else {
            StringBuilder var5 = new StringBuilder("state: ");
            var5.append(this.e);
            throw new IllegalStateException(var5.toString());
         }
      } else if (var2 != -1L) {
         if (this.e == 1) {
            this.e = 2;
            return new e(this);
         } else {
            StringBuilder var4 = new StringBuilder("state: ");
            var4.append(this.e);
            throw new IllegalStateException(var4.toString());
         }
      } else {
         throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
      }
   }

   @Override
   public final void c() {
      this.d.flush();
   }

   @Override
   public final void cancel() {
      s0.g var1 = this.b;
      if (var1 != null) {
         q0.c.d(var1.d);
      }
   }

   @Override
   public final void d() {
      this.d.flush();
   }

   @Override
   public final void e(f0 var1) {
      Type var4 = this.b.c.b.type();
      StringBuilder var5 = new StringBuilder();
      var5.append(var1.b);
      var5.append(' ');
      u var3 = var1.a;
      boolean var2;
      if (!var3.a.equals("https") && var4 == Type.HTTP) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var5.append(var3);
      } else {
         var5.append(com.guard.wallet.utils.g.L0(var3));
      }

      var5.append(" HTTP/1.1");
      String var6 = var5.toString();
      this.l(var1.c, var6);
   }

   @Override
   public final long f(j0 var1) {
      if (!t0.e.b(var1)) {
         return 0L;
      } else {
         return "chunked".equalsIgnoreCase(var1.x("Transfer-Encoding", null)) ? -1L : t0.e.a(var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final i0 g(boolean var1) {
      int var2 = this.e;
      if (var2 != 1 && var2 != 3) {
         StringBuilder var12 = new StringBuilder("state: ");
         var12.append(this.e);
         throw new IllegalStateException(var12.toString());
      } else {
         EOFException var10000;
         label55: {
            q var3;
            try {
               var3 = q.a(this.j());
            } catch (EOFException var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label55;
            }

            var2 = var3.e;

            i0 var4;
            try {
               var4 = new i0();
               var4.b = (c0)var3.g;
               var4.c = var2;
               var4.d = var3.f;
               var4.f = this.k().e();
            } catch (EOFException var7) {
               var10000 = var7;
               boolean var14 = false;
               break label55;
            }

            if (var1 && var2 == 100) {
               return null;
            }

            if (var2 == 100) {
               try {
                  this.e = 3;
                  return var4;
               } catch (EOFException var5) {
                  var10000 = var5;
                  boolean var15 = false;
               }
            } else {
               try {
                  this.e = 4;
                  return var4;
               } catch (EOFException var6) {
                  var10000 = var6;
                  boolean var16 = false;
               }
            }
         }

         EOFException var13 = var10000;
         s0.g var10 = this.b;
         String var11;
         if (var10 != null) {
            var11 = var10.c.a.a.m();
         } else {
            var11 = "unknown";
         }

         throw new IOException(a.a.k("unexpected end of stream on ", var11), var13);
      }
   }

   @Override
   public final s0.g h() {
      return this.b;
   }

   public final d i(long var1) {
      if (this.e == 4) {
         this.e = 5;
         return new d(this, var1);
      } else {
         StringBuilder var3 = new StringBuilder("state: ");
         var3.append(this.e);
         throw new IllegalStateException(var3.toString());
      }
   }

   public final String j() {
      String var1 = this.c.q(this.f);
      this.f = this.f - (long)var1.length();
      return var1;
   }

   public final p0.s k() {
      p0.f var5 = new p0.f();

      while (true) {
         String var4 = this.j();
         if (var4.length() == 0) {
            return new p0.s(var5);
         }

         p0.q.c.getClass();
         int var1 = var4.indexOf(":", 1);
         String var2;
         String var3;
         if (var1 != -1) {
            var3 = var4.substring(0, var1);
            var2 = var4.substring(var1 + 1);
         } else {
            var2 = var4;
            if (var4.startsWith(":")) {
               var2 = var4.substring(1);
            }

            var3 = "";
         }

         var5.a(var3, var2);
      }
   }

   public final void l(p0.s var1, String var2) {
      if (this.e != 0) {
         StringBuilder var6 = new StringBuilder("state: ");
         var6.append(this.e);
         throw new IllegalStateException(var6.toString());
      } else {
         a1.f var5 = this.d;
         var5.s(var2).s("\r\n");
         int var4 = var1.a.length / 2;

         for (int var3 = 0; var3 < var4; var3++) {
            var5.s(var1.d(var3)).s(": ").s(var1.f(var3)).s("\r\n");
         }

         var5.s("\r\n");
         this.e = 1;
      }
   }
}
