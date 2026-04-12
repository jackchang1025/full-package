package p000;

import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fw */
/* loaded from: classes2.dex */
public final class C0511fw extends AbstractRunnableC0512fx {

    /* renamed from: a1 */
    public final /* synthetic */ C0096a0 f56335a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f56336a2;

    /* renamed from: a3 */
    public final /* synthetic */ boolean f56337a3;

    public C0511fw(C0096a0 c0096a0, String str, boolean z) {
        this.f56335a1 = c0096a0;
        this.f56336a2 = str;
        this.f56337a3 = z;
    }

    @Override // p000.AbstractRunnableC0512fx
    /* renamed from: a1 */
    public final void mo212866a1() {
        C0096a0 c0096a0 = this.f56335a1;
        WorkDatabase workDatabase = c0096a0.f45559a6;
        workDatabase.m212858a2();
        try {
            ArrayList arrayListM215184a7 = workDatabase.mo210465b9().m215184a7(this.f56336a2);
            int size = arrayListM215184a7.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM215184a7.get(i);
                i++;
                AbstractRunnableC0512fx.m212867a0(c0096a0, (String) obj);
            }
            workDatabase.m212863b2();
            workDatabase.m212860a9();
            if (this.f56337a3) {
                fu0.m212865a0(c0096a0.f45558a5, c0096a0.f45559a6, c0096a0.f45561a8);
            }
        } catch (Throwable th) {
            workDatabase.m212860a9();
            throw th;
        }
    }
}
