package p000;

import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class tb1 extends AbstractC0879my {

    /* renamed from: a0 */
    public ub1 f60194a0;

    /* renamed from: a1 */
    public int f60195a1 = 0;

    public tb1() {
    }

    @Override // p000.AbstractC0879my
    /* renamed from: a7 */
    public boolean mo210886a7(CoordinatorLayout coordinatorLayout, View view, int i) {
        mo56b9(coordinatorLayout, view, i);
        if (this.f60194a0 == null) {
            this.f60194a0 = new ub1(view);
        }
        ub1 ub1Var = this.f60194a0;
        View view2 = ub1Var.f60372a0;
        ub1Var.f60373a1 = view2.getTop();
        ub1Var.f60374a2 = view2.getLeft();
        this.f60194a0.m214830a0();
        int i2 = this.f60195a1;
        if (i2 == 0) {
            return true;
        }
        this.f60194a0.m214831a1(i2);
        this.f60195a1 = 0;
        return true;
    }

    /* renamed from: b8 */
    public final int m214734b8() {
        ub1 ub1Var = this.f60194a0;
        if (ub1Var != null) {
            return ub1Var.f60375a3;
        }
        return 0;
    }

    /* renamed from: b9 */
    public void mo56b9(CoordinatorLayout coordinatorLayout, View view, int i) {
        coordinatorLayout.m210068b6(view, i);
    }

    public tb1(int i) {
    }
}
