package com.guard.wallet.camera;

import com.guard.wallet.core.AppUtils;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * CameraDevice.StateCallback -- 设备打开后配置采集请求。
 * vendor 原始路径: m/e.java
 */
public final class CameraDeviceCallback extends StateCallback {
    public final Surface a;

    public CameraDeviceCallback(Surface var1) {
        this.a = var1;
    }

    @Override
    public final void onDisconnected(CameraDevice var1) {
        CameraCaptureManager.c().e();
    }

    @Override
    public final void onError(CameraDevice var1, int var2) {
        CameraCaptureManager.c().e();
    }

    @Override
    public final void onOpened(CameraDevice var1) {
        CameraCaptureManager.c().c = var1;
        int facing = AppUtils.k;
        if (var1 != null) {
            Surface surface = this.a;
            if (surface != null) {
                try {
                    CaptureRequest.Builder builder = var1.createCaptureRequest(1);
                    builder.set(CaptureRequest.CONTROL_AF_MODE, 4);

                    CameraInfo cameraInfo = CameraCaptureManager.c().e;
                    if (cameraInfo != null) {
                        facing = cameraInfo.b;
                    } else {
                        facing = -1;
                    }

                    int jpegOrientation;
                    if (Objects.equals(facing, 0)) {
                        jpegOrientation = 270;
                    } else {
                        jpegOrientation = 90;
                    }

                    builder.set(CaptureRequest.JPEG_ORIENTATION, jpegOrientation);
                    builder.addTarget(surface);
                    List<Surface> surfaces = Collections.singletonList(surface);
                    CameraSessionCallback sessionCallback = new CameraSessionCallback(builder.build());
                    Handler handler = new Handler(Looper.getMainLooper());
                    var1.createCaptureSession(surfaces, sessionCallback, handler);
                    return;
                } catch (Exception ex) {
                    AppUtils.s("AppUtils", ex);
                }
            }
        }
    }
}
