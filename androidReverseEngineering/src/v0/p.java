package v0;

import java.io.IOException;

public final class p extends q0.a {
   public final boolean b;
   public final int c;
   public final int d;
   public final s e;

   public p(s var1, int var2, int var3) {
      super(new Object[]{var1.d, var2, var3}, "OkHttp %s ping %08x%08x");
      this.e = var1;
      this.b = true;
      this.c = var2;
      this.d = var3;
   }

   @Override
   public final void a() {
      int var1 = this.c;
      int var2 = this.d;
      boolean var3 = this.b;
      s var4 = this.e;
      var4.getClass();

      try {
         var4.u.B(var3, var1, var2);
      } catch (IOException var6) {
         var4.y(var6);
      }
   }
}
