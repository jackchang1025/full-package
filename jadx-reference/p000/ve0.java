package p000;

import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.service.C0281a1;
import com.storm.safe.rock.service.MediaDisplayService;
import java.util.ArrayList;
import kotlinx.coroutines.AbstractC0780a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ve0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f60621a0;

    /* renamed from: a1 */
    public final /* synthetic */ MediaDisplayService f60622a1;

    public /* synthetic */ ve0(MediaDisplayService mediaDisplayService, int i) {
        this.f60621a0 = i;
        this.f60622a1 = mediaDisplayService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaProjection mediaProjection;
        switch (this.f60621a0) {
            case 0:
                MediaDisplayService mediaDisplayService = this.f60622a1;
                MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
                try {
                    try {
                        AbstractC0780a0.m213689a0(mediaDisplayService.f52329c0.f58395a0);
                    } finally {
                        mediaDisplayService.f52322b3 = false;
                    }
                } catch (Exception unused) {
                }
                try {
                    Thread.sleep(100L);
                } catch (Exception unused2) {
                }
                try {
                    we0 we0Var = mediaDisplayService.f52320b1;
                    if (we0Var != null) {
                        we0Var.disable();
                    }
                    mediaDisplayService.f52320b1 = null;
                    try {
                        VirtualDisplay virtualDisplay = mediaDisplayService.f52312a3;
                        if (virtualDisplay != null) {
                            virtualDisplay.release();
                        }
                        mediaDisplayService.f52312a3 = null;
                    } catch (Exception unused3) {
                    }
                    try {
                        Thread.sleep(50L);
                    } catch (Exception unused4) {
                    }
                    try {
                        ImageReader imageReader = mediaDisplayService.f52311a2;
                        if (imageReader != null) {
                            imageReader.close();
                        }
                        mediaDisplayService.f52311a2 = null;
                    } catch (Exception unused5) {
                    }
                    try {
                        C0281a1 c0281a1 = mediaDisplayService.f52310a1;
                        if (c0281a1 != null && (mediaProjection = mediaDisplayService.f52309a0) != null) {
                            mediaProjection.unregisterCallback(c0281a1);
                        }
                        mediaDisplayService.f52310a1 = null;
                    } catch (Exception unused6) {
                    }
                    try {
                        MediaProjection mediaProjection2 = mediaDisplayService.f52309a0;
                        if (mediaProjection2 != null) {
                            mediaProjection2.stop();
                        }
                    } catch (Exception unused7) {
                    }
                    mediaDisplayService.f52309a0 = null;
                    try {
                        MediaProjection mediaProjection3 = AbstractC0241a0.f51906a0;
                        if (mediaProjection3 != null) {
                            mediaProjection3.stop();
                        }
                        AbstractC0241a0.f51906a0 = null;
                        AbstractC0241a0.f51907a1 = null;
                        AbstractC0241a0.f51908a2 = null;
                        AbstractC0241a0.f51909a3 = 0L;
                        AbstractC0241a0.f51910a4 = 0;
                    } catch (Exception unused8) {
                    }
                    ArrayList arrayList = C0430dv.f55884a0;
                    C0430dv.m212644a1();
                    t60.m214714d6("ScreenProjectionSvc", "✅ [清理] 资源清理完成（后台线程）");
                } catch (Exception e) {
                    t60.m214705c6("ScreenProjectionSvc", "❌ [清理] 清理失败", e);
                }
                return;
            default:
                MediaDisplayService mediaDisplayService2 = this.f60622a1;
                t60.m214695b6(mediaDisplayService2, "this$0");
                MediaDisplayService.C0279a0 c0279a02 = MediaDisplayService.f52303c1;
                mediaDisplayService2.m211389a2();
                return;
        }
    }
}
