package p0;

public final class g0 extends a1.q {
   public final x o;
   public final int p;
   public final byte[] q;
   public final int r;

   public g0(int var1, x var2, byte[] var3) {
      this.o = var2;
      this.p = var1;
      this.q = var3;
      this.r = 0;
      super();
   }

   @Override
   public final void V(a1.f var1) {
      int var2 = this.p;
      var1.c(this.q, this.r, var2);
   }

   @Override
   public final long i() {
      return (long)this.p;
   }

   @Override
   public final x j() {
      return this.o;
   }
}
