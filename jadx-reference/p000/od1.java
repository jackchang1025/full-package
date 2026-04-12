package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class od1 extends AnimatorListenerAdapter implements r71 {

    /* renamed from: a0 */
    public final View f58784a0;

    /* renamed from: a1 */
    public final int f58785a1;

    /* renamed from: a2 */
    public final ViewGroup f58786a2;

    /* renamed from: a4 */
    public boolean f58788a4;

    /* renamed from: a5 */
    public boolean f58789a5 = false;

    /* renamed from: a3 */
    public final boolean f58787a3 = true;

    public od1(View view, int i) {
        this.f58784a0 = view;
        this.f58785a1 = i;
        this.f58786a2 = (ViewGroup) view.getParent();
        m214187a5(true);
    }

    @Override // p000.r71
    /* renamed from: a1 */
    public final void mo212983a1() {
        m214187a5(false);
    }

    @Override // p000.r71
    /* renamed from: a2 */
    public final void mo212984a2() {
        m214187a5(true);
    }

    @Override // p000.r71
    /* renamed from: a3 */
    public final void mo212985a3(s71 s71Var) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (!this.f58789a5) {
            hd1.f56654a0.mo213284f0(this.f58784a0, this.f58785a1);
            ViewGroup viewGroup = this.f58786a2;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
        m214187a5(false);
        s71Var.m214581c0(this);
    }

    /* renamed from: a5 */
    public final void m214187a5(boolean z) {
        ViewGroup viewGroup;
        if (!this.f58787a3 || this.f58788a4 == z || (viewGroup = this.f58786a2) == null) {
            return;
        }
        this.f58788a4 = z;
        b81.m210599f2(viewGroup, z);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f58789a5 = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (!this.f58789a5) {
            hd1.f56654a0.mo213284f0(this.f58784a0, this.f58785a1);
            ViewGroup viewGroup = this.f58786a2;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
        m214187a5(false);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public final void onAnimationPause(Animator animator) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (this.f58789a5) {
            return;
        }
        hd1.f56654a0.mo213284f0(this.f58784a0, this.f58785a1);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public final void onAnimationResume(Animator animator) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (this.f58789a5) {
            return;
        }
        hd1.f56654a0.mo213284f0(this.f58784a0, 0);
    }

    @Override // p000.r71
    /* renamed from: a0 */
    public final void mo214186a0() {
    }

    @Override // p000.r71
    /* renamed from: a4 */
    public final void mo212986a4() {
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationRepeat(Animator animator) {
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
    }
}
