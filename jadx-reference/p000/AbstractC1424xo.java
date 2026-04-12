package p000;

import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.RunnableC0782a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xo */
/* loaded from: classes2.dex */
public abstract class AbstractC1424xo extends AbstractC0781a1 {

    /* renamed from: a5 */
    public static final /* synthetic */ int f61165a5 = 0;

    /* renamed from: a2 */
    public long f61166a2;

    /* renamed from: a3 */
    public boolean f61167a3;

    /* renamed from: a4 */
    public C0127ba f61168a4;

    /* renamed from: c8 */
    public final void m215200c8(boolean z) {
        long j = this.f61166a2 - (z ? 4294967296L : 1L);
        this.f61166a2 = j;
        if (j <= 0 && this.f61167a3) {
            shutdown();
        }
    }

    /* renamed from: c9 */
    public abstract Thread mo212675c9();

    /* renamed from: d0 */
    public final void m215201d0(boolean z) {
        this.f61166a2 = (z ? 4294967296L : 1L) + this.f61166a2;
        if (z) {
            return;
        }
        this.f61167a3 = true;
    }

    /* renamed from: d1 */
    public abstract long mo213704d1();

    /* renamed from: d2 */
    public final boolean m215202d2() {
        C0127ba c0127ba = this.f61168a4;
        if (c0127ba == null) {
            return false;
        }
        AbstractC1259th abstractC1259th = (AbstractC1259th) (c0127ba.isEmpty() ? null : c0127ba.removeFirst());
        if (abstractC1259th == null) {
            return false;
        }
        abstractC1259th.run();
        return true;
    }

    /* renamed from: d3 */
    public void mo213700d3(long j, AbstractRunnableC1422xm abstractRunnableC1422xm) {
        RunnableC0782a2.f57657a9.m213707d7(j, abstractRunnableC1422xm);
    }

    public abstract void shutdown();
}
