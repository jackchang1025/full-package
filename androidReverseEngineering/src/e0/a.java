package e0;

import a1.q;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.util.Objects;

public final class a extends LinearLayout {
   public a(Activity var1, String var2) {
      super(var1);
      LayoutParams var3 = new LayoutParams(-1, -1);
      var3.gravity = 1;
      this.setOrientation(1);
      this.setGravity(17);
      this.setLayoutParams(var3);
      c var5 = new c(var1);
      if (!var5.a()) {
         var5.setImageURL(com.guard.wallet.utils.d.b());
      }

      var5.setTag("waiting-icon-image");
      this.addView(var5, 800, 160);
      if (!q.B(var2)) {
         TextView var4 = new TextView(var1);
         var4.setTag("waiting-hint-text");
         var4.setText(var2);
         var4.setSingleLine(false);
         var4.setTextColor(-1);
         var4.setBackgroundColor(0);
         var4.setTextAlignment(5);
         var4.setGravity(8388611);
         var4.setTextSize(2, 16.0F);
         var4.setTypeface(Typeface.defaultFromStyle(1), 1);
         this.addView(var4, 800, 260);
      }
   }

   public final void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      var5 = this.getChildCount();
      if (var5 > 0) {
         for (int var8 = 0; var8 < var5; var8++) {
            View var7 = this.getChildAt(var8);
            if (Objects.equals(var7.getTag(), "waiting-icon-image")) {
               int var6 = var4 - var2;
               var6 = (var6 - (int)((float)var6 * 0.4F)) / 2;
               var7.layout(var6, 5, var4 - var6, 165);
            }

            if (Objects.equals(var7.getTag(), "waiting-hint-text")) {
               int var11 = (var4 - var2 - 800) / 2;
               var7.layout(var11, 180, var4 - var11, 440);
            }
         }
      }
   }
}
