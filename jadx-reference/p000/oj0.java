package p000;

import android.os.Build;
import androidx.work.NetworkType;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class oj0 extends AbstractC0799kx {
    static {
        new nj0(null);
        t60.m214694b5(C1351vv.m214966b1("NetworkMeteredCtrlr"), "tagWithPrefix(\"NetworkMeteredCtrlr\")");
    }

    @Override // p000.AbstractC0799kx
    /* renamed from: a0 */
    public final boolean mo212609a0(wg1 wg1Var) {
        t60.m214695b6(wg1Var, "workSpec");
        return wg1Var.f60921a9.f58193a0 == NetworkType.f45520a4;
    }

    @Override // p000.AbstractC0799kx
    /* renamed from: a1 */
    public final boolean mo212610a1(Object obj) {
        rj0 rj0Var = (rj0) obj;
        t60.m214695b6(rj0Var, "value");
        boolean z = rj0Var.f59779a0;
        if (Build.VERSION.SDK_INT >= 26) {
            return (z && rj0Var.f59781a2) ? false : true;
        }
        C1351vv.m214963a5().getClass();
        return !z;
    }
}
