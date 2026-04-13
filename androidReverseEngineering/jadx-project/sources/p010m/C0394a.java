package p010m;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import android.view.Surface;

/* renamed from: m.a */
/* loaded from: classes.dex */
public final class C0394a extends CameraCaptureSession.CaptureCallback {
    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureBufferLost(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Surface surface, long j2) {
        super.onCaptureBufferLost(cameraCaptureSession, captureRequest, surface, j2);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
        super.onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
        super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
        int i2 = C0395b.f790b;
        Log.d("m.b", captureFailure.toString());
        C0397d.m963c().m966e();
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureProgressed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
        super.onCaptureProgressed(cameraCaptureSession, captureRequest, captureResult);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i2) {
        super.onCaptureSequenceAborted(cameraCaptureSession, i2);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i2, long j2) {
        super.onCaptureSequenceCompleted(cameraCaptureSession, i2, j2);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j2, long j3) {
        super.onCaptureStarted(cameraCaptureSession, captureRequest, j2, j3);
    }

    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
    public final void onReadoutStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j2, long j3) {
        super.onReadoutStarted(cameraCaptureSession, captureRequest, j2, j3);
    }
}
