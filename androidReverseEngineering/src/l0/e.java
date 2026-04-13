package l0;

import f0.t;

public final class e implements g0.a {
   public final f d;

   public e(f var1) {
      this.d = var1;
   }

   @Override
   public final void a(Exception var1) {
      g0.a var2 = this.d.d;
      if (var2 != null) {
         var2.a(var1);
      }
   }

   public final void b(f0.k var1) {
      d var4 = new d(this, var1);
      var4.k = var1;
      t var2 = new t(0);
      f0.b var3 = (f0.b)var4.k;
      var3.k = var2;
      var2.g = var4.m;
      var3.o = new b0.b(23);
      ((f0.b)var1).p();
   }
}
