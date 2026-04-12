package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zr */
/* loaded from: classes2.dex */
public final class C1527zr extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public boolean f61565a0;

    /* renamed from: a1 */
    public final /* synthetic */ boolean f61566a1;

    /* renamed from: a2 */
    public final /* synthetic */ og1 f61567a2;

    /* renamed from: a3 */
    public final /* synthetic */ AbstractC1535zy f61568a3;

    public C1527zr(AbstractC1535zy abstractC1535zy, boolean z, og1 og1Var) {
        this.f61568a3 = abstractC1535zy;
        this.f61566a1 = z;
        this.f61567a2 = og1Var;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f61565a0 = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        AbstractC1535zy abstractC1535zy = this.f61568a3;
        abstractC1535zy.f61628b7 = 0;
        abstractC1535zy.f61622b1 = null;
        if (this.f61565a0) {
            return;
        }
        FloatingActionButton floatingActionButton = abstractC1535zy.f61632c1;
        boolean z = this.f61566a1;
        floatingActionButton.m211057a0(z ? 8 : 4, z);
        og1 og1Var = this.f61567a2;
        if (og1Var != null) {
            ((b81) og1Var.f58832a0).mo210605d8((FloatingActionButton) og1Var.f58833a1);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        AbstractC1535zy abstractC1535zy = this.f61568a3;
        abstractC1535zy.f61632c1.m211057a0(0, this.f61566a1);
        abstractC1535zy.f61628b7 = 1;
        abstractC1535zy.f61622b1 = animator;
        this.f61565a0 = false;
    }
}
