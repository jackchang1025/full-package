package k1;

public final class a extends c {
   public final int i;

   public a(int var1) {
      this.i = var1;
      if (var1 != 1) {
         if (var1 != 2) {
            super(3, 1);
         } else {
            super(2, 1);
         }
      } else {
         super(1, 1);
      }
   }

   @Override
   public final void b() {
      switch (this.i) {
         case 2:
            super.b();
            if (o1.a.a(super.c)) {
               return;
            }

            throw new i1.c(1007, "Received text is no valid utf8 string!");
         default:
            super.b();
      }
   }
}
