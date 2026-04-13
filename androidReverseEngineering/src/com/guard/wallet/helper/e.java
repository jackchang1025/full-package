package com.guard.wallet.helper;

import android.util.Log;
import android.view.ViewTreeObserver.OnWindowAttachListener;
import java.util.concurrent.atomic.AtomicReference;

public final class e implements OnWindowAttachListener {
   public final void onWindowAttached() {
      AtomicReference var1 = g.a;
      Log.d("com.guard.wallet.helper.g", "BlockTextView 已显示至窗口");
      g.f.set(true);
   }

   public final void onWindowDetached() {
      AtomicReference var1 = g.a;
      Log.d("com.guard.wallet.helper.g", "BlockTextView 已从窗口移除");
      g.f.set(false);
   }
}
