package p000;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.PathInterpolator;
import java.util.Collections;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class df1 implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ jf1 f55717a0;

    /* renamed from: a1 */
    public final /* synthetic */ xf1 f55718a1;

    /* renamed from: a2 */
    public final /* synthetic */ xf1 f55719a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f55720a3;

    /* renamed from: a4 */
    public final /* synthetic */ View f55721a4;

    public df1(jf1 jf1Var, xf1 xf1Var, xf1 xf1Var2, int i, View view) {
        this.f55717a0 = jf1Var;
        this.f55718a1 = xf1Var;
        this.f55719a2 = xf1Var2;
        this.f55720a3 = i;
        this.f55721a4 = view;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        jf1 jf1Var = this.f55717a0;
        if1 if1Var = jf1Var.f57330a0;
        if1Var.mo213036a3(animatedFraction);
        xf1 xf1Var = this.f55718a1;
        vf1 vf1Var = xf1Var.f61102a0;
        float fMo213034a1 = if1Var.mo213034a1();
        PathInterpolator pathInterpolator = ff1.f56234a4;
        int i = Build.VERSION.SDK_INT;
        pf1 of1Var = i >= 30 ? new of1(xf1Var) : i >= 29 ? new mf1(xf1Var) : new lf1(xf1Var);
        for (int i2 = 1; i2 <= 256; i2 <<= 1) {
            if ((this.f55720a3 & i2) == 0) {
                of1Var.mo214190a2(i2, vf1Var.mo214391a5(i2));
            } else {
                f60 f60VarMo214391a5 = vf1Var.mo214391a5(i2);
                f60 f60VarMo214391a52 = this.f55719a2.f61102a0.mo214391a5(i2);
                float f = 1.0f - fMo213034a1;
                of1Var.mo214190a2(i2, xf1.m215169a4(f60VarMo214391a5, (int) (((f60VarMo214391a5.f56154a0 - f60VarMo214391a52.f56154a0) * f) + 0.5d), (int) (((f60VarMo214391a5.f56155a1 - f60VarMo214391a52.f56155a1) * f) + 0.5d), (int) (((f60VarMo214391a5.f56156a2 - f60VarMo214391a52.f56156a2) * f) + 0.5d), (int) (((f60VarMo214391a5.f56157a3 - f60VarMo214391a52.f56157a3) * f) + 0.5d)));
            }
        }
        ff1.m212803a6(this.f55721a4, of1Var.mo213836a1(), Collections.singletonList(jf1Var));
    }
}
