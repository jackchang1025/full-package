package p000;

import android.view.ViewGroup;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gv */
/* loaded from: classes.dex */
public final class C0558gv extends t71 {

    /* renamed from: a0 */
    public boolean f56573a0 = false;

    /* renamed from: a1 */
    public final /* synthetic */ ViewGroup f56574a1;

    public C0558gv(ViewGroup viewGroup) {
        this.f56574a1 = viewGroup;
    }

    @Override // p000.t71, p000.r71
    /* renamed from: a1 */
    public final void mo212983a1() {
        b81.m210599f2(this.f56574a1, false);
    }

    @Override // p000.t71, p000.r71
    /* renamed from: a2 */
    public final void mo212984a2() {
        b81.m210599f2(this.f56574a1, true);
    }

    @Override // p000.r71
    /* renamed from: a3 */
    public final void mo212985a3(s71 s71Var) {
        if (!this.f56573a0) {
            b81.m210599f2(this.f56574a1, false);
        }
        s71Var.m214581c0(this);
    }

    @Override // p000.t71, p000.r71
    /* renamed from: a4 */
    public final void mo212986a4() {
        b81.m210599f2(this.f56574a1, false);
        this.f56573a0 = true;
    }
}
