package p000;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zg0 {

    /* renamed from: a0 */
    public long f61545a0;

    /* renamed from: a2 */
    public TimeInterpolator f61547a2 = null;

    /* renamed from: a3 */
    public int f61548a3 = 0;

    /* renamed from: a4 */
    public int f61549a4 = 1;

    /* renamed from: a1 */
    public long f61546a1 = 150;

    public zg0(long j) {
        this.f61545a0 = j;
    }

    /* renamed from: a0 */
    public final void m215402a0(ObjectAnimator objectAnimator) {
        objectAnimator.setStartDelay(this.f61545a0);
        objectAnimator.setDuration(this.f61546a1);
        objectAnimator.setInterpolator(m215403a1());
        objectAnimator.setRepeatCount(this.f61548a3);
        objectAnimator.setRepeatMode(this.f61549a4);
    }

    /* renamed from: a1 */
    public final TimeInterpolator m215403a1() {
        TimeInterpolator timeInterpolator = this.f61547a2;
        return timeInterpolator != null ? timeInterpolator : AbstractC1249t7.f60179a1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zg0)) {
            return false;
        }
        zg0 zg0Var = (zg0) obj;
        if (this.f61545a0 == zg0Var.f61545a0 && this.f61546a1 == zg0Var.f61546a1 && this.f61548a3 == zg0Var.f61548a3 && this.f61549a4 == zg0Var.f61549a4) {
            return m215403a1().getClass().equals(zg0Var.m215403a1().getClass());
        }
        return false;
    }

    public final int hashCode() {
        long j = this.f61545a0;
        long j2 = this.f61546a1;
        return ((((m215403a1().getClass().hashCode() + (((((int) (j ^ (j >>> 32))) * 31) + ((int) ((j2 >>> 32) ^ j2))) * 31)) * 31) + this.f61548a3) * 31) + this.f61549a4;
    }

    public final String toString() {
        return "\n" + zg0.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " delay: " + this.f61545a0 + " duration: " + this.f61546a1 + " interpolator: " + m215403a1().getClass() + " repeatCount: " + this.f61548a3 + " repeatMode: " + this.f61549a4 + "}\n";
    }
}
