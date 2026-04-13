package com.guard.wallet.plug;

import com.guard.wallet.req.ListenPropResponse;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class b implements Predicate {
   public final int a;
   public final List b;
   public final List c;
   public final List d;
   public final Object e;

   public final void a(ListenPropResponse var1) {
      int var2 = this.a;
      List var4 = this.d;
      List var3 = this.c;
      List var5 = this.b;
      switch (var2) {
         case 0:
            if (Objects.equals(var1.getProp(), "text")) {
               var5.add(var1);
            }

            if (Objects.equals(var1.getProp(), "id")) {
               var3.add(var1);
            }

            if (Objects.equals(var1.getProp(), "desc")) {
               var4.add(var1);
            }

            return;
         default:
            if (Objects.equals(var1.getProp(), "text")) {
               var5.add(var1);
            }

            if (Objects.equals(var1.getProp(), "id")) {
               var3.add(var1);
            }

            if (Objects.equals(var1.getProp(), "desc")) {
               var4.add(var1);
            }
      }
   }
}
