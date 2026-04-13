package r;

public enum g {
   b,
   c,
   d,
   e,
   f,
   g,
   h,
   i;

   public static final g[] j;
   public final int a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      g var0 = new g(0);
      b = var0;
      g var5 = new g(1);
      c = var5;
      g var2 = new g(2);
      d = var2;
      g var3 = new g(3);
      e = var3;
      g var4 = new g(4);
      f = var4;
      g var1 = new g(5);
      g = var1;
      g var6 = new g(6);
      h = var6;
      g var7 = new g(7);
      i = var7;
      j = new g[]{var0, var5, var2, var3, var4, var1, var6, var7};
   }

   public g(int var3) {
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
