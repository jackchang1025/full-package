package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import com.storm.safe.rock.service.modules.cipher.C0336a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class vm0 extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f60656a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f60657a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f60658a2;

    public /* synthetic */ vm0(Object obj, View view, int i) {
        this.f60656a0 = i;
        this.f60657a1 = obj;
        this.f60658a2 = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f60656a0) {
            case 2:
                ((oc1) this.f60657a1).mo212659a1((View) this.f60658a2);
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f60656a0) {
            case 0:
                t60.m214695b6(animator, "animation");
                tm0 tm0Var = (tm0) this.f60657a1;
                tm0Var.f60243a4 = null;
                tm0Var.f60240a1 = true;
                tm0Var.f60239a0 = ((C0336a2) this.f60658a2).f53320a9;
                tm0Var.f60241a2 = Float.MIN_VALUE;
                tm0Var.f60242a3 = Float.MIN_VALUE;
                break;
            case 1:
                ((C0130bd) this.f60657a1).remove(animator);
                ((s71) this.f60658a2).f59911b2.remove(animator);
                break;
            case 2:
                ((oc1) this.f60657a1).mo212658a0();
                break;
            default:
                ((jf1) this.f60657a1).f57330a0.mo213036a3(1.0f);
                ff1.m212801a4((View) this.f60658a2);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f60656a0) {
            case 1:
                ((s71) this.f60658a2).f59911b2.add(animator);
                break;
            case 2:
                ((oc1) this.f60657a1).mo212660a2();
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }

    public vm0(s71 s71Var, C0130bd c0130bd) {
        this.f60656a0 = 1;
        this.f60658a2 = s71Var;
        this.f60657a1 = c0130bd;
    }
}
