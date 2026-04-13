package v0;

public final class c {
   public static final a1.h d = a1.h.d(":");
   public static final a1.h e = a1.h.d(":status");
   public static final a1.h f = a1.h.d(":method");
   public static final a1.h g = a1.h.d(":path");
   public static final a1.h h = a1.h.d(":scheme");
   public static final a1.h i = a1.h.d(":authority");
   public final a1.h a;
   public final a1.h b;
   public final int c;

   public c(a1.h var1, a1.h var2) {
      this.a = var1;
      this.b = var2;
      int var3 = var1.j();
      this.c = var2.j() + var3 + 32;
   }

   public c(a1.h var1, String var2) {
      this(var1, a1.h.d(var2));
   }

   public c(String var1, String var2) {
      this(a1.h.d(var1), a1.h.d(var2));
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var4 = var1 instanceof c;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4) {
         var1 = var1;
         a1.h var5 = var1.a;
         var2 = var3;
         if (this.a.equals(var5)) {
            var2 = var3;
            if (this.b.equals(var1.b)) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   @Override
   public final int hashCode() {
      int var1 = this.a.hashCode();
      return this.b.hashCode() + (var1 + 527) * 31;
   }

   @Override
   public final String toString() {
      return q0.c.i(new Object[]{this.a.m(), this.b.m()}, "%s: %s");
   }
}
