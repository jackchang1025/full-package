package f;

import android.os.Bundle;

public final class c extends a {
   public final float b;

   public c(float var1) {
      super("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
      this.b = var1;
   }

   @Override
   public final void a(Bundle var1) {
      var1.putFloat(super.a, this.b);
   }
}
