package p010m;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;

/* renamed from: m.b */
/* loaded from: classes.dex */
public final class C0395b extends CameraCaptureSession.StateCallback {

    /* renamed from: b */
    public static final /* synthetic */ int f790b = 0;

    /* renamed from: a */
    public final CaptureRequest f791a;

    public C0395b(CaptureRequest captureRequest) {
        this.f791a = captureRequest;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigured(CameraCaptureSession cameraCaptureSession) {
        try {
            C0397d.m963c().f800d = cameraCaptureSession;
            cameraCaptureSession.setRepeatingRequest(this.f791a, new C0394a(), null);
        } catch (CameraAccessException e2) {
            throw new RuntimeException(e2);
        }
    }
}
