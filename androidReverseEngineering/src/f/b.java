package f;

import android.os.Bundle;

public final class b extends a {
   public final CharSequence b;

   public b(CharSequence var1) {
      super("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE");
      this.b = var1;
   }

   @Override
   public final void a(Bundle var1) {
      var1.putCharSequence(super.a, this.b);
   }
}
