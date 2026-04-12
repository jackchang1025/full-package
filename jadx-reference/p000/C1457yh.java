package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yh */
/* loaded from: classes2.dex */
public final class C1457yh extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f61318a0;

    /* renamed from: a1 */
    public final View f61319a1;

    /* renamed from: a2 */
    public boolean f61320a2;

    public C1457yh(View view, boolean z) {
        this.f61318a0 = 0;
        this.f61320a2 = z;
        this.f61319a1 = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f61318a0) {
            case 0:
                if (!this.f61320a2) {
                    this.f61319a1.setVisibility(4);
                    break;
                }
                break;
            default:
                jd1 jd1Var = hd1.f56654a0;
                View view = this.f61319a1;
                jd1Var.mo213495e9(view, 1.0f);
                if (this.f61320a2) {
                    view.setLayerType(0, null);
                    break;
                }
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        int i = this.f61318a0;
        View view = this.f61319a1;
        switch (i) {
            case 0:
                if (this.f61320a2) {
                    view.setVisibility(0);
                    break;
                }
                break;
            default:
                WeakHashMap weakHashMap = xa1.f61054a0;
                if (fa1.m212770a7(view) && view.getLayerType() == 0) {
                    this.f61320a2 = true;
                    view.setLayerType(2, null);
                    break;
                }
                break;
        }
    }

    public C1457yh(View view) {
        this.f61318a0 = 1;
        this.f61320a2 = false;
        this.f61319a1 = view;
    }
}
