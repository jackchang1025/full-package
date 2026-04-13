package v0;

public final class a0 {
   public final int a;
   public final int b;
   public final Object c;

   public a0() {
      this.c = new a0[256];
      this.a = 0;
      this.b = 0;
   }

   public a0(int var1, int var2) {
      this.c = null;
      this.a = var1;
      var2 &= 7;
      var1 = var2;
      if (var2 == 0) {
         var1 = 8;
      }

      this.b = var1;
   }
}
