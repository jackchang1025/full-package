package p000;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ef1 implements View.OnApplyWindowInsetsListener {

    /* renamed from: a0 */
    public final C0816ld f56001a0;

    /* renamed from: a1 */
    public xf1 f56002a1;

    public ef1(View view, C0816ld c0816ld) {
        xf1 xf1VarMo213836a1;
        this.f56001a0 = c0816ld;
        WeakHashMap weakHashMap = xa1.f61054a0;
        xf1 xf1VarM213954a0 = ma1.m213954a0(view);
        if (xf1VarM213954a0 != null) {
            int i = Build.VERSION.SDK_INT;
            xf1VarMo213836a1 = (i >= 30 ? new of1(xf1VarM213954a0) : i >= 29 ? new mf1(xf1VarM213954a0) : new lf1(xf1VarM213954a0)).mo213836a1();
        } else {
            xf1VarMo213836a1 = null;
        }
        this.f56002a1 = xf1VarMo213836a1;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        if (!view.isLaidOut()) {
            this.f56002a1 = xf1.m215170a6(view, windowInsets);
            return ff1.m212805a8(view, windowInsets);
        }
        xf1 xf1VarM215170a6 = xf1.m215170a6(view, windowInsets);
        vf1 vf1Var = xf1VarM215170a6.f61102a0;
        if (this.f56002a1 == null) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            this.f56002a1 = ma1.m213954a0(view);
        }
        if (this.f56002a1 == null) {
            this.f56002a1 = xf1VarM215170a6;
            return ff1.m212805a8(view, windowInsets);
        }
        C0816ld c0816ldM212806a9 = ff1.m212806a9(view);
        if (c0816ldM212806a9 != null && Objects.equals((WindowInsets) c0816ldM212806a9.f57882a2, windowInsets)) {
            return ff1.m212805a8(view, windowInsets);
        }
        xf1 xf1Var = this.f56002a1;
        int i = 0;
        for (int i2 = 1; i2 <= 256; i2 <<= 1) {
            if (!vf1Var.mo214391a5(i2).equals(xf1Var.f61102a0.mo214391a5(i2))) {
                i |= i2;
            }
        }
        if (i == 0) {
            return ff1.m212805a8(view, windowInsets);
        }
        xf1 xf1Var2 = this.f56002a1;
        jf1 jf1Var = new jf1(i, (i & 8) != 0 ? vf1Var.mo214391a5(8).f56157a3 > xf1Var2.f61102a0.mo214391a5(8).f56157a3 ? ff1.f56234a4 : ff1.f56235a5 : ff1.f56236a6, 160L);
        jf1Var.f57330a0.mo213036a3(0.0f);
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(jf1Var.f57330a0.mo213033a0());
        f60 f60VarMo214391a5 = vf1Var.mo214391a5(i);
        f60 f60VarMo214391a52 = xf1Var2.f61102a0.mo214391a5(i);
        int iMin = Math.min(f60VarMo214391a5.f56154a0, f60VarMo214391a52.f56154a0);
        int i3 = f60VarMo214391a5.f56155a1;
        int i4 = f60VarMo214391a52.f56155a1;
        int iMin2 = Math.min(i3, i4);
        int i5 = f60VarMo214391a5.f56156a2;
        int i6 = f60VarMo214391a52.f56156a2;
        int iMin3 = Math.min(i5, i6);
        int i7 = f60VarMo214391a5.f56157a3;
        int i8 = i;
        int i9 = f60VarMo214391a52.f56157a3;
        C1217sc c1217sc = new C1217sc(f60.m212748a1(iMin, iMin2, iMin3, Math.min(i7, i9)), f60.m212748a1(Math.max(f60VarMo214391a5.f56154a0, f60VarMo214391a52.f56154a0), Math.max(i3, i4), Math.max(i5, i6), Math.max(i7, i9)));
        ff1.m212802a5(view, windowInsets, false);
        duration.addUpdateListener(new df1(jf1Var, xf1VarM215170a6, xf1Var2, i8, view));
        duration.addListener(new vm0(jf1Var, view, 3));
        el0.m212695a0(view, new RunnableC0818lf(view, jf1Var, c1217sc, duration));
        this.f56002a1 = xf1VarM215170a6;
        return ff1.m212805a8(view, windowInsets);
    }
}
