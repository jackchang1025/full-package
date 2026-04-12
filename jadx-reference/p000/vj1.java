package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityService$TakeScreenshotCallback;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.hardware.HardwareBuffer;
import com.storm.safe.rock.manager.C0263a5;
import java.util.concurrent.CountDownLatch;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class vj1 implements AccessibilityService$TakeScreenshotCallback {

    /* renamed from: a0 */
    public final /* synthetic */ Ref$ObjectRef f60646a0;

    /* renamed from: a1 */
    public final /* synthetic */ CountDownLatch f60647a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0263a5 f60648a2;

    public vj1(Ref$ObjectRef ref$ObjectRef, CountDownLatch countDownLatch, C0263a5 c0263a5) {
        this.f60646a0 = ref$ObjectRef;
        this.f60647a1 = countDownLatch;
        this.f60648a2 = c0263a5;
    }

    public final void onFailure(int i) {
        long j;
        if (i == 1) {
            C0263a5 c0263a5 = this.f60648a2;
            long j2 = c0263a5.f52157a6 + 20;
            j = C0263a5.f52144b0.isVivoDevice() ? 600L : 500L;
            if (j2 > j) {
                j2 = j;
            }
            c0263a5.f52157a6 = j2;
        } else if (i != 3) {
            t60.m214726f4("etzbzyzqxvqm", "截图失败: errorCode=" + i);
        } else {
            C0263a5 c0263a52 = this.f60648a2;
            long j3 = c0263a52.f52157a6 + 20;
            j = C0263a5.f52144b0.isVivoDevice() ? 600L : 500L;
            if (j3 > j) {
                j3 = j;
            }
            c0263a52.f52157a6 = j3;
        }
        this.f60647a1.countDown();
    }

    public final void onSuccess(AccessibilityService.ScreenshotResult screenshotResult) {
        t60.m214695b6(screenshotResult, "result");
        try {
            try {
                HardwareBuffer hardwareBuffer = screenshotResult.getHardwareBuffer();
                t60.m214694b5(hardwareBuffer, "result.hardwareBuffer");
                ColorSpace colorSpace = screenshotResult.getColorSpace();
                t60.m214694b5(colorSpace, "result.colorSpace");
                Ref$ObjectRef ref$ObjectRef = this.f60646a0;
                Bitmap bitmapWrapHardwareBuffer = Bitmap.wrapHardwareBuffer(hardwareBuffer, colorSpace);
                ref$ObjectRef.f57626a0 = bitmapWrapHardwareBuffer != null ? bitmapWrapHardwareBuffer.copy(Bitmap.Config.ARGB_8888, false) : null;
                hardwareBuffer.close();
                if (C0263a5.f52145b1 == null) {
                    uj1 uj1Var = C0263a5.f52144b0;
                    C0263a5.f52145b1 = Boolean.TRUE;
                    t60.m214714d6("etzbzyzqxvqm", "✅ TakeScreenshotCallback API 可用");
                }
            } catch (Exception e) {
                t60.m214705c6("etzbzyzqxvqm", "处理截图结果失败", e);
            }
            this.f60647a1.countDown();
        } catch (Throwable th) {
            this.f60647a1.countDown();
            throw th;
        }
    }
}
