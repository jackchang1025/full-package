package p0;

import java.io.IOException;

public enum c0 {
   b,
   c,
   d,
   e,
   f,
   g;

   public static final c0[] h;
   public final String a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      c0 var1 = new c0("http/1.0");
      b = var1;
      c0 var2 = new c0("http/1.1");
      c = var2;
      c0 var3 = new c0("spdy/3.1");
      d = var3;
      c0 var4 = new c0("h2");
      e = var4;
      c0 var5 = new c0("h2_prior_knowledge");
      f = var5;
      c0 var0 = new c0("quic");
      g = var0;
      h = new c0[]{var1, var2, var3, var4, var5, var0};
   }

   public c0(String var3) {
      this.a = var3;
   }

   public static c0 a(String var0) {
      if (var0.equals("http/1.0")) {
         return b;
      } else if (var0.equals("http/1.1")) {
         return c;
      } else if (var0.equals("h2_prior_knowledge")) {
         return f;
      } else if (var0.equals("h2")) {
         return e;
      } else if (var0.equals("spdy/3.1")) {
         return d;
      } else if (var0.equals("quic")) {
         return g;
      } else {
         throw new IOException("Unexpected protocol: ".concat(var0));
      }
   }

   @Override
   public final String toString() {
      return this.a;
   }
}
