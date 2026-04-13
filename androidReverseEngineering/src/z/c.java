package z;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilterWithChild;

public final class c implements a, b {
   public final int a;
   public final CombineFilterWithChild b;

   @Override
   public final int a() {
      return 20;
   }

   @Override
   public final UiObjectCollection b(UiObject var1) {
      int var2 = this.a;
      CombineFilterWithChild var3 = this.b;
      switch (var2) {
         case 0:
            return var1.findByCombineWithChild(var3);
         default:
            return var1.findByCombineWithoutChild(var3);
      }
   }

   @Override
   public final UiObject c(UiObject var1) {
      int var2 = this.a;
      CombineFilterWithChild var3 = this.b;
      switch (var2) {
         case 0:
            return var1.findOneByCombineWithChild(var3);
         default:
            return var1.findOneByCombineWithoutChild(var3);
      }
   }
}
