package p000;

import androidx.work.impl.utils.futures.C0100a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g1 */
/* loaded from: classes2.dex */
public final class RunnableC0518g1 implements Runnable {

    /* renamed from: a0 */
    public final C0100a1 f56364a0;

    /* renamed from: a1 */
    public final ob0 f56365a1;

    public RunnableC0518g1(C0100a1 c0100a1, ob0 ob0Var) {
        this.f56364a0 = c0100a1;
        this.f56365a1 = ob0Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f56364a0.f56381a0 != this) {
            return;
        }
        if (AbstractC0521g4.f56379a5.mo212872a5(this.f56364a0, this, AbstractC0521g4.m212887a5(this.f56365a1))) {
            AbstractC0521g4.m212884a2(this.f56364a0);
        }
    }
}
