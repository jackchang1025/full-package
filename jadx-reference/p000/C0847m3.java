package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.transformation.ExpandableTransformationBehavior;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: m3 */
/* loaded from: classes.dex */
public final class C0847m3 extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f58247a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58248a1;

    public /* synthetic */ C0847m3(int i, Object obj) {
        this.f58247a0 = i;
        this.f58248a1 = obj;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f58247a0) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.f58248a1;
                actionBarOverlayLayout.f43858c2 = null;
                actionBarOverlayLayout.f43846b0 = false;
                break;
            case 4:
                ((AbstractC0408da) this.f58248a1).mo212568a3();
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        switch (this.f58247a0) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.f58248a1;
                actionBarOverlayLayout.f43858c2 = null;
                actionBarOverlayLayout.f43846b0 = false;
                break;
            case 1:
                C1246t4 c1246t4 = (C1246t4) this.f58248a1;
                ArrayList arrayList = new ArrayList(c1246t4.f60138a4);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((C0410dc) arrayList.get(i)).m212579a0(c1246t4);
                }
                break;
            case 2:
                C1309uq c1309uq = (C1309uq) this.f58248a1;
                c1309uq.m215176b5();
                c1309uq.f60503b7.start();
                break;
            case 3:
                ((ExpandableTransformationBehavior) this.f58248a1).f50054a1 = null;
                break;
            case 4:
                ((AbstractC0408da) this.f58248a1).mo212569a4();
                break;
            case 5:
                ((HideBottomViewOnScrollBehavior) this.f58248a1).f49137a8 = null;
                break;
            case 6:
            default:
                super.onAnimationEnd(animator);
                break;
            case 7:
                t60.m214695b6(animator, "animation");
                ((w00) this.f58248a1).invoke();
                break;
            case 8:
                zg1 zg1Var = (zg1) this.f58248a1;
                if (((ValueAnimator) zg1Var.f61552a1) == animator) {
                    zg1Var.f61552a1 = null;
                    break;
                }
                break;
            case 9:
                ((s71) this.f58248a1).m214576b1();
                animator.removeListener(this);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        switch (this.f58247a0) {
            case 6:
                super.onAnimationRepeat(animator);
                sa0 sa0Var = (sa0) this.f58248a1;
                sa0Var.f59945a6 = (sa0Var.f59945a6 + 1) % sa0Var.f59944a5.f55695a2.length;
                sa0Var.f59946a7 = true;
                break;
            default:
                super.onAnimationRepeat(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f58247a0) {
            case 1:
                C1246t4 c1246t4 = (C1246t4) this.f58248a1;
                ArrayList arrayList = new ArrayList(c1246t4.f60138a4);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((C0410dc) arrayList.get(i)).m212580a1(c1246t4);
                }
                break;
            case 4:
                ((AbstractC0408da) this.f58248a1).mo212570a5(animator);
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }
}
