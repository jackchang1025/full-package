package a1;

public final class p {
   public final byte[] a;
   public int b;
   public int c;
   public boolean d;
   public final boolean e;
   public p f;
   public p g;

   public p() {
      this.a = new byte[8192];
      this.e = true;
      this.d = false;
   }

   public p(byte[] var1, int var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = true;
      this.e = false;
   }

   public final p a() {
      p var2 = this.f;
      p var1;
      if (var2 != this) {
         var1 = var2;
      } else {
         var1 = null;
      }

      p var3 = this.g;
      var3.f = var2;
      this.f.g = var3;
      this.f = null;
      this.g = null;
      return var1;
   }

   public final void b(p var1) {
      var1.g = this;
      var1.f = this.f;
      this.f.g = var1;
      this.f = var1;
   }

   public final p c() {
      this.d = true;
      int var2 = this.b;
      int var1 = this.c;
      return new p(this.a, var2, var1);
   }

   public final void d(p var1, int var2) {
      if (var1.e) {
         int var3 = var1.c;
         byte[] var5 = var1.a;
         if (var3 + var2 > 8192) {
            if (var1.d) {
               throw new IllegalArgumentException();
            }

            int var4 = var1.b;
            if (var3 + var2 - var4 > 8192) {
               throw new IllegalArgumentException();
            }

            System.arraycopy(var5, var4, var5, 0, var3 - var4);
            var1.c = var1.c - var1.b;
            var1.b = 0;
         }

         int var7 = this.b;
         var3 = var1.c;
         System.arraycopy(this.a, var7, var5, var3, var2);
         var1.c += var2;
         this.b += var2;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
