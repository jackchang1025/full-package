package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yg */
/* loaded from: classes2.dex */
public final class C1456yg extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f61310a0 = 1;

    /* renamed from: a1 */
    public final /* synthetic */ boolean f61311a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f61312a2;

    /* renamed from: a3 */
    public final /* synthetic */ Object f61313a3;

    public C1456yg(boolean z, View view, View view2) {
        this.f61311a1 = z;
        this.f61312a2 = view;
        this.f61313a3 = view2;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f61310a0) {
            case 0:
                View view = (View) this.f61313a3;
                if (!this.f61311a1) {
                    ((View) this.f61312a2).setVisibility(4);
                    view.setAlpha(1.0f);
                    view.setVisibility(0);
                    break;
                }
                break;
            default:
                AbstractC1535zy abstractC1535zy = (AbstractC1535zy) this.f61313a3;
                abstractC1535zy.f61628b7 = 0;
                abstractC1535zy.f61622b1 = null;
                og1 og1Var = (og1) this.f61312a2;
                if (og1Var != null) {
                    ((b81) og1Var.f58832a0).mo210606d9();
                    break;
                }
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        switch (this.f61310a0) {
            case 0:
                View view = (View) this.f61313a3;
                if (this.f61311a1) {
                    ((View) this.f61312a2).setVisibility(0);
                    view.setAlpha(0.0f);
                    view.setVisibility(4);
                    break;
                }
                break;
            default:
                AbstractC1535zy abstractC1535zy = (AbstractC1535zy) this.f61313a3;
                abstractC1535zy.f61632c1.m211057a0(0, this.f61311a1);
                abstractC1535zy.f61628b7 = 2;
                abstractC1535zy.f61622b1 = animator;
                break;
        }
    }

    public C1456yg(AbstractC1535zy abstractC1535zy, boolean z, og1 og1Var) {
        this.f61313a3 = abstractC1535zy;
        this.f61311a1 = z;
        this.f61312a2 = og1Var;
    }
}
