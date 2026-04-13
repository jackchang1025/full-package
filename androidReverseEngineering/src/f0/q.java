package f0;

public abstract class q implements g0.b, o {
   public boolean d;
   public g0.a e;
   public g0.b f;
   public o g;
   public boolean h;

   @Override
   public void b(o var1, m var2) {
      if (this.h) {
         var2.k();
      } else {
         a1.q.p(this, var2);
      }
   }

   public void c(Exception var1) {
      if (!this.d) {
         this.d = true;
         g0.a var2 = this.e;
         if (var2 != null) {
            var2.a(var1);
         }
      }
   }

   @Override
   public final void close() {
      this.h = true;
      o var1 = this.g;
      if (var1 != null) {
         var1.close();
      }
   }

   @Override
   public boolean e() {
      return this.g.e();
   }

   @Override
   public final String g() {
      o var1 = this.g;
      return var1 == null ? null : var1.g();
   }

   @Override
   public void h(g0.b var1) {
      this.f = var1;
   }

   public final void i(o var1) {
      o var2 = this.g;
      if (var2 != null) {
         var2.h(null);
      }

      this.g = var1;
      var1.h(this);
      this.g.j(new com.guard.wallet.http.h(this, 3));
   }

   @Override
   public final void j(g0.a var1) {
      this.e = var1;
   }

   @Override
   public g0.b k() {
      return this.f;
   }
}
