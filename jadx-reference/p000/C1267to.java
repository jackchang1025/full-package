package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: to */
/* loaded from: classes2.dex */
public final class C1267to extends u70 {

    /* renamed from: a4 */
    public final /* synthetic */ int f60244a4;

    /* renamed from: a5 */
    public final Object f60245a5;

    public /* synthetic */ C1267to(int i, Object obj) {
        this.f60244a4 = i;
        this.f60245a5 = obj;
    }

    @Override // p000.u70
    /* renamed from: b1 */
    public final void mo213037b1(Throwable th) {
        switch (this.f60244a4) {
            case 0:
                ((InterfaceC1266tn) this.f60245a5).mo214761a2();
                break;
            default:
                ((h10) this.f60245a5).invoke(th);
                break;
        }
    }

    @Override // p000.h10
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        switch (this.f60244a4) {
            case 0:
                mo213037b1((Throwable) obj);
                break;
            default:
                mo213037b1((Throwable) obj);
                break;
        }
        return C1351vv.f60710b1;
    }
}
