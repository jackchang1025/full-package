package f0;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public final class y implements g0.b {
   public final LinkedList d = new LinkedList();
   public final ByteOrder e;
   public final m f;

   static {
      new Hashtable();
   }

   public y(o var1) {
      new ArrayList();
      this.e = ByteOrder.BIG_ENDIAN;
      this.f = new m();
      var1.h(this);
   }

   @Override
   public final void b(o var1, m var2) {
      m var5 = this.f;
      var2.c(var5);

      while (true) {
         LinkedList var3 = this.d;
         if (var3.size() <= 0 || var5.c < ((x)var3.peek()).a) {
            if (var3.size() == 0) {
               var5.c(var2);
            }

            return;
         }

         var5.b = this.e;
         x var4 = ((x)var3.poll()).a(var1, var5);
         if (var4 != null) {
            var3.addFirst(var4);
         }
      }
   }
}
