package p000;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.dqtvuisjd;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class fd0 {

    /* renamed from: a0 */
    public final dqtvuisjd f56198a0;

    /* renamed from: a1 */
    public C0454ef f56199a1;

    /* renamed from: a2 */
    public int f56200a2;

    static {
        new ed0(null);
    }

    public fd0(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f56198a0 = dqtvuisjdVar;
        new Handler(Looper.getMainLooper());
        this.f56200a2 = 252;
    }

    /* renamed from: a0 */
    public final void m212792a0() {
        try {
            if (this.f56199a1 == null) {
                t60.m214726f4("MaskOverlayManager", "⚠️ overlay 为空，重新创建...");
                this.f56199a1 = C0454ef.f55976c3.getInstance(this.f56198a0);
            }
            C0454ef c0454ef = this.f56199a1;
            if (c0454ef != null) {
                int i = this.f56200a2;
                c0454ef.f55985a7 = true;
                c0454ef.f55996b8.post(new RunnableC0027ag(c0454ef, i, 1));
            }
            C0454ef c0454ef2 = this.f56199a1;
            if (c0454ef2 != null) {
                c0454ef2.f55996b8.post(new RunnableC0449ea(false, c0454ef2));
            }
        } catch (Exception e) {
            t60.m214705c6("MaskOverlayManager", "❌ 启用非阻塞模式失败", e);
        }
    }

    /* renamed from: a1 */
    public final boolean m212793a1() {
        C0454ef c0454ef = this.f56199a1;
        if (c0454ef != null) {
            return c0454ef.f55984a6;
        }
        return false;
    }

    /* renamed from: a2 */
    public final void m212794a2(w00 w00Var) {
        C1351vv c1351vv;
        try {
            C0454ef c0454ef = this.f56199a1;
            if (c0454ef != null) {
                if (c0454ef.f55984a6) {
                    c0454ef.f55996b8.post(new RunnableC1052p1(c0454ef, w00Var));
                } else {
                    w00Var.invoke();
                }
                c1351vv = C1351vv.f60710b1;
            } else {
                c1351vv = null;
            }
            if (c1351vv == null) {
                w00Var.invoke();
            }
        } catch (Exception e) {
            t60.m214705c6("MaskOverlayManager", "❌ 操作期间遮罩控制失败", e);
        }
    }
}
