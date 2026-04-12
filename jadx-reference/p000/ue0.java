package p000;

import android.content.Intent;
import android.os.Handler;
import com.storm.safe.rock.service.MediaDisplayService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ue0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f60417a0;

    /* renamed from: a1 */
    public final /* synthetic */ MediaDisplayService f60418a1;

    /* renamed from: a2 */
    public final /* synthetic */ Intent f60419a2;

    public /* synthetic */ ue0(MediaDisplayService mediaDisplayService, Intent intent, int i) {
        this.f60417a0 = i;
        this.f60418a1 = mediaDisplayService;
        this.f60419a2 = intent;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException {
        switch (this.f60417a0) {
            case 0:
                MediaDisplayService mediaDisplayService = this.f60418a1;
                Intent intent = this.f60419a2;
                MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
                for (int i = 0; mediaDisplayService.f52322b3 && i < 20; i++) {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception unused) {
                    }
                }
                if (mediaDisplayService.f52322b3) {
                    t60.m214704c5("ScreenProjectionSvc", "❌ [启动] 等待清理超时，强制重置状态");
                    mediaDisplayService.f52322b3 = false;
                }
                Handler handler = mediaDisplayService.f52315a6;
                if (handler != null) {
                    handler.post(new ue0(mediaDisplayService, intent, 1));
                    break;
                }
                break;
            default:
                MediaDisplayService mediaDisplayService2 = this.f60418a1;
                Intent intent2 = this.f60419a2;
                MediaDisplayService.C0279a0 c0279a02 = MediaDisplayService.f52303c1;
                t60.m214695b6(mediaDisplayService2, "this$0");
                mediaDisplayService2.m211390a3(intent2);
                break;
        }
    }
}
