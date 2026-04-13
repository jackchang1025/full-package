package com.guard.wallet.thread;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import java.util.concurrent.Callable;
import o.a0;

public final class g implements Callable {
   public final a0 a;
   public final boolean b;

   public g(boolean var1, a0 var2) {
      this.b = var1;
      this.a = var2;
   }

   @Override
   public final Object call() {
      byte var3 = 0;
      int var1 = 0;

      int var2;
      a0 var5;
      while (true) {
         boolean var4 = this.b;
         var5 = this.a;
         var2 = var3;
         if (!var4) {
            break;
         }

         var2 = var3;
         if (var1 >= 5) {
            break;
         }

         var2 = var3;
         if (!var5.M()) {
            break;
         }

         Log.e("com.guard.wallet.thread.g", "无线配对成功,仍然停留在配对对话框,等待自动关闭");
         var1++;
         com.guard.wallet.utils.g.T0(5);
      }

      while (var2 <= 5 && var5.M()) {
         Log.d("com.guard.wallet.thread.g", "无线配对已结束,等待5秒后,仍然停留在配对对话框");
         UiObject var6 = var5.k();
         CombineFilter var8 = new CombineFilter();
         StringCondition var7 = o.b.b(var8, a.a.c(var8, "className", "android.widget.Button"), "text");
         var7.setContains(com.guard.wallet.utils.f.b("PAIR_CANCEL_TEXT"));
         var8.getStringConditions().add(var7);
         var6 = var6.findOneByCombine(var8);
         if (var6 != null && var6.click()) {
            Log.d("com.guard.wallet.thread.g", "无线配对已结束,等待5秒后,仍然停留在配对对话框 已取消配对");
         }

         var2++;
         com.guard.wallet.utils.g.T0(5);
      }

      return var5.M() ^ true;
   }
}
