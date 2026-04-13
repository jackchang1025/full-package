package p0;

public enum n0 {
   b,
   c,
   d,
   e,
   f;

   public static final n0[] g;
   public final String a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      n0 var1 = new n0("TLSv1.3");
      b = var1;
      n0 var3 = new n0("TLSv1.2");
      c = var3;
      n0 var2 = new n0("TLSv1.1");
      d = var2;
      n0 var4 = new n0("TLSv1");
      e = var4;
      n0 var0 = new n0("SSLv3");
      f = var0;
      g = new n0[]{var1, var3, var2, var4, var0};
   }

   public n0(String var3) {
      this.a = var3;
   }

   public static n0 a(String var0) {
      var0.getClass();
      int var2 = var0.hashCode();
      byte var1 = -1;
      switch (var2) {
         case -503070503:
            if (var0.equals("TLSv1.1")) {
               var1 = 0;
            }
            break;
         case -503070502:
            if (var0.equals("TLSv1.2")) {
               var1 = 1;
            }
            break;
         case -503070501:
            if (var0.equals("TLSv1.3")) {
               var1 = 2;
            }
            break;
         case 79201641:
            if (var0.equals("SSLv3")) {
               var1 = 3;
            }
            break;
         case 79923350:
            if (var0.equals("TLSv1")) {
               var1 = 4;
            }
      }

      switch (var1) {
         case 0:
            return d;
         case 1:
            return c;
         case 2:
            return b;
         case 3:
            return f;
         case 4:
            return e;
         default:
            throw new IllegalArgumentException("Unexpected TLS version: ".concat(var0));
      }
   }
}
