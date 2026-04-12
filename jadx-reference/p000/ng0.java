package p000;

import android.view.animation.Interpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ng0 implements Interpolator {

    /* renamed from: a0 */
    public final /* synthetic */ int f58627a0;

    /* renamed from: a1 */
    public final /* synthetic */ C1347vr f58628a1;

    public /* synthetic */ ng0(C1347vr c1347vr, int i) {
        this.f58627a0 = i;
        this.f58628a1 = c1347vr;
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        double dMo210531a0;
        switch (this.f58627a0) {
            case 0:
                dMo210531a0 = this.f58628a1.mo210531a0(f);
                break;
            case 1:
                dMo210531a0 = this.f58628a1.mo210531a0(f);
                break;
            default:
                dMo210531a0 = this.f58628a1.mo210531a0(f);
                break;
        }
        return (float) dMo210531a0;
    }
}
