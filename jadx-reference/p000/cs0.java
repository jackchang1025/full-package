package p000;

import android.animation.TimeInterpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class cs0 implements TimeInterpolator {

    /* renamed from: a0 */
    public final TimeInterpolator f55501a0;

    public cs0(TimeInterpolator timeInterpolator) {
        this.f55501a0 = timeInterpolator;
    }

    /* renamed from: a0 */
    public static TimeInterpolator m212525a0(boolean z, TimeInterpolator timeInterpolator) {
        return z ? timeInterpolator : new cs0(timeInterpolator);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return 1.0f - this.f55501a0.getInterpolation(f);
    }
}
