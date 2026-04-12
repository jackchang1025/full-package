package p000;

import android.view.View;
import android.view.WindowInsets;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ma1 {
    /* renamed from: a0 */
    public static xf1 m213954a0(View view) {
        WindowInsets rootWindowInsets = view.getRootWindowInsets();
        if (rootWindowInsets == null) {
            return null;
        }
        xf1 xf1VarM215170a6 = xf1.m215170a6(null, rootWindowInsets);
        vf1 vf1Var = xf1VarM215170a6.f61102a0;
        vf1Var.mo214396b5(xf1VarM215170a6);
        vf1Var.mo214390a3(view.getRootView());
        return xf1VarM215170a6;
    }

    /* renamed from: a1 */
    public static int m213955a1(View view) {
        return view.getScrollIndicators();
    }

    /* renamed from: a2 */
    public static void m213956a2(View view, int i) {
        view.setScrollIndicators(i);
    }

    /* renamed from: a3 */
    public static void m213957a3(View view, int i, int i2) {
        view.setScrollIndicators(i, i2);
    }
}
