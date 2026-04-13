package com.guard.wallet.camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraCaptureSession.StateCallback;

/**
 * SessionStateCallback -- 会话就绪后启动重复采集。
 * vendor 原始路径: m/b.java
 */
public final class CameraSessionCallback extends StateCallback {
   public static final int b = 0;
   public final CaptureRequest a;

   public CameraSessionCallback(CaptureRequest var1) {
      this.a = var1;
   }

   public final void onConfigureFailed(CameraCaptureSession var1) {
   }

   public final void onConfigured(CameraCaptureSession var1) {
      try {
         CameraCaptureManager.c().d = var1;
         CaptureRequest var3 = this.a;
         CameraCaptureCallback var2 = new CameraCaptureCallback();
         var1.setRepeatingRequest(var3, var2, null);
      } catch (CameraAccessException var4) {
         throw new RuntimeException(var4);
      }
   }
}
