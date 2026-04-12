package p000;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.dqtvuisjd;
import kotlin.coroutines.AbstractC0775a0;
import kotlinx.coroutines.android.C0785a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class xz0 {

    /* renamed from: a0 */
    public final Context f61206a0;

    /* renamed from: a1 */
    public final C0873ms f61207a1;

    /* renamed from: a2 */
    public Handler f61208a2;

    /* renamed from: a3 */
    public HandlerThread f61209a3;

    static {
        new wz0(null);
    }

    public xz0(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f61206a0 = dqtvuisjdVar2;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f61207a1 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
    }

    /* renamed from: a0 */
    public final void m215218a0() {
        try {
            HandlerThread handlerThread = new HandlerThread("AccessibilityBackground");
            handlerThread.start();
            this.f61208a2 = new Handler(handlerThread.getLooper());
            this.f61209a3 = handlerThread;
        } catch (Exception e) {
            t60.m214705c6("ServiceLifecycleManager", "❌ 后台Handler初始化失败", e);
        }
        Context context = this.f61206a0;
        try {
            al1.f43714a5.getInstance(context).m209821a1();
        } catch (Exception e2) {
            t60.m214705c6("ServiceLifecycleManager", "❌ 启动保活协调器失败", e2);
        }
        try {
            AppCoreService.f52296a0.start(context);
        } catch (Exception e3) {
            t60.m214705c6("ServiceLifecycleManager", "❌ 启动 AppCoreService 失败", e3);
        }
    }
}
