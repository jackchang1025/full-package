package i0;

import java.util.Hashtable;

public enum h {
   b;

   public static final Hashtable c;
   public static final h[] d;
   public final String a;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      h var0 = new h("http/1.0");
      h var1 = new h("http/1.1");
      b = var1;
      f var3 = new f();
      g var2 = new g();
      d = new h[]{var0, var1, var3, var2};
      Hashtable var4 = new Hashtable();
      c = var4;
      var4.put("http/1.0", var0);
      var4.put("http/1.1", var1);
      var4.put("spdy/3.1", var3);
      var4.put("h2-13", var2);
   }

   public h(String var3) {
      this.a = var3;
   }

   @Override
   public final String toString() {
      return this.a;
   }
}
