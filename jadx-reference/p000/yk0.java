package p000;

import androidx.activity.C0038a0;
import java.util.ArrayDeque;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class yk0 implements InterfaceC0514fz {

    /* renamed from: a0 */
    public final d00 f61337a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0038a0 f61338a1;

    public yk0(C0038a0 c0038a0, d00 d00Var) {
        this.f61338a1 = c0038a0;
        this.f61337a0 = d00Var;
    }

    @Override // p000.InterfaceC0514fz
    public final void cancel() {
        C0038a0 c0038a0 = this.f61338a1;
        ArrayDeque arrayDeque = c0038a0.f43756a1;
        d00 d00Var = this.f61337a0;
        arrayDeque.remove(d00Var);
        d00Var.f55549a1.remove(this);
        if (AbstractC0496fi.m212821a0()) {
            d00Var.f55550a2 = null;
            c0038a0.m209836a2();
        }
    }
}
