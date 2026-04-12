package p000;

import com.storm.safe.rock.manager.C0263a5;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class uj1 {
    public /* synthetic */ uj1(AbstractC1120qr abstractC1120qr) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isVivoDevice() {
        return ((Boolean) C0263a5.f52146b2.getValue()).booleanValue();
    }

    public final String getCAPTURE_TECH_ACCESSIBILITY() {
        return C0263a5.f52150b6;
    }

    public final int getCaptureFps() {
        return C0263a5.f52147b3;
    }

    public final long getCaptureInterval() {
        return 1000 / AbstractC1117qo.m214413a9(getCaptureFps(), 5, 30);
    }

    public final int getCaptureQuality() {
        return C0263a5.f52148b4;
    }

    public final float getCaptureScale() {
        return C0263a5.f52149b5;
    }

    public final void resetScreenshotSupport() {
        if (t60.m214686a2(C0263a5.f52145b1, Boolean.FALSE)) {
            C0263a5.f52145b1 = null;
            t60.m214714d6("etzbzyzqxvqm", "🔄 已重置 takeScreenshotSupported，将重新检测");
        }
    }

    public final void setCaptureFps(int i) {
        C0263a5.f52147b3 = i;
    }

    public final void setCaptureQuality(int i) {
        C0263a5.f52148b4 = i;
    }

    public final void setCaptureScale(float f) {
        C0263a5.f52149b5 = f;
    }

    public final void setParams(int i, int i2, double d) {
        setCaptureQuality(AbstractC1117qo.m214413a9(i, 10, 100));
        setCaptureFps(AbstractC1117qo.m214413a9(i2, 5, 30));
        setCaptureScale(AbstractC1117qo.m214412a8((float) d, 0.25f, 1.0f));
    }

    private uj1() {
    }
}
