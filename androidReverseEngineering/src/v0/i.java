package v0;

import java.io.IOException;

public final class i extends q0.a {
   public final int b;
   public final long c;
   public final s d;

   public i(s var1, Object[] var2, int var3, long var4) {
      super(var2, "OkHttp Window Update %s stream %d");
      this.d = var1;
      this.b = var3;
      this.c = var4;
   }

   @Override
   public final void a() {
      s var2 = this.d;

      try {
         var2.u.D(this.b, this.c);
      } catch (IOException var3) {
         var2.y(var3);
      }
   }
}
