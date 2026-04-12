package p000;

import android.view.WindowInsetsAnimation;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class hf1 extends if1 {

    /* renamed from: a4 */
    public final WindowInsetsAnimation f56665a4;

    public hf1(WindowInsetsAnimation windowInsetsAnimation) {
        super(0, null, 0L);
        this.f56665a4 = windowInsetsAnimation;
    }

    @Override // p000.if1
    /* renamed from: a0 */
    public final long mo213033a0() {
        return this.f56665a4.getDurationMillis();
    }

    @Override // p000.if1
    /* renamed from: a1 */
    public final float mo213034a1() {
        return this.f56665a4.getInterpolatedFraction();
    }

    @Override // p000.if1
    /* renamed from: a2 */
    public final int mo213035a2() {
        return this.f56665a4.getTypeMask();
    }

    @Override // p000.if1
    /* renamed from: a3 */
    public final void mo213036a3(float f) {
        this.f56665a4.setFraction(f);
    }
}
