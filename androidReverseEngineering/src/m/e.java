package m;

import a1.q;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureRequest.Key;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class e extends StateCallback {
   public final Surface a;

   public e(Surface var1) {
      this.a = var1;
   }

   public final void onDisconnected(CameraDevice var1) {
      d.c().e();
   }

   public final void onError(CameraDevice var1, int var2) {
      d.c().e();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onOpened(CameraDevice var1) {
      d.c().c = var1;
      int var2 = q.k;
      if (var1 != null) {
         Surface var6 = this.a;
         if (var6 != null) {
            Exception var10000;
            label57: {
               c var3;
               Builder var5;
               try {
                  var5 = var1.createCaptureRequest(1);
                  var5.set(CaptureRequest.CONTROL_AF_MODE, 4);
                  var3 = d.c().e;
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var10001 = false;
                  break label57;
               }

               if (var3 != null) {
                  try {
                     var2 = var3.b;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var18 = false;
                     break label57;
                  }
               } else {
                  var2 = -1;
               }

               Key var4;
               label41: {
                  try {
                     if (Objects.equals(var2, 0)) {
                        var4 = CaptureRequest.JPEG_ORIENTATION;
                        var14 = 270;
                        break label41;
                     }
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var19 = false;
                     break label57;
                  }

                  try {
                     var4 = CaptureRequest.JPEG_ORIENTATION;
                     var14 = 90;
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var20 = false;
                     break label57;
                  }
               }

               try {
                  var5.set(var4, var14);
                  var5.addTarget(var6);
                  List var15 = Collections.singletonList(var6);
                  b var16 = new b(var5.build());
                  Handler var17 = new Handler(Looper.getMainLooper());
                  var1.createCaptureSession(var15, var16, var17);
                  return;
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var21 = false;
               }
            }

            Exception var12 = var10000;
            q.s("a1.q", var12);
         }
      }
   }
}
