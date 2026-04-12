package p000;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xf1 {

    /* renamed from: a1 */
    public static final xf1 f61101a1;

    /* renamed from: a0 */
    public final vf1 f61102a0;

    static {
        if (Build.VERSION.SDK_INT >= 30) {
            f61101a1 = uf1.f60422b6;
        } else {
            f61101a1 = vf1.f60623a1;
        }
    }

    public xf1(WindowInsets windowInsets) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            this.f61102a0 = new uf1(this, windowInsets);
            return;
        }
        if (i >= 29) {
            this.f61102a0 = new tf1(this, windowInsets);
        } else if (i >= 28) {
            this.f61102a0 = new sf1(this, windowInsets);
        } else {
            this.f61102a0 = new rf1(this, windowInsets);
        }
    }

    /* renamed from: a4 */
    public static f60 m215169a4(f60 f60Var, int i, int i2, int i3, int i4) {
        int iMax = Math.max(0, f60Var.f56154a0 - i);
        int iMax2 = Math.max(0, f60Var.f56155a1 - i2);
        int iMax3 = Math.max(0, f60Var.f56156a2 - i3);
        int iMax4 = Math.max(0, f60Var.f56157a3 - i4);
        return (iMax == i && iMax2 == i2 && iMax3 == i3 && iMax4 == i4) ? f60Var : f60.m212748a1(iMax, iMax2, iMax3, iMax4);
    }

    /* renamed from: a6 */
    public static xf1 m215170a6(View view, WindowInsets windowInsets) {
        windowInsets.getClass();
        xf1 xf1Var = new xf1(windowInsets);
        if (view != null) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            if (ia1.m213141a1(view)) {
                xf1 xf1VarM213954a0 = ma1.m213954a0(view);
                vf1 vf1Var = xf1Var.f61102a0;
                vf1Var.mo214396b5(xf1VarM213954a0);
                vf1Var.mo214390a3(view.getRootView());
            }
        }
        return xf1Var;
    }

    /* renamed from: a0 */
    public final int m215171a0() {
        return this.f61102a0.mo214392a9().f56157a3;
    }

    /* renamed from: a1 */
    public final int m215172a1() {
        return this.f61102a0.mo214392a9().f56154a0;
    }

    /* renamed from: a2 */
    public final int m215173a2() {
        return this.f61102a0.mo214392a9().f56156a2;
    }

    /* renamed from: a3 */
    public final int m215174a3() {
        return this.f61102a0.mo214392a9().f56155a1;
    }

    /* renamed from: a5 */
    public final WindowInsets m215175a5() {
        vf1 vf1Var = this.f61102a0;
        if (vf1Var instanceof qf1) {
            return ((qf1) vf1Var).f59497a2;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof xf1) {
            return tk0.m214759a0(this.f61102a0, ((xf1) obj).f61102a0);
        }
        return false;
    }

    public final int hashCode() {
        vf1 vf1Var = this.f61102a0;
        if (vf1Var == null) {
            return 0;
        }
        return vf1Var.hashCode();
    }

    public xf1() {
        this.f61102a0 = new vf1(this);
    }
}
