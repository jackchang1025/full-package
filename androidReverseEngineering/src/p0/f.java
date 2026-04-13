package p0;

import java.util.ArrayList;

public final class f {
   public final ArrayList a = new ArrayList(20);

   public final void a(String var1, String var2) {
      ArrayList var3 = this.a;
      var3.add(var1);
      var3.add(var2.trim());
   }

   public final void b(String var1) {
      int var2 = 0;

      while (true) {
         ArrayList var4 = this.a;
         if (var2 >= var4.size()) {
            return;
         }

         int var3 = var2;
         if (var1.equalsIgnoreCase((String)var4.get(var2))) {
            var4.remove(var2);
            var4.remove(var2);
            var3 = var2 - 2;
         }

         var2 = var3 + 2;
      }
   }

   public final void c(String var1, String var2) {
      s.a(var1);
      s.b(var2, var1);
      this.b(var1);
      this.a(var1, var2);
   }
}
