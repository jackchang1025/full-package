package f;

import android.os.Bundle;

public final class d extends a {
   public final int b;

   public d(String var1, int var2) {
      super(var1);
      this.b = var2;
   }

   @Override
   public final void a(Bundle var1) {
      var1.putInt(super.a, this.b);
   }
}
