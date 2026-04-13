package o0;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

public final class b extends AnimatorListenerAdapter {
   public final int a;
   public final h b;
   public final Object c;

   public final void onAnimationEnd(Animator var1) {
      int var2 = this.a;
      f var3 = (f)this.c;
      switch (var2) {
         case 0:
            var3 = var3;
            var3.e = null;
            var3.b = true;
            var3.a = (float)this.b.m;
            return;
         default:
            Runnable var4 = (Runnable)var3;
            if (var4 != null) {
               var4.run();
            }
      }
   }
}
