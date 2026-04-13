package p010m;

import a1.AbstractC0026q;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import java.util.Collections;
import java.util.Objects;

/* renamed from: m.e */
/* loaded from: classes.dex */
public final class C0398e extends CameraDevice.StateCallback {

    /* renamed from: a */
    public final Surface f802a;

    public C0398e(Surface surface) {
        this.f802a = surface;
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onDisconnected(CameraDevice cameraDevice) {
        C0397d.m963c().m966e();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onError(CameraDevice cameraDevice, int i2) {
        C0397d.m963c().m966e();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onOpened(CameraDevice cameraDevice) {
        Surface surface;
        CaptureRequest.Key key;
        int i2;
        C0397d.m963c().f799c = cameraDevice;
        int i3 = AbstractC0026q.f65k;
        if (cameraDevice == null || (surface = this.f802a) == null) {
            return;
        }
        try {
            CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(1);
            createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, 4);
            C0396c c0396c = C0397d.m963c().f801e;
            if (Objects.equals(Integer.valueOf(c0396c != null ? c0396c.f793b : -1), 0)) {
                key = CaptureRequest.JPEG_ORIENTATION;
                i2 = 270;
            } else {
                key = CaptureRequest.JPEG_ORIENTATION;
                i2 = 90;
            }
            createCaptureRequest.set(key, i2);
            createCaptureRequest.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface), new C0395b(createCaptureRequest.build()), new Handler(Looper.getMainLooper()));
        } catch (Exception e2) {
            AbstractC0026q.m186s("a1.q", e2);
        }
    }
}
