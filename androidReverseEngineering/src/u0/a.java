package u0;

import a1.i;
import a1.t;
import a1.v;
import java.io.IOException;

public abstract class a implements t {
   public final i a;
   public boolean b;
   public final g c;

   public a(g var1) {
      this.c = var1;
      this.a = new i(var1.c.a());
   }

   @Override
   public final v a() {
      return this.a;
   }

   @Override
   public long u(a1.e var1, long var2) {
      g var4 = this.c;

      try {
         return var4.c.u(var1, var2);
      } catch (IOException var5) {
         var4.b.h();
         this.x();
         throw var5;
      }
   }

   public final void x() {
      g var2 = this.c;
      int var1 = var2.e;
      if (var1 != 6) {
         if (var1 == 5) {
            i var5 = this.a;
            v var4 = var5.e;
            var5.e = v.d;
            var4.a();
            var4.b();
            var2.e = 6;
         } else {
            StringBuilder var3 = new StringBuilder("state: ");
            var3.append(var2.e);
            throw new IllegalStateException(var3.toString());
         }
      }
   }
}
