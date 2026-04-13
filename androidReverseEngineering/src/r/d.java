package r;

public enum d {
   b,
   c,
   d;

   public static final d[] e;
   public final int a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      d var2 = new d(-1);
      b = var2;
      d var0 = new d(0);
      c = var0;
      d var1 = new d(1);
      d var3 = new d(2);
      d = var3;
      e = new d[]{var2, var0, var1, var3, new d(3)};
   }

   public d(int var3) {
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
