package r;

public enum f {
   b,
   c,
   d,
   e,
   f,
   g,
   h,
   i,
   j,
   k,
   l,
   m;

   public static final f[] n;
   public final int a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      f var11 = new f(-1);
      b = var11;
      f var1 = new f(0);
      c = var1;
      f var9 = new f(1);
      d = var9;
      f var6 = new f(2);
      e = var6;
      f var4 = new f(3);
      f = var4;
      f var3 = new f(4);
      g = var3;
      f var8 = new f(5);
      h = var8;
      f var2 = new f(6);
      f var10 = new f(7);
      i = var10;
      f var7 = new f(8);
      j = var7;
      f var0 = new f(9);
      k = var0;
      f var5 = new f(10);
      l = var5;
      f var12 = new f(11);
      m = var12;
      n = new f[]{var11, var1, var9, var6, var4, var3, var8, var2, var10, var7, var0, var5, var12};
   }

   public f(int var3) {
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
