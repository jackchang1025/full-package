package j;

public enum c {
   a,
   b,
   c,
   d;

   public static final c[] e;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      c var1 = new c();
      a = var1;
      c var0 = new c();
      b = var0;
      c var3 = new c();
      c = var3;
      c var2 = new c();
      d = var2;
      e = new c[]{var1, var0, var3, var2, new c(), new c()};
   }
}
