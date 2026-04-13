package r;

public enum c {
   b,
   c,
   d;

   public static final c[] e;
   public final int a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      c var1 = new c(0);
      b = var1;
      c var2 = new c(1);
      c = var2;
      c var0 = new c(2);
      d = var0;
      e = new c[]{var1, var2, var0};
   }

   public c(int var3) {
      this.a = var3;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.a);
      var1.append(" ");
      var1.append(this.name());
      return var1.toString();
   }
}
