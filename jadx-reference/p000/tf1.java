package p000;

import android.view.WindowInsets;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class tf1 extends sf1 {

    /* renamed from: b3 */
    public f60 f60212b3;

    /* renamed from: b4 */
    public f60 f60213b4;

    /* renamed from: b5 */
    public f60 f60214b5;

    public tf1(xf1 xf1Var, WindowInsets windowInsets) {
        super(xf1Var, windowInsets);
        this.f60212b3 = null;
        this.f60213b4 = null;
        this.f60214b5 = null;
    }

    @Override // p000.vf1
    /* renamed from: a6 */
    public f60 mo214739a6() {
        if (this.f60213b4 == null) {
            this.f60213b4 = f60.m212749a2(this.f59497a2.getMandatorySystemGestureInsets());
        }
        return this.f60213b4;
    }

    @Override // p000.vf1
    /* renamed from: a8 */
    public f60 mo214740a8() {
        if (this.f60212b3 == null) {
            this.f60212b3 = f60.m212749a2(this.f59497a2.getSystemGestureInsets());
        }
        return this.f60212b3;
    }

    @Override // p000.vf1
    /* renamed from: b0 */
    public f60 mo214741b0() {
        if (this.f60214b5 == null) {
            this.f60214b5 = f60.m212749a2(this.f59497a2.getTappableElementInsets());
        }
        return this.f60214b5;
    }

    @Override // p000.qf1, p000.vf1
    /* renamed from: b1 */
    public xf1 mo214393b1(int i, int i2, int i3, int i4) {
        return xf1.m215170a6(null, this.f59497a2.inset(i, i2, i3, i4));
    }

    @Override // p000.rf1, p000.vf1
    /* renamed from: b6 */
    public void mo214539b6(f60 f60Var) {
    }
}
