package m;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.util.Log;
import android.view.Surface;

public final class a extends CaptureCallback {
   public final void onCaptureBufferLost(CameraCaptureSession var1, CaptureRequest var2, Surface var3, long var4) {
      super.onCaptureBufferLost(var1, var2, var3, var4);
   }

   public final void onCaptureCompleted(CameraCaptureSession var1, CaptureRequest var2, TotalCaptureResult var3) {
      super.onCaptureCompleted(var1, var2, var3);
   }

   public final void onCaptureFailed(CameraCaptureSession var1, CaptureRequest var2, CaptureFailure var3) {
      super.onCaptureFailed(var1, var2, var3);
      int var4 = b.b;
      Log.d("m.b", var3.toString());
      d.c().e();
   }

   public final void onCaptureProgressed(CameraCaptureSession var1, CaptureRequest var2, CaptureResult var3) {
      super.onCaptureProgressed(var1, var2, var3);
   }

   public final void onCaptureSequenceAborted(CameraCaptureSession var1, int var2) {
      super.onCaptureSequenceAborted(var1, var2);
   }

   public final void onCaptureSequenceCompleted(CameraCaptureSession var1, int var2, long var3) {
      super.onCaptureSequenceCompleted(var1, var2, var3);
   }

   public final void onCaptureStarted(CameraCaptureSession var1, CaptureRequest var2, long var3, long var5) {
      super.onCaptureStarted(var1, var2, var3, var5);
   }

   public final void onReadoutStarted(CameraCaptureSession var1, CaptureRequest var2, long var3, long var5) {
      super.onReadoutStarted(var1, var2, var3, var5);
   }
}
