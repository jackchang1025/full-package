package h0;

import f0.t;
import java.util.concurrent.CancellationException;

// $VF: synthetic class
public final class f implements g, b {
   public final h d;
   public final h e;

   @Override
   public final void a(Exception var1, Object var2) {
      CancellationException var3;
      if (this.d.g(var1, var2, null)) {
         var3 = null;
      } else {
         var3 = new CancellationException();
      }

      this.e.g(var3, null, null);
   }

   @Override
   public final void b(Exception var1, Object var2, t var3) {
      CancellationException var4;
      if (this.d.g(var1, var2, var3)) {
         var4 = null;
      } else {
         var4 = new CancellationException();
      }

      this.e.g(var4, var2, var3);
   }
}
