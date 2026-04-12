package p000;

import android.animation.AnimatorSet;
import android.view.View;
import com.google.android.material.bottomappbar.BottomAppBar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: el */
/* loaded from: classes2.dex */
public final class C0460el implements fd1 {

    /* renamed from: a0 */
    public final /* synthetic */ BottomAppBar f56072a0;

    public /* synthetic */ C0460el(BottomAppBar bottomAppBar) {
        this.f56072a0 = bottomAppBar;
    }

    @Override // p000.fd1
    /* renamed from: b5 */
    public xf1 mo212585b5(View view, xf1 xf1Var, gd1 gd1Var) {
        boolean z;
        BottomAppBar bottomAppBar = this.f56072a0;
        if (bottomAppBar.f49160f5) {
            bottomAppBar.f49166g1 = xf1Var.m215171a0();
        }
        boolean z2 = false;
        if (bottomAppBar.f49161f6) {
            z = bottomAppBar.f49168g3 != xf1Var.m215172a1();
            bottomAppBar.f49168g3 = xf1Var.m215172a1();
        } else {
            z = false;
        }
        if (bottomAppBar.f49162f7) {
            boolean z3 = bottomAppBar.f49167g2 != xf1Var.m215173a2();
            bottomAppBar.f49167g2 = xf1Var.m215173a2();
            z2 = z3;
        }
        if (!z && !z2) {
            return xf1Var;
        }
        AnimatorSet animatorSet = bottomAppBar.f49151e6;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AnimatorSet animatorSet2 = bottomAppBar.f49150e5;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        bottomAppBar.m210929d2();
        bottomAppBar.m210928d1();
        return xf1Var;
    }
}
