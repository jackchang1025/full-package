package s0;

import a1.s;
import a1.v;
import java.io.IOException;
import java.net.ProtocolException;

public final class c implements s {
   public final s a;
   public boolean b;
   public final long c;
   public long d;
   public boolean e;
   public final e f;

   public c(e var1, s var2, long var3) {
      this.f = var1;
      if (var2 != null) {
         this.a = var2;
         this.c = var3;
      } else {
         throw new IllegalArgumentException("delegate == null");
      }
   }

   public final String A() {
      StringBuilder var1 = new StringBuilder();
      var1.append(c.class.getSimpleName());
      var1.append("(");
      var1.append(this.a.toString());
      var1.append(")");
      return var1.toString();
   }

   @Override
   public final v a() {
      return this.a.a();
   }

   @Override
   public final void close() {
      if (!this.e) {
         this.e = true;
         long var1 = this.c;
         if (var1 != -1L && this.d != var1) {
            throw new ProtocolException("unexpected end of stream");
         } else {
            try {
               this.x();
               this.y(null);
            } catch (IOException var4) {
               throw this.y(var4);
            }
         }
      }
   }

   @Override
   public final void flush() {
      try {
         this.z();
      } catch (IOException var2) {
         throw this.y(var2);
      }
   }

   @Override
   public final void i(a1.e var1, long var2) {
      if (!this.e) {
         long var4 = this.c;
         if (var4 != -1L && this.d + var2 > var4) {
            StringBuilder var7 = new StringBuilder("expected ");
            var7.append(var4);
            var7.append(" bytes but received ");
            var7.append(this.d + var2);
            throw new ProtocolException(var7.toString());
         } else {
            try {
               this.a.i(var1, var2);
               this.d += var2;
            } catch (IOException var6) {
               throw this.y(var6);
            }
         }
      } else {
         throw new IllegalStateException("closed");
      }
   }

   public final void x() {
      this.a.close();
   }

   public final IOException y(IOException var1) {
      if (this.b) {
         return var1;
      } else {
         this.b = true;
         e var2 = this.f;
         if (var1 != null) {
            var2.c(var1);
         }

         var2.b.getClass();
         return var2.a.c(var2, true, false, var1);
      }
   }

   public final void z() {
      this.a.flush();
   }
}
