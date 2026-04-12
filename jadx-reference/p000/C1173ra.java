package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ra */
/* loaded from: classes.dex */
public final class C1173ra extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f59651a0;

    /* renamed from: a1 */
    public final /* synthetic */ C1174rb f59652a1;

    /* renamed from: a2 */
    public final /* synthetic */ ViewPropertyAnimator f59653a2;

    /* renamed from: a3 */
    public final /* synthetic */ View f59654a3;

    /* renamed from: a4 */
    public final /* synthetic */ C1176rd f59655a4;

    public /* synthetic */ C1173ra(C1176rd c1176rd, C1174rb c1174rb, ViewPropertyAnimator viewPropertyAnimator, View view, int i) {
        this.f59651a0 = i;
        this.f59655a4 = c1176rd;
        this.f59652a1 = c1174rb;
        this.f59653a2 = viewPropertyAnimator;
        this.f59654a3 = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f59651a0) {
            case 0:
                this.f59653a2.setListener(null);
                View view = this.f59654a3;
                view.setAlpha(1.0f);
                view.setTranslationX(0.0f);
                view.setTranslationY(0.0f);
                C1174rb c1174rb = this.f59652a1;
                dr0 dr0Var = c1174rb.f59660a0;
                C1176rd c1176rd = this.f59655a4;
                c1176rd.m213916a2(dr0Var);
                c1176rd.f59685b7.remove(c1174rb.f59660a0);
                c1176rd.m214529a8();
                break;
            default:
                this.f59653a2.setListener(null);
                View view2 = this.f59654a3;
                view2.setAlpha(1.0f);
                view2.setTranslationX(0.0f);
                view2.setTranslationY(0.0f);
                C1174rb c1174rb2 = this.f59652a1;
                dr0 dr0Var2 = c1174rb2.f59661a1;
                C1176rd c1176rd2 = this.f59655a4;
                c1176rd2.m213916a2(dr0Var2);
                c1176rd2.f59685b7.remove(c1174rb2.f59661a1);
                c1176rd2.m214529a8();
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        switch (this.f59651a0) {
            case 0:
                dr0 dr0Var = this.f59652a1.f59660a0;
                this.f59655a4.getClass();
                break;
            default:
                dr0 dr0Var2 = this.f59652a1.f59661a1;
                this.f59655a4.getClass();
                break;
        }
    }
}
