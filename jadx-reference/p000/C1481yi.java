package p000;

import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yi */
/* loaded from: classes.dex */
public final class C1481yi extends t71 {

    /* renamed from: a0 */
    public final /* synthetic */ int f61322a0;

    /* renamed from: a1 */
    public Object f61323a1;

    public /* synthetic */ C1481yi() {
        this.f61322a0 = 2;
    }

    @Override // p000.t71, p000.r71
    /* renamed from: a0 */
    public void mo214186a0() {
        switch (this.f61322a0) {
            case 2:
                C0166cb c0166cb = (C0166cb) this.f61323a1;
                if (!c0166cb.f46090c5) {
                    c0166cb.m214582c9();
                    c0166cb.f46090c5 = true;
                    break;
                }
                break;
        }
    }

    @Override // p000.r71
    /* renamed from: a3 */
    public final void mo212985a3(s71 s71Var) {
        switch (this.f61322a0) {
            case 0:
                View view = (View) this.f61323a1;
                jd1 jd1Var = hd1.f56654a0;
                jd1Var.mo213495e9(view, 1.0f);
                jd1Var.getClass();
                s71Var.m214581c0(this);
                break;
            case 1:
                ((s71) this.f61323a1).mo210787c2();
                s71Var.m214581c0(this);
                break;
            default:
                C0166cb c0166cb = (C0166cb) this.f61323a1;
                int i = c0166cb.f46089c4 - 1;
                c0166cb.f46089c4 = i;
                if (i == 0) {
                    c0166cb.f46090c5 = false;
                    c0166cb.m214576b1();
                }
                s71Var.m214581c0(this);
                break;
        }
    }

    public /* synthetic */ C1481yi(int i, Object obj) {
        this.f61322a0 = i;
        this.f61323a1 = obj;
    }
}
