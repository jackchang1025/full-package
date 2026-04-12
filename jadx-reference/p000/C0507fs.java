package p000;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import com.storm.safe.rock.manager.C0258a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fs */
/* loaded from: classes2.dex */
public final class C0507fs extends CameraCaptureSession.StateCallback {

    /* renamed from: a0 */
    public final /* synthetic */ CameraDevice f56315a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0258a0 f56316a1;

    /* renamed from: a2 */
    public final /* synthetic */ CaptureRequest.Builder f56317a2;

    public C0507fs(CameraDevice cameraDevice, C0258a0 c0258a0, CaptureRequest.Builder builder) {
        this.f56315a0 = cameraDevice;
        this.f56316a1 = c0258a0;
        this.f56317a2 = builder;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
        t60.m214695b6(cameraCaptureSession, "session");
        t60.m214704c5("CameraManager", "摄像头会话配置失败");
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigured(CameraCaptureSession cameraCaptureSession) throws CameraAccessException {
        t60.m214695b6(cameraCaptureSession, "session");
        if (this.f56315a0 == null) {
            return;
        }
        C0258a0 c0258a0 = this.f56316a1;
        c0258a0.f52069a2 = cameraCaptureSession;
        try {
            CaptureRequest captureRequestBuild = this.f56317a2.build();
            t60.m214694b5(captureRequestBuild, "captureRequestBuilder.build()");
            cameraCaptureSession.setRepeatingRequest(captureRequestBuild, null, c0258a0.f52071a4);
        } catch (CameraAccessException e) {
            t60.m214705c6("CameraManager", "创建捕获请求失败", e);
        }
    }
}
