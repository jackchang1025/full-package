package p000;

import android.graphics.Typeface;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class p51 extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ cq0 f59158b0;

    /* renamed from: b1 */
    public final /* synthetic */ r51 f59159b1;

    public p51(r51 r51Var, cq0 cq0Var) {
        this.f59159b1 = r51Var;
        this.f59158b0 = cq0Var;
    }

    @Override // p000.cq0
    /* renamed from: c6 */
    public final void mo212508c6(int i) {
        this.f59159b1.f59637b2 = true;
        this.f59158b0.mo212508c6(i);
    }

    @Override // p000.cq0
    /* renamed from: c7 */
    public final void mo212509c7(Typeface typeface) {
        r51 r51Var = this.f59159b1;
        r51Var.f59638b3 = Typeface.create(typeface, r51Var.f59627a2);
        r51Var.f59637b2 = true;
        this.f59158b0.mo212510c8(r51Var.f59638b3, false);
    }
}
