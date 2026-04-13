package o0;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

public final class c implements AnimatorUpdateListener {
   public final f a;
   public final h b;

   public c(h var1, f var2) {
      this.b = var1;
      this.a = var2;
   }

   public final void onAnimationUpdate(ValueAnimator var1) {
      float var2 = (Float)var1.getAnimatedValue();
      this.a.a = var2;
      this.b.invalidate();
   }
}
