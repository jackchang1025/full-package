package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qy */
/* loaded from: classes.dex */
public final class C1160qy extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f59561a0 = 1;

    /* renamed from: a1 */
    public final /* synthetic */ dr0 f59562a1;

    /* renamed from: a2 */
    public final /* synthetic */ View f59563a2;

    /* renamed from: a3 */
    public final /* synthetic */ ViewPropertyAnimator f59564a3;

    /* renamed from: a4 */
    public final /* synthetic */ C1176rd f59565a4;

    public C1160qy(C1176rd c1176rd, dr0 dr0Var, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.f59565a4 = c1176rd;
        this.f59562a1 = dr0Var;
        this.f59564a3 = viewPropertyAnimator;
        this.f59563a2 = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f59561a0) {
            case 1:
                this.f59563a2.setAlpha(1.0f);
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f59561a0) {
            case 0:
                this.f59564a3.setListener(null);
                this.f59563a2.setAlpha(1.0f);
                C1176rd c1176rd = this.f59565a4;
                dr0 dr0Var = this.f59562a1;
                c1176rd.m213916a2(dr0Var);
                c1176rd.f59684b6.remove(dr0Var);
                c1176rd.m214529a8();
                break;
            default:
                this.f59564a3.setListener(null);
                C1176rd c1176rd2 = this.f59565a4;
                dr0 dr0Var2 = this.f59562a1;
                c1176rd2.m213916a2(dr0Var2);
                c1176rd2.f59682b4.remove(dr0Var2);
                c1176rd2.m214529a8();
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        switch (this.f59561a0) {
            case 0:
                this.f59565a4.getClass();
                break;
            default:
                this.f59565a4.getClass();
                break;
        }
    }

    public C1160qy(C1176rd c1176rd, dr0 dr0Var, View view, ViewPropertyAnimator viewPropertyAnimator) {
        this.f59565a4 = c1176rd;
        this.f59562a1 = dr0Var;
        this.f59563a2 = view;
        this.f59564a3 = viewPropertyAnimator;
    }
}
