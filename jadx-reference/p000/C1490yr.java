package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.search.C0224a6;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yr */
/* loaded from: classes.dex */
public final class C1490yr extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f61361a0;

    /* renamed from: a1 */
    public boolean f61362a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f61363a2;

    public C1490yr(C0224a6 c0224a6, boolean z) {
        this.f61361a0 = 1;
        this.f61363a2 = c0224a6;
        this.f61362a1 = z;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f61361a0) {
            case 0:
                this.f61362a1 = true;
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f61361a0) {
            case 0:
                C1491ys c1491ys = (C1491ys) this.f61363a2;
                if (!this.f61362a1) {
                    if (((Float) c1491ys.f61394c5.getAnimatedValue()).floatValue() != 0.0f) {
                        c1491ys.f61395c6 = 2;
                        c1491ys.f61387b8.invalidate();
                        break;
                    } else {
                        c1491ys.f61395c6 = 0;
                        c1491ys.m215309a5(0);
                        break;
                    }
                } else {
                    this.f61362a1 = false;
                    break;
                }
            default:
                C0224a6 c0224a6 = (C0224a6) this.f61363a2;
                boolean z = this.f61362a1;
                C0224a6.m211092a0(c0224a6, z ? 1.0f : 0.0f);
                if (z) {
                    ClippableRoundedCornerLayout clippableRoundedCornerLayout = c0224a6.f49768a2;
                    clippableRoundedCornerLayout.f49542a0 = null;
                    clippableRoundedCornerLayout.invalidate();
                    break;
                }
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f61361a0) {
            case 1:
                C0224a6.m211092a0((C0224a6) this.f61363a2, this.f61362a1 ? 0.0f : 1.0f);
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }

    public C1490yr(C1491ys c1491ys) {
        this.f61361a0 = 0;
        this.f61363a2 = c1491ys;
        this.f61362a1 = false;
    }
}
