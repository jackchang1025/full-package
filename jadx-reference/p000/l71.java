package p000;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.view.WindowManager;
import com.storm.safe.rock.service.modules.cipher.C0339a5;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class l71 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f57842a0;

    /* renamed from: a1 */
    public final /* synthetic */ AccessibilityService f57843a1;

    public /* synthetic */ l71(AccessibilityService accessibilityService, int i) {
        this.f57842a0 = i;
        this.f57843a1 = accessibilityService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f57842a0;
        AccessibilityService accessibilityService = this.f57843a1;
        switch (i) {
            case 0:
                Handler handler = C0339a5.f53370a8;
                try {
                    CopyOnWriteArrayList copyOnWriteArrayList = AbstractC1095q3.f59370a0;
                    t60.m214694b5(accessibilityService.getApplicationContext(), "svc.applicationContext");
                    AtomicBoolean atomicBoolean = AbstractC1095q3.f59371a1;
                    int i2 = 1;
                    if (!atomicBoolean.get() && v00.m214888a0()) {
                        AbstractC1095q3.f59370a0.clear();
                        atomicBoolean.set(true);
                        AbstractC1095q3.f59372a2.execute(new RunnableC1053p2(1));
                    }
                    if (!atomicBoolean.get()) {
                        handler.post(new l71(accessibilityService, i2));
                        break;
                    } else {
                        C0339a5.f53371a9 = true;
                        break;
                    }
                } catch (Exception unused) {
                    handler.post(new l71(accessibilityService, 2));
                    return;
                }
                break;
            case 1:
                WindowManager windowManager = C0339a5.f53362a0;
                C0339a5.m211854a2(accessibilityService);
                break;
            default:
                WindowManager windowManager2 = C0339a5.f53362a0;
                C0339a5.m211854a2(accessibilityService);
                break;
        }
    }
}
