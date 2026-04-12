package p000;

import java.util.concurrent.ScheduledFuture;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fu */
/* loaded from: classes2.dex */
public final class C0509fu implements wj0, h10 {

    /* renamed from: a0 */
    public final /* synthetic */ int f56330a0;

    /* renamed from: a1 */
    public final Object f56331a1;

    public /* synthetic */ C0509fu(int i, Object obj) {
        this.f56330a0 = i;
        this.f56331a1 = obj;
    }

    /* renamed from: a0 */
    public final void m212864a0(Throwable th) {
        switch (this.f56330a0) {
            case 0:
                if (th != null) {
                    ((ScheduledFuture) this.f56331a1).cancel(false);
                    break;
                }
                break;
            case 1:
                ((C1420xk) this.f56331a1).mo214761a2();
                break;
            default:
                ((h10) this.f56331a1).invoke(th);
                break;
        }
    }

    @Override // p000.h10
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        switch (this.f56330a0) {
            case 0:
                m212864a0((Throwable) obj);
                break;
            case 1:
                m212864a0((Throwable) obj);
                break;
            default:
                m212864a0((Throwable) obj);
                break;
        }
        return C1351vv.f60710b1;
    }

    public final String toString() {
        switch (this.f56330a0) {
            case 0:
                return "CancelFutureOnCancel[" + ((ScheduledFuture) this.f56331a1) + ']';
            case 1:
                return "DisposeOnCancel[" + ((C1420xk) this.f56331a1) + ']';
            default:
                return "InvokeOnCancel[" + ((h10) this.f56331a1).getClass().getSimpleName() + '@' + AbstractC1117qo.m214435d1(this) + ']';
        }
    }
}
