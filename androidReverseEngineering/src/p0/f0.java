package p0;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class f0 {
   public final u a;
   public final String b;
   public final s c;
   public final a1.q d;
   public final Map e;
   public volatile d f;

   public f0(l0.m var1) {
      this.a = (u)var1.b;
      this.b = var1.a;
      f var2 = (f)var1.c;
      var2.getClass();
      this.c = new s(var2);
      this.d = (a1.q)var1.d;
      Map var3 = (Map)var1.e;
      byte[] var5 = q0.c.a;
      Map var4;
      if (var3.isEmpty()) {
         var4 = Collections.emptyMap();
      } else {
         var4 = Collections.unmodifiableMap(new LinkedHashMap(var3));
      }

      this.e = var4;
   }

   public final String a(String var1) {
      return this.c.c(var1);
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("Request{method=");
      var1.append(this.b);
      var1.append(", url=");
      var1.append(this.a);
      var1.append(", tags=");
      var1.append(this.e);
      var1.append('}');
      return var1.toString();
   }
}
