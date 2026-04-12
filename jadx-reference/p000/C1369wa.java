package p000;

import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wa */
/* loaded from: classes.dex */
public final class C1369wa extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ C1370wb f60869b0;

    public C1369wa(C1370wb c1370wb) {
        this.f60869b0 = c1370wb;
    }

    @Override // p000.cq0
    /* renamed from: c5 */
    public final void mo212507c5(Throwable th) {
        this.f60869b0.f60879a0.m215061a3(th);
    }

    @Override // p000.cq0
    /* renamed from: c9 */
    public final void mo212511c9(x31 x31Var) {
        C1370wb c1370wb = this.f60869b0;
        c1370wb.f60881a2 = x31Var;
        c1370wb.f60880a1 = new og1(c1370wb.f60881a2, new C1351vv(20), c1370wb.f60879a0.f60908a7);
        C1375wg c1375wg = c1370wb.f60879a0;
        c1375wg.getClass();
        ArrayList arrayList = new ArrayList();
        c1375wg.f60901a0.writeLock().lock();
        try {
            c1375wg.f60903a2 = 1;
            arrayList.addAll(c1375wg.f60902a1);
            c1375wg.f60902a1.clear();
            c1375wg.f60901a0.writeLock().unlock();
            c1375wg.f60904a3.post(new RunnableC0503fo(arrayList, c1375wg.f60903a2, (Throwable) null));
        } catch (Throwable th) {
            c1375wg.f60901a0.writeLock().unlock();
            throw th;
        }
    }
}
