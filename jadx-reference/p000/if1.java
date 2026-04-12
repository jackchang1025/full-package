package p000;

import android.view.animation.Interpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class if1 {

    /* renamed from: a0 */
    public final int f56880a0;

    /* renamed from: a1 */
    public float f56881a1;

    /* renamed from: a2 */
    public final Interpolator f56882a2;

    /* renamed from: a3 */
    public final long f56883a3;

    public if1(int i, Interpolator interpolator, long j) {
        this.f56880a0 = i;
        this.f56882a2 = interpolator;
        this.f56883a3 = j;
    }

    /* renamed from: a0 */
    public long mo213033a0() {
        return this.f56883a3;
    }

    /* renamed from: a1 */
    public float mo213034a1() {
        Interpolator interpolator = this.f56882a2;
        return interpolator != null ? interpolator.getInterpolation(this.f56881a1) : this.f56881a1;
    }

    /* renamed from: a2 */
    public int mo213035a2() {
        return this.f56880a0;
    }

    /* renamed from: a3 */
    public void mo213036a3(float f) {
        this.f56881a1 = f;
    }
}
