package l1;

import java.util.TreeMap;

public abstract class e implements b {
   public final TreeMap a = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

   public final String a(String var1) {
      String var2 = (String)this.a.get(var1);
      var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public final void b(String var1, String var2) {
      this.a.put(var1, var2);
   }
}
