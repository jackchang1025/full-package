package p000;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mg1 implements Executor {

    /* renamed from: a0 */
    public final /* synthetic */ pg1 f58372a0;

    public mg1(pg1 pg1Var) {
        this.f58372a0 = pg1Var;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        ((Handler) this.f58372a0.f59230a2).post(runnable);
    }
}
