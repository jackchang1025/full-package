package k0;

import f0.m;
import f0.o;
import f0.q;

public final class c extends q {
   public final long i;
   public long j;
   public final m k = new m();

   public c(long var1) {
      this.i = var1;
   }

   @Override
   public final void b(o var1, m var2) {
      int var3 = var2.c;
      long var6 = this.j;
      long var4 = this.i;
      var3 = (int)Math.min(var4 - var6, (long)var3);
      m var8 = this.k;
      var2.d(var8, var3);
      var3 = var8.c;
      super.b(var1, var8);
      this.j = this.j + (long)(var3 - var8.c);
      var8.c(var2);
      if (this.j == var4) {
         this.c(null);
      }
   }

   @Override
   public final void c(Exception var1) {
      Object var6 = var1;
      if (var1 == null) {
         long var2 = this.j;
         long var4 = this.i;
         var6 = var1;
         if (var2 != var4) {
            StringBuilder var7 = new StringBuilder("End of data reached before content length was read: ");
            var7.append(this.j);
            var7.append("/");
            var7.append(var4);
            var7.append(" Paused: ");
            var7.append(this.e());
            var6 = new i0.b(var7.toString());
         }
      }

      super.c((Exception)var6);
   }
}
