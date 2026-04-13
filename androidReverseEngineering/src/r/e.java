package r;

public enum e {
   b,
   c,
   d;

   public static final e[] e;
   public final int a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      e var2 = new e(0);
      b = var2;
      e var0 = new e(1);
      c = var0;
      e var1 = new e(2);
      d = var1;
      e = new e[]{var2, var0, var1};
   }

   public e(int var3) {
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
