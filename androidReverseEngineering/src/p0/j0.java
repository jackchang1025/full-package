package p0;

import java.io.Closeable;

public final class j0 implements Closeable {
   public final f0 a;
   public final c0 b;
   public final int c;
   public final String d;
   public final r e;
   public final s f;
   public final l0 g;
   public final j0 h;
   public final j0 i;
   public final j0 j;
   public final long k;
   public final long l;
   public final s0.e m;

   public j0(i0 var1) {
      this.a = var1.a;
      this.b = var1.b;
      this.c = var1.c;
      this.d = var1.d;
      this.e = var1.e;
      f var2 = var1.f;
      var2.getClass();
      this.f = new s(var2);
      this.g = var1.g;
      this.h = var1.h;
      this.i = var1.i;
      this.j = var1.j;
      this.k = var1.k;
      this.l = var1.l;
      this.m = var1.m;
   }

   @Override
   public final void close() {
      l0 var1 = this.g;
      if (var1 != null) {
         var1.close();
      } else {
         throw new IllegalStateException("response is not eligible for a body and must not be closed");
      }
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("Response{protocol=");
      var1.append(this.b);
      var1.append(", code=");
      var1.append(this.c);
      var1.append(", message=");
      var1.append(this.d);
      var1.append(", url=");
      var1.append(this.a.a);
      var1.append('}');
      return var1.toString();
   }

   public final String x(String var1, String var2) {
      var1 = this.f.c(var1);
      if (var1 != null) {
         var2 = var1;
      }

      return var2;
   }
}
