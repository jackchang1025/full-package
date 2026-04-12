package p000;

import android.view.WindowInsets;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class rf1 extends qf1 {

    /* renamed from: b2 */
    public f60 f59766b2;

    public rf1(xf1 xf1Var, WindowInsets windowInsets) {
        super(xf1Var, windowInsets);
        this.f59766b2 = null;
    }

    @Override // p000.vf1
    /* renamed from: a1 */
    public xf1 mo214535a1() {
        return xf1.m215170a6(null, this.f59497a2.consumeStableInsets());
    }

    @Override // p000.vf1
    /* renamed from: a2 */
    public xf1 mo214536a2() {
        return xf1.m215170a6(null, this.f59497a2.consumeSystemWindowInsets());
    }

    @Override // p000.vf1
    /* renamed from: a7 */
    public final f60 mo214537a7() {
        if (this.f59766b2 == null) {
            WindowInsets windowInsets = this.f59497a2;
            this.f59766b2 = f60.m212748a1(windowInsets.getStableInsetLeft(), windowInsets.getStableInsetTop(), windowInsets.getStableInsetRight(), windowInsets.getStableInsetBottom());
        }
        return this.f59766b2;
    }

    @Override // p000.vf1
    /* renamed from: b2 */
    public boolean mo214538b2() {
        return this.f59497a2.isConsumed();
    }

    @Override // p000.vf1
    /* renamed from: b6 */
    public void mo214539b6(f60 f60Var) {
        this.f59766b2 = f60Var;
    }
}
