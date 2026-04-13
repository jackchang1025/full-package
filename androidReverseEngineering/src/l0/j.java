package l0;

// $VF: synthetic class
public final class j implements g0.a {
   public final k d;
   public final boolean e;

   @Override
   public final void a(Exception var1) {
      k var2 = this.d;
      if (var1 != null) {
         var2.getClass();
         f0.k var6 = ((b)var2).o.x;
         b0.b var3 = new b0.b(24);
         f0.b var7 = (f0.b)var6;
         var7.k = var3;
         var7.o = new b0.b(23);
         var7.close();
      } else {
         f0.k var4 = var2.f;
         if (this.e) {
            k0.b var5 = new k0.b(var4);
            var5.g = 0;
            var2.i = var5;
         } else {
            var2.i = var4;
         }

         var2.i.f(var2.n);
         var2.n = null;
         var2.i.d(var2.j);
         var2.j = null;
         if (var2.k) {
            var2.l();
         } else {
            var2.b().c(new o.a(var2, 7));
         }
      }
   }
}
