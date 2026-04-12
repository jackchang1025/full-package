package p000;

import androidx.constraintlayout.motion.widget.MotionLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class rg0 extends pg0 {

    /* renamed from: a0 */
    public float f59767a0 = 0.0f;

    /* renamed from: a1 */
    public float f59768a1 = 0.0f;

    /* renamed from: a2 */
    public float f59769a2;

    /* renamed from: a3 */
    public final /* synthetic */ MotionLayout f59770a3;

    public rg0(MotionLayout motionLayout) {
        this.f59770a3 = motionLayout;
    }

    @Override // p000.pg0
    /* renamed from: a0 */
    public final float mo212548a0() {
        return this.f59770a3.f44527c1;
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        float f2 = this.f59767a0;
        MotionLayout motionLayout = this.f59770a3;
        if (f2 > 0.0f) {
            float f3 = this.f59769a2;
            if (f2 / f3 < f) {
                f = f2 / f3;
            }
            motionLayout.f44527c1 = f2 - (f3 * f);
            return ((f2 * f) - (((f3 * f) * f) / 2.0f)) + this.f59768a1;
        }
        float f4 = this.f59769a2;
        if ((-f2) / f4 < f) {
            f = (-f2) / f4;
        }
        motionLayout.f44527c1 = (f4 * f) + f2;
        return (((f4 * f) * f) / 2.0f) + (f2 * f) + this.f59768a1;
    }
}
