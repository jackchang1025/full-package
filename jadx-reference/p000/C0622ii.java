package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ii */
/* loaded from: classes2.dex */
public final class C0622ii extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f56891a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0623ij f56892a1;

    public /* synthetic */ C0622ii(C0623ij c0623ij, int i) {
        this.f56891a0 = i;
        this.f56892a1 = c0623ij;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        switch (this.f56891a0) {
            case 1:
                super.onAnimationEnd(animator);
                C0623ij c0623ij = this.f56892a1;
                c0623ij.mo212538a0();
                C0410dc c0410dc = c0623ij.f56905b0;
                if (c0410dc != null) {
                    c0410dc.m212579a0((n50) c0623ij.f55538a0);
                    break;
                }
                break;
            default:
                super.onAnimationEnd(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        switch (this.f56891a0) {
            case 0:
                super.onAnimationRepeat(animator);
                C0623ij c0623ij = this.f56892a1;
                c0623ij.f56902a7 = (c0623ij.f56902a7 + 4) % c0623ij.f56901a6.f55695a2.length;
                break;
            default:
                super.onAnimationRepeat(animator);
                break;
        }
    }
}
