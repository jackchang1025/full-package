package p000;

import android.content.Context;
import android.net.ConnectivityManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sj0 extends AbstractC0826ln {

    /* renamed from: a5 */
    public final ConnectivityManager f59998a5;

    /* renamed from: a6 */
    public final mj0 f59999a6;

    public sj0(Context context, pg1 pg1Var) {
        super(context, pg1Var);
        Object systemService = this.f58054a1.getSystemService("connectivity");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        this.f59998a5 = (ConnectivityManager) systemService;
        this.f59999a6 = new mj0(1, this);
    }

    @Override // p000.AbstractC0826ln
    /* renamed from: a0 */
    public final Object mo212612a0() {
        return tj0.m214752a0(this.f59998a5);
    }

    @Override // p000.AbstractC0826ln
    /* renamed from: a3 */
    public final void mo212613a3() {
        try {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            int i = tj0.f60235a0;
            c1351vvM214963a5.getClass();
            ij0.m213166a0(this.f59998a5, this.f59999a6);
        } catch (IllegalArgumentException unused) {
            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
            int i2 = tj0.f60235a0;
            c1351vvM214963a52.getClass();
        } catch (SecurityException unused2) {
            C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
            int i3 = tj0.f60235a0;
            c1351vvM214963a53.getClass();
        }
    }

    @Override // p000.AbstractC0826ln
    /* renamed from: a4 */
    public final void mo212614a4() {
        try {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            int i = tj0.f60235a0;
            c1351vvM214963a5.getClass();
            gj0.m212960a2(this.f59998a5, this.f59999a6);
        } catch (IllegalArgumentException unused) {
            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
            int i2 = tj0.f60235a0;
            c1351vvM214963a52.getClass();
        } catch (SecurityException unused2) {
            C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
            int i3 = tj0.f60235a0;
            c1351vvM214963a53.getClass();
        }
    }
}
