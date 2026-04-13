package com.guard.wallet.plug;

import com.guard.wallet.req.ListenPropResponse;
import java.util.Objects;
import java.util.function.Predicate;

public final class e implements Predicate {
   public final int a;

   public e(int var1) {
      this.a = var1;
   }

   @Override
   public final boolean test(Object var1) {
      return Objects.equals(((ListenPropResponse)var1).getTargetIndex(), this.a);
   }
}
