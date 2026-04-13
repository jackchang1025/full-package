package e0;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.guard.wallet.utils.k;

public final class h implements OnGlobalLayoutListener {
   public final View a;

   public h(View var1) {
      this.a = var1;
   }

   public final void onGlobalLayout() {
      k.b(this.a);
   }
}
