package v0;

public enum b {
   b,
   c,
   d,
   e,
   f,
   g;

   public static final b[] h;
   public final int a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      b var0 = new b(0);
      b = var0;
      b var1 = new b(1);
      c = var1;
      b var5 = new b(2);
      d = var5;
      b var4 = new b(3);
      e = var4;
      b var2 = new b(7);
      f = var2;
      b var3 = new b(8);
      g = var3;
      h = new b[]{var0, var1, var5, var4, var2, var3, new b(9), new b(10), new b(11), new b(12), new b(13)};
   }

   public b(int var3) {
      this.a = var3;
   }
}
