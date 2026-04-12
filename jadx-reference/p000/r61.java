package p000;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.TimeoutCancellationException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class r61 extends hu0 implements Runnable {

    /* renamed from: a4 */
    public final long f59639a4;

    public r61(long j, ContinuationImpl continuationImpl) {
        super(continuationImpl.getContext(), continuationImpl);
        this.f59639a4 = j;
    }

    @Override // p000.y70
    /* renamed from: d2 */
    public final String mo214491d2() {
        return super.mo214491d2() + "(timeMillis=" + this.f59639a4 + ')';
    }

    @Override // java.lang.Runnable
    public final void run() {
        b81.m210575b6(this.f56146a2);
        m215254a8(new TimeoutCancellationException("Timed out waiting for " + this.f59639a4 + " ms", this));
    }
}
