package p000;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t7 */
/* loaded from: classes2.dex */
public abstract class AbstractC1249t7 {

    /* renamed from: a0 */
    public static final LinearInterpolator f60178a0 = new LinearInterpolator();

    /* renamed from: a1 */
    public static final C1487yo f60179a1 = new C1487yo(1);

    /* renamed from: a2 */
    public static final C1487yo f60180a2 = new C1487yo(0);

    /* renamed from: a3 */
    public static final C1487yo f60181a3 = new C1487yo(C1487yo.f61348a4);

    /* renamed from: a4 */
    public static final DecelerateInterpolator f60182a4 = new DecelerateInterpolator();

    /* renamed from: a0 */
    public static float m214727a0(float f, float f2, float f3) {
        return AbstractC0003a2.m19a0(f2, f, f3, f);
    }

    /* renamed from: a1 */
    public static float m214728a1(float f, float f2, float f3, float f4, float f5) {
        return f5 <= f3 ? f : f5 >= f4 ? f2 : m214727a0(f, f2, (f5 - f3) / (f4 - f3));
    }

    /* renamed from: a2 */
    public static int m214729a2(int i, float f, int i2) {
        return Math.round(f * (i2 - i)) + i;
    }
}
