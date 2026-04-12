package p000;

import android.view.Display;
import android.view.OrientationEventListener;
import com.storm.safe.rock.service.MediaDisplayService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class we0 extends OrientationEventListener {

    /* renamed from: a0 */
    public final /* synthetic */ MediaDisplayService f60896a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public we0(MediaDisplayService mediaDisplayService) {
        super(mediaDisplayService);
        this.f60896a0 = mediaDisplayService;
    }

    @Override // android.view.OrientationEventListener
    public final void onOrientationChanged(int i) {
        MediaDisplayService mediaDisplayService = this.f60896a0;
        Display display = mediaDisplayService.f52313a4;
        int rotation = display != null ? display.getRotation() : 0;
        if (rotation != mediaDisplayService.f52319b0) {
            mediaDisplayService.f52319b0 = rotation;
            try {
                mediaDisplayService.m211391a4();
            } catch (Exception e) {
                t60.m214705c6("ScreenProjectionSvc", "❌ [旋转] VirtualDisplay 重建失败", e);
            }
        }
    }
}
