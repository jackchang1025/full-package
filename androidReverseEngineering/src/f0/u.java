package f0;

public final class u extends x {
   public final Object b;

   public u(int var1, v var2) {
      super(var1);
      if (var1 > 0) {
         this.b = var2;
      } else {
         throw new IllegalArgumentException("length should be > 0");
      }
   }

   @Override
   public final x a(o var1, m var2) {
      byte[] var3 = new byte[super.a];
      var2.e(var3);
      ((v)this.b).c(var3);
      return null;
   }
}
