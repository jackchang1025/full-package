package p000;

import android.content.Context;
import androidx.work.impl.utils.futures.C0100a1;
import java.util.UUID;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hg1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ C0100a1 f56667a0;

    /* renamed from: a1 */
    public final /* synthetic */ UUID f56668a1;

    /* renamed from: a2 */
    public final /* synthetic */ C1241t f56669a2;

    /* renamed from: a3 */
    public final /* synthetic */ Context f56670a3;

    /* renamed from: a4 */
    public final /* synthetic */ ig1 f56671a4;

    public hg1(ig1 ig1Var, C0100a1 c0100a1, UUID uuid, C1241t c1241t, Context context) {
        this.f56671a4 = ig1Var;
        this.f56667a0 = c0100a1;
        this.f56668a1 = uuid;
        this.f56669a2 = c1241t;
        this.f56670a3 = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (!(this.f56667a0.f56381a0 instanceof C0486f8)) {
                String string = this.f56668a1.toString();
                wg1 wg1VarM215185a8 = this.f56671a4.f56886a2.m215185a8(string);
                if (wg1VarM215185a8 == null || wg1VarM215185a8.f60913a1.m210457a0()) {
                    throw new IllegalStateException("Calls to setForegroundAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
                }
                ((so0) this.f56671a4.f56885a1).m214654a5(string, this.f56669a2);
                this.f56670a3.startService(r31.m214472a0(this.f56670a3, cq0.m212483b3(wg1VarM215185a8), this.f56669a2));
            }
            this.f56667a0.m210484a8(null);
        } catch (Throwable th) {
            this.f56667a0.m210485a9(th);
        }
    }
}
