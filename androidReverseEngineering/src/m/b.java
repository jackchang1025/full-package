package m;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraCaptureSession.StateCallback;

public final class b extends StateCallback {
   public static final int b = 0;
   public final CaptureRequest a;

   public b(CaptureRequest var1) {
      this.a = var1;
   }

   public final void onConfigureFailed(CameraCaptureSession var1) {
   }

   public final void onConfigured(CameraCaptureSession var1) {
      try {
         d.c().d = var1;
         CaptureRequest var3 = this.a;
         a var2 = new a();
         var1.setRepeatingRequest(var3, var2, null);
      } catch (CameraAccessException var4) {
         throw new RuntimeException(var4);
      }
   }
}
