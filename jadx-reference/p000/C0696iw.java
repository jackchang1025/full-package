package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: iw */
/* loaded from: classes2.dex */
public final class C0696iw extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f57234a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0697ix f57235a1;

    public /* synthetic */ C0696iw(C0697ix c0697ix, int i) {
        this.f57234a0 = i;
        this.f57235a1 = c0697ix;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        switch (this.f57234a0) {
            case 1:
                this.f57235a1.f61104a1.m215162a6(false);
                break;
            default:
                super.onAnimationEnd(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f57234a0) {
            case 0:
                this.f57235a1.f61104a1.m215162a6(true);
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }
}
