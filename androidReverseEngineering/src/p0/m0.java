package p0;

import java.net.InetSocketAddress;
import java.net.Proxy;

public final class m0 {
   public final a a;
   public final Proxy b;
   public final InetSocketAddress c;

   public m0(a var1, Proxy var2, InetSocketAddress var3) {
      if (var1 != null) {
         if (var3 != null) {
            this.a = var1;
            this.b = var2;
            this.c = var3;
         } else {
            throw new NullPointerException("inetSocketAddress == null");
         }
      } else {
         throw new NullPointerException("address == null");
      }
   }

   @Override
   public final boolean equals(Object var1) {
      if (var1 instanceof m0) {
         var1 = var1;
         if (var1.a.equals(this.a) && var1.b.equals(this.b) && var1.c.equals(this.c)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public final int hashCode() {
      int var2 = this.a.hashCode();
      int var1 = this.b.hashCode();
      return this.c.hashCode() + (var1 + (var2 + 527) * 31) * 31;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("Route{");
      var1.append(this.c);
      var1.append("}");
      return var1.toString();
   }
}
