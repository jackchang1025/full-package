package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qz */
/* loaded from: classes.dex */
public final class C1161qz extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ dr0 f59567a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f59568a1;

    /* renamed from: a2 */
    public final /* synthetic */ View f59569a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f59570a3;

    /* renamed from: a4 */
    public final /* synthetic */ ViewPropertyAnimator f59571a4;

    /* renamed from: a5 */
    public final /* synthetic */ C1176rd f59572a5;

    public C1161qz(C1176rd c1176rd, dr0 dr0Var, int i, View view, int i2, ViewPropertyAnimator viewPropertyAnimator) {
        this.f59572a5 = c1176rd;
        this.f59567a0 = dr0Var;
        this.f59568a1 = i;
        this.f59569a2 = view;
        this.f59570a3 = i2;
        this.f59571a4 = viewPropertyAnimator;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        int i = this.f59568a1;
        View view = this.f59569a2;
        if (i != 0) {
            view.setTranslationX(0.0f);
        }
        if (this.f59570a3 != 0) {
            view.setTranslationY(0.0f);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        this.f59571a4.setListener(null);
        C1176rd c1176rd = this.f59572a5;
        dr0 dr0Var = this.f59567a0;
        c1176rd.m213916a2(dr0Var);
        c1176rd.f59683b5.remove(dr0Var);
        c1176rd.m214529a8();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        this.f59572a5.getClass();
    }
}
