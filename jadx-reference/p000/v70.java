package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class v70 extends u70 {

    /* renamed from: a4 */
    public final y70 f60591a4;

    /* renamed from: a5 */
    public final w70 f60592a5;

    /* renamed from: a6 */
    public final C0582hi f60593a6;

    /* renamed from: a7 */
    public final Object f60594a7;

    public v70(y70 y70Var, w70 w70Var, C0582hi c0582hi, Object obj) {
        this.f60591a4 = y70Var;
        this.f60592a5 = w70Var;
        this.f60593a6 = c0582hi;
        this.f60594a7 = obj;
    }

    @Override // p000.u70
    /* renamed from: b1 */
    public final void mo213037b1(Throwable th) {
        C0582hi c0582hiM215250d3 = y70.m215250d3(this.f60593a6);
        y70 y70Var = this.f60591a4;
        w70 w70Var = this.f60592a5;
        Object obj = this.f60594a7;
        if (c0582hiM215250d3 != null) {
            while (c0582hiM215250d3.f56672a4.m215264c9((2 & 1) == 0, (2 & 2) != 0, new v70(y70Var, w70Var, c0582hiM215250d3, obj)) == vj0.f60645a0) {
                c0582hiM215250d3 = y70.m215250d3(c0582hiM215250d3);
                if (c0582hiM215250d3 == null) {
                }
            }
            return;
        }
        y70Var.mo212674a5(y70Var.m215258b7(w70Var, obj));
    }

    @Override // p000.h10
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        mo213037b1((Throwable) obj);
        return C1351vv.f60710b1;
    }
}
