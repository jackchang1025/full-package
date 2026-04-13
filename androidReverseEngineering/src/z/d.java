package z;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import java.util.ArrayList;

public final class d implements a, b {
   public final int a;
   public int b;
   public final Object c;

   public d() {
      this.a = 3;
      super();
      this.c = new int[10];
   }

   public d(ArrayList var1) {
      this.a = 2;
      super();
      this.b = 0;
      this.c = var1;
   }

   @Override
   public final int a() {
      switch (this.a) {
         case 0:
            return this.b;
         default:
            return this.b;
      }
   }

   @Override
   public final UiObjectCollection b(UiObject var1) {
      int var2 = this.a;
      Object var3 = this.c;
      switch (var2) {
         case 0:
            return var1.findByCombine((CombineFilter)var3);
         default:
            return var1.findByOperateOr((CombineFiltersWithOr)var3);
      }
   }

   @Override
   public final UiObject c(UiObject var1) {
      int var2 = this.a;
      Object var3 = this.c;
      switch (var2) {
         case 0:
            return var1.findOneByCombine((CombineFilter)var3);
         default:
            return var1.findOneByOperateOr((CombineFiltersWithOr)var3);
      }
   }

   public final int d() {
      int var1;
      if ((this.b & 128) != 0) {
         var1 = ((int[])this.c)[7];
      } else {
         var1 = 65535;
      }

      return var1;
   }

   public final void e(int var1, int var2) {
      if (var1 >= 0) {
         int[] var3 = (int[])this.c;
         if (var1 < var3.length) {
            this.b |= 1 << var1;
            var3[var1] = var2;
         }
      }
   }
}
