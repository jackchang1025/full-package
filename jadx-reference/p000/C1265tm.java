package p000;

import java.util.concurrent.ScheduledFuture;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tm */
/* loaded from: classes2.dex */
public final class C1265tm implements InterfaceC1266tn {

    /* renamed from: a0 */
    public final ScheduledFuture f60238a0;

    public C1265tm(ScheduledFuture scheduledFuture) {
        this.f60238a0 = scheduledFuture;
    }

    @Override // p000.InterfaceC1266tn
    /* renamed from: a2 */
    public final void mo214761a2() {
        this.f60238a0.cancel(false);
    }

    public final String toString() {
        return "DisposableFutureHandle[" + this.f60238a0 + ']';
    }
}
