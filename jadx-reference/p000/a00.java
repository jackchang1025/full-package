package p000;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class a00 extends AnimationSet implements Runnable {

    /* renamed from: a0 */
    public final ViewGroup f1a0;

    /* renamed from: a1 */
    public final View f2a1;

    /* renamed from: a2 */
    public boolean f3a2;

    /* renamed from: a3 */
    public boolean f4a3;

    /* renamed from: a4 */
    public boolean f5a4;

    public a00(Animation animation, ViewGroup viewGroup, View view) {
        super(false);
        this.f5a4 = true;
        this.f1a0 = viewGroup;
        this.f2a1 = view;
        addAnimation(animation);
        viewGroup.post(this);
    }

    @Override // android.view.animation.AnimationSet, android.view.animation.Animation
    public final boolean getTransformation(long j, Transformation transformation) {
        this.f5a4 = true;
        if (this.f3a2) {
            return !this.f4a3;
        }
        if (!super.getTransformation(j, transformation)) {
            this.f3a2 = true;
            el0.m212695a0(this.f1a0, this);
        }
        return true;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z = this.f3a2;
        ViewGroup viewGroup = this.f1a0;
        if (z || !this.f5a4) {
            viewGroup.endViewTransition(this.f2a1);
            this.f4a3 = true;
        } else {
            this.f5a4 = false;
            viewGroup.post(this);
        }
    }

    @Override // android.view.animation.Animation
    public final boolean getTransformation(long j, Transformation transformation, float f) {
        this.f5a4 = true;
        if (this.f3a2) {
            return !this.f4a3;
        }
        if (!super.getTransformation(j, transformation, f)) {
            this.f3a2 = true;
            el0.m212695a0(this.f1a0, this);
        }
        return true;
    }
}
