package u0;

public final class f extends a {
   public boolean d;

   public f(g var1) {
      super(var1);
   }

   @Override
   public final void close() {
      if (!super.b) {
         if (!this.d) {
            this.x();
         }

         super.b = true;
      }
   }

   @Override
   public final long u(a1.e var1, long var2) {
      if (var2 >= 0L) {
         if (!super.b) {
            if (this.d) {
               return -1L;
            } else {
               var2 = super.u(var1, var2);
               if (var2 == -1L) {
                  this.d = true;
                  this.x();
                  return -1L;
               } else {
                  return var2;
               }
            }
         } else {
            throw new IllegalStateException("closed");
         }
      } else {
         StringBuilder var4 = new StringBuilder("byteCount < 0: ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString());
      }
   }
}
