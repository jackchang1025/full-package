package e0;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import com.guard.wallet.service.MyAccessibilityService;

public final class f extends View implements Callback {
   public final Handler a;
   public Paint b;
   public Paint c;
   public RectF d;
   public RectF e;
   public int f = 0;
   public int g = 380;
   public int h = 14;

   public f(MyAccessibilityService var1) {
      super(var1);
      Paint var2 = new Paint();
      this.b = var2;
      var2.setColor(-1);
      this.b.setAntiAlias(true);
      this.b.setStyle(Style.FILL);
      this.b.setStrokeWidth(0.0F);
      Paint var3 = new Paint();
      this.c = var3;
      var3.setColor(Color.parseColor("#1677ff"));
      this.c.setAntiAlias(true);
      this.c.setStyle(Style.FILL);
      this.c.setStrokeWidth(0.0F);
      this.a = new Handler(Looper.getMainLooper(), this);
   }

   public final boolean handleMessage(Message var1) {
      int var2 = var1.what;
      if (var2 > 0 && var2 > this.f && var2 <= 100) {
         this.f = var2;
         this.invalidate();
      }

      return false;
   }

   public final void onDraw(Canvas var1) {
      if (this.d == null) {
         RectF var7 = new RectF();
         this.d = var7;
         var7.set(0.0F, 0.0F, (float)this.g, (float)this.h);
      }

      if (this.e == null) {
         this.e = new RectF();
      }

      float var4 = (float)this.f / 100.0F;
      RectF var8 = this.d;
      float var3 = var8.left;
      float var5 = var8.top;
      float var6 = var8.right;
      float var2 = var8.bottom;
      this.e.set(var3, var5, (var6 - var3) * var4 + var3, var2);
      var1.drawRoundRect(this.d, 100.0F, 100.0F, this.b);
      var1.drawRoundRect(this.e, 100.0F, 100.0F, this.c);
   }

   public final void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      if (this.d == null) {
         RectF var8 = new RectF();
         this.d = var8;
         var2 = var4 - var2;
         var4 = var5 - var3;
         var3 = (int)((float)var2 * 0.4F);
         this.g = var3;
         float var7 = (float)(var2 - var3) / 2.0F;
         var2 = this.h;
         float var6;
         if (var4 >= var2) {
            var6 = (float)(var4 - var2) / 2.0F;
         } else {
            this.h = var4;
            var6 = 0.0F;
         }

         var8.set(var7, var6, (float)var3 + var7, (float)this.h + var6);
      }
   }

   public final void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
   }
}
