package o0;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

public final class a implements AnimatorUpdateListener {
   public final f a;
   public final float b;
   public final float c;
   public final float d;
   public final float e;
   public final h f;

   public a(h var1, f var2, float var3, float var4, float var5, float var6) {
      this.f = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.d = var5;
      this.e = var6;
   }

   public final void onAnimationUpdate(ValueAnimator var1) {
      float var2 = (Float)var1.getAnimatedValue();
      float var3 = 1.0F - var2;
      float var4 = this.b;
      float var5 = this.c;
      f var6 = this.a;
      var6.c = var5 * var2 + var4 * var3;
      var4 = this.d;
      var6.d = var2 * this.e + var3 * var4;
      this.f.invalidate();
   }
}
