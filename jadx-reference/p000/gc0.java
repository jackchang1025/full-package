package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class gc0 {
    public /* synthetic */ gc0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final int addFailReason(long j) {
        return (j & 2305843009213693952L) != 0 ? 2 : 1;
    }

    public final long updateHead(long j, int i) {
        return m212939wo(j, 1073741823L) | i;
    }

    public final long updateTail(long j, int i) {
        return m212939wo(j, 1152921503533105152L) | (i << 30);
    }

    public final <T> T withState(long j, l10 l10Var) {
        return (T) l10Var.invoke(Integer.valueOf((int) (1073741823 & j)), Integer.valueOf((int) ((j & 1152921503533105152L) >> 30)));
    }

    /* renamed from: wo */
    public final long m212939wo(long j, long j2) {
        return j & (~j2);
    }

    private gc0() {
    }
}
