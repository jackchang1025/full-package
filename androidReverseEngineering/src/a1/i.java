package a1;

import java.util.concurrent.TimeUnit;

public final class i extends v {
   public v e;

   public i(v var1) {
      if (var1 != null) {
         this.e = var1;
      } else {
         throw new IllegalArgumentException("delegate == null");
      }
   }

   @Override
   public final v a() {
      return this.e.a();
   }

   @Override
   public final v b() {
      return this.e.b();
   }

   @Override
   public final long c() {
      return this.e.c();
   }

   @Override
   public final v d(long var1) {
      return this.e.d(var1);
   }

   @Override
   public final boolean e() {
      return this.e.e();
   }

   @Override
   public final void f() {
      this.e.f();
   }

   @Override
   public final v g(long var1, TimeUnit var3) {
      return this.e.g(var1, var3);
   }
}
