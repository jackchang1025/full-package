package p000;

import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e71 extends kj1 {

    /* renamed from: a6 */
    public final /* synthetic */ int f55937a6;

    /* renamed from: a7 */
    public boolean f55938a7;

    /* renamed from: a8 */
    public int f55939a8;

    /* renamed from: a9 */
    public final /* synthetic */ Object f55940a9;

    public e71(f71 f71Var, int i) {
        this.f55937a6 = 0;
        this.f55940a9 = f71Var;
        this.f55939a8 = i;
        this.f55938a7 = false;
    }

    @Override // p000.oc1
    /* renamed from: a0 */
    public final void mo212658a0() {
        switch (this.f55937a6) {
            case 0:
                if (!this.f55938a7) {
                    ((f71) this.f55940a9).f56159a0.setVisibility(this.f55939a8);
                    break;
                }
                break;
            default:
                int i = this.f55939a8 + 1;
                this.f55939a8 = i;
                nc1 nc1Var = (nc1) this.f55940a9;
                if (i == nc1Var.f58497a0.size()) {
                    oc1 oc1Var = nc1Var.f58500a3;
                    if (oc1Var != null) {
                        oc1Var.mo212658a0();
                    }
                    this.f55939a8 = 0;
                    this.f55938a7 = false;
                    nc1Var.f58501a4 = false;
                    break;
                }
                break;
        }
    }

    @Override // p000.kj1, p000.oc1
    /* renamed from: a1 */
    public void mo212659a1(View view) {
        switch (this.f55937a6) {
            case 0:
                this.f55938a7 = true;
                break;
        }
    }

    @Override // p000.kj1, p000.oc1
    /* renamed from: a2 */
    public final void mo212660a2() {
        switch (this.f55937a6) {
            case 0:
                ((f71) this.f55940a9).f56159a0.setVisibility(0);
                break;
            default:
                if (!this.f55938a7) {
                    this.f55938a7 = true;
                    oc1 oc1Var = ((nc1) this.f55940a9).f58500a3;
                    if (oc1Var != null) {
                        oc1Var.mo212660a2();
                        break;
                    }
                }
                break;
        }
    }

    public e71(nc1 nc1Var) {
        this.f55937a6 = 1;
        this.f55940a9 = nc1Var;
        this.f55938a7 = false;
        this.f55939a8 = 0;
    }
}
