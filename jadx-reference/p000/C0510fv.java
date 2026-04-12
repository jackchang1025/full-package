package p000;

import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import java.util.UUID;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fv */
/* loaded from: classes2.dex */
public final class C0510fv extends AbstractRunnableC0512fx {

    /* renamed from: a1 */
    public final /* synthetic */ C0096a0 f56333a1;

    /* renamed from: a2 */
    public final /* synthetic */ UUID f56334a2;

    public C0510fv(C0096a0 c0096a0, UUID uuid) {
        this.f56333a1 = c0096a0;
        this.f56334a2 = uuid;
    }

    @Override // p000.AbstractRunnableC0512fx
    /* renamed from: a1 */
    public final void mo212866a1() {
        C0096a0 c0096a0 = this.f56333a1;
        WorkDatabase workDatabase = c0096a0.f45559a6;
        workDatabase.m212858a2();
        try {
            AbstractRunnableC0512fx.m212867a0(c0096a0, this.f56334a2.toString());
            workDatabase.m212863b2();
            workDatabase.m212860a9();
            fu0.m212865a0(c0096a0.f45558a5, c0096a0.f45559a6, c0096a0.f45561a8);
        } catch (Throwable th) {
            workDatabase.m212860a9();
            throw th;
        }
    }
}
