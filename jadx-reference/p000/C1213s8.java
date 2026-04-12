package p000;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: s8 */
/* loaded from: classes2.dex */
public final class C1213s8 extends Animatable2.AnimationCallback {

    /* renamed from: a0 */
    public final /* synthetic */ C0410dc f59918a0;

    public C1213s8(C0410dc c0410dc) {
        this.f59918a0 = c0410dc;
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationEnd(Drawable drawable) {
        this.f59918a0.m212579a0(drawable);
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationStart(Drawable drawable) {
        this.f59918a0.m212580a1(drawable);
    }
}
