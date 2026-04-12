package p000;

import kotlinx.coroutines.AbstractC0783a3;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xk */
/* loaded from: classes2.dex */
public final class C1420xk extends AbstractRunnableC1422xm {

    /* renamed from: a2 */
    public final C0530gb f61152a2;

    /* renamed from: a3 */
    public final /* synthetic */ AbstractC0783a3 f61153a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1420xk(AbstractC0783a3 abstractC0783a3, long j, C0530gb c0530gb) {
        super(j);
        this.f61153a3 = abstractC0783a3;
        this.f61152a2 = c0530gb;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f61152a2.m212935c6(this.f61153a3);
    }

    @Override // p000.AbstractRunnableC1422xm
    public final String toString() {
        return super.toString() + this.f61152a2;
    }
}
