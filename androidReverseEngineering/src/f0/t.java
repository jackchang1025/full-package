package f0;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class t implements g0.b, g0.c, g0.a {
   public final int d;
   public Object e;
   public Object f;
   public Object g;

   public t() {
      this.d = 0;
      super();
      this.e = new m();
      this.f = null;
   }

   public t(int var1) {
      this.d = var1;
      if (var1 != 2) {
         if (var1 != 5) {
            this();
         } else {
            this(UUID.randomUUID().toString());
         }
      } else {
         super();
      }
   }

   public t(b0.b var1, m0.a var2, m var3) {
      this.d = 4;
      this.g = var1;
      this.f = var2;
      this.e = var3;
      super();
   }

   public t(com.guard.wallet.thread.j var1, m var2, l0.g var3) {
      this.d = 3;
      this.g = var1;
      this.e = var2;
      this.f = var3;
      super();
   }

   public t(String var1) {
      this.d = 5;
      super();
      this.e = p0.z.s;
      this.g = new ArrayList();
      this.f = a1.h.d(var1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void a(Exception var1) {
      switch (this.d) {
         case 3:
            Exception var10000;
            label32:
            if (var1 == null) {
               try {
                  com.guard.wallet.thread.j var2 = (com.guard.wallet.thread.j)this.g;
                  m var3 = (m)this.e;
                  String var7 = var3.h(null);
                  var3.k();
                  var2.e = i0.e.c(var7, "&", false, i0.e.b);
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var10001 = false;
                  break label32;
               }

               ((g0.a)this.f).a(null);
               return;
            } else {
               try {
                  throw var1;
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var9 = false;
               }
            }

            var1 = var10000;
            ((g0.a)this.f).a(var1);
            return;
         default:
            if (var1 == null) {
               try {
                  ((h0.h)this.f).g(null, (m)this.e, null);
                  return;
               } catch (Exception var6) {
                  var1 = var6;
               }
            }

            ((h0.h)this.f).g(var1, null, null);
      }
   }

   @Override
   public final void b(o var1, m var2) {
      ByteBuffer var5 = ByteBuffer.allocate(var2.c);

      while (var2.c > 0) {
         byte var3 = var2.i(1).get();
         var2.c--;
         if (var3 == 10) {
            ((Buffer)var5).flip();
            ((m)this.e).a(var5);
            s var4 = (s)this.g;
            m var6 = (m)this.e;
            String var7 = var6.h((Charset)this.f);
            var6.k();
            var4.c(var7);
            this.e = new m();
            return;
         }

         var5.put(var3);
      }

      ((Buffer)var5).flip();
      ((m)this.e).a(var5);
   }

   @Override
   public final void c() {
      ((p)this.f).c((m)this.e);
      if (((m)this.e).c == 0 && (g0.a)this.g != null) {
         ((p)this.f).d(null);
         ((g0.a)this.g).a(null);
      }
   }

   public final void d(String var1, a1.q var2) {
      StringBuilder var3 = new StringBuilder("form-data; name=");
      p0.z.W(var3, "files");
      if (var1 != null) {
         var3.append("; filename=");
         p0.z.W(var3, var1);
      }

      p0.f var4 = new p0.f();
      String var7 = var3.toString();
      p0.s.a("Content-Disposition");
      var4.a("Content-Disposition", var7);
      p0.s var5 = new p0.s(var4);
      if (var5.c("Content-Type") == null) {
         if (var5.c("Content-Length") == null) {
            p0.y var6 = new p0.y(var5, var2);
            ((List)this.g).add(var6);
         } else {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
         }
      } else {
         throw new IllegalArgumentException("Unexpected header: Content-Type");
      }
   }

   public final p0.z e() {
      if (!((List)this.g).isEmpty()) {
         return new p0.z((a1.h)this.f, (p0.x)this.e, (List)this.g);
      } else {
         throw new IllegalStateException("Multipart body must have at least one part.");
      }
   }

   public final void f(p0.x var1) {
      if (var1 != null) {
         if (var1.b.equals("multipart")) {
            this.e = var1;
         } else {
            StringBuilder var2 = new StringBuilder("multipart != ");
            var2.append(var1);
            throw new IllegalArgumentException(var2.toString());
         }
      } else {
         throw new NullPointerException("type == null");
      }
   }
}
