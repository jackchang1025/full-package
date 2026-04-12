package p000;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.util.Size;
import android.view.Surface;
import com.storm.safe.rock.manager.C0258a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ft */
/* loaded from: classes2.dex */
public final class C0508ft extends CameraDevice.StateCallback {

    /* renamed from: a0 */
    public final /* synthetic */ C0258a0 f56329a0;

    public C0508ft(C0258a0 c0258a0) {
        this.f56329a0 = c0258a0;
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onDisconnected(CameraDevice cameraDevice) {
        t60.m214695b6(cameraDevice, "camera");
        this.f56329a0.f52076a9.release();
        cameraDevice.close();
        C0258a0 c0258a0 = this.f56329a0;
        c0258a0.f52067a0 = null;
        c0258a0.f52081b4 = false;
        this.f56329a0.f52080b3 = false;
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onError(CameraDevice cameraDevice, int i) {
        t60.m214695b6(cameraDevice, "camera");
        this.f56329a0.f52076a9.release();
        cameraDevice.close();
        C0258a0 c0258a0 = this.f56329a0;
        c0258a0.f52067a0 = null;
        c0258a0.f52081b4 = false;
        this.f56329a0.f52080b3 = false;
        t60.m214704c5("CameraManager", "摄像头错误: " + i);
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onOpened(CameraDevice cameraDevice) throws CameraAccessException {
        t60.m214695b6(cameraDevice, "camera");
        this.f56329a0.f52076a9.release();
        C0258a0 c0258a0 = this.f56329a0;
        c0258a0.f52067a0 = cameraDevice;
        c0258a0.f52081b4 = true;
        this.f56329a0.f52080b3 = false;
        C0258a0 c0258a02 = this.f56329a0;
        try {
            if (!c0258a02.f52081b4) {
                t60.m214726f4("CameraManager", "⚠️ 摄像头已关闭，跳过创建会话");
                return;
            }
            CameraDevice cameraDevice2 = c0258a02.f52067a0;
            if (cameraDevice2 == null) {
                t60.m214726f4("CameraManager", "⚠️ CameraDevice 为 null，跳过创建会话");
                return;
            }
            String str = c0258a02.f52073a6;
            if (str == null) {
                t60.m214704c5("CameraManager", "当前摄像头ID为null");
                return;
            }
            CameraCharacteristics cameraCharacteristics = c0258a02.f52072a5.getCameraCharacteristics(str);
            t60.m214694b5(cameraCharacteristics, "cameraManager.getCameraC…eristics(currentCameraId)");
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size sizeM211241a1 = C0258a0.m211241a1(streamConfigurationMap != null ? streamConfigurationMap.getOutputSizes(35) : null);
            c0258a02.f52083b6 = sizeM211241a1.getWidth();
            int height = sizeM211241a1.getHeight();
            c0258a02.f52084b7 = height;
            t60.m214714d6("CameraManager", "📷 摄像头预览尺寸: " + c0258a02.f52083b6 + "x" + height);
            ImageReader imageReaderNewInstance = ImageReader.newInstance(c0258a02.f52083b6, c0258a02.f52084b7, 35, 6);
            imageReaderNewInstance.setOnImageAvailableListener(c0258a02.f52093c6, c0258a02.f52071a4);
            c0258a02.f52068a1 = imageReaderNewInstance;
            Surface surface = imageReaderNewInstance.getSurface();
            CaptureRequest.Builder builderCreateCaptureRequest = cameraDevice2.createCaptureRequest(1);
            t60.m214694b5(builderCreateCaptureRequest, "cameraDevice.createCaptu…aDevice.TEMPLATE_PREVIEW)");
            builderCreateCaptureRequest.addTarget(surface);
            cameraDevice2.createCaptureSession(AbstractC1117qo.m214451e7(surface), new C0507fs(cameraDevice2, c0258a02, builderCreateCaptureRequest), c0258a02.f52071a4);
        } catch (CameraAccessException e) {
            t60.m214705c6("CameraManager", "创建摄像头预览会话失败", e);
        }
    }
}
