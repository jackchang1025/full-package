package p000;

import android.os.Build;
import android.view.animation.Interpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class jf1 {

    /* renamed from: a0 */
    public if1 f57330a0;

    public jf1(int i, Interpolator interpolator, long j) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.f57330a0 = new hf1(AbstractC0740k0.m213374a9(i, interpolator, j));
        } else {
            this.f57330a0 = new ff1(i, interpolator, j);
        }
    }
}
