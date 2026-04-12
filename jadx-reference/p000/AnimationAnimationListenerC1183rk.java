package p000;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.fragment.app.C0067a3;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: rk */
/* loaded from: classes.dex */
public final class AnimationAnimationListenerC1183rk implements Animation.AnimationListener {

    /* renamed from: a0 */
    public final /* synthetic */ ViewGroup f59788a0;

    /* renamed from: a1 */
    public final /* synthetic */ View f59789a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0067a3 f59790a2;

    public AnimationAnimationListenerC1183rk(ViewGroup viewGroup, View view, C0067a3 c0067a3) {
        this.f59788a0 = viewGroup;
        this.f59789a1 = view;
        this.f59790a2 = c0067a3;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationEnd(Animation animation) {
        this.f59788a0.post(new RunnableC0165ca(5, this));
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationStart(Animation animation) {
    }
}
