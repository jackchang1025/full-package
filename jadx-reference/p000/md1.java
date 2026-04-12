package p000;

import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class md1 extends b40 {

    /* renamed from: h4 */
    public int f58335h4 = 0;

    /* renamed from: h5 */
    public int f58336h5 = 0;

    /* renamed from: h6 */
    public int f58337h6 = 0;

    /* renamed from: h7 */
    public int f58338h7 = 0;

    /* renamed from: h8 */
    public int f58339h8 = 0;

    /* renamed from: h9 */
    public int f58340h9 = 0;

    /* renamed from: i0 */
    public boolean f58341i0 = false;

    /* renamed from: i1 */
    public int f58342i1 = 0;

    /* renamed from: i2 */
    public int f58343i2 = 0;

    /* renamed from: i3 */
    public final C0418dj f58344i3 = new C0418dj();

    /* renamed from: i4 */
    public C0813la f58345i4 = null;

    @Override // p000.b40
    /* renamed from: e6 */
    public final void mo210538e6() {
        for (int i = 0; i < this.f45712h3; i++) {
            C0829lq c0829lq = this.f45711h2[i];
            if (c0829lq != null) {
                c0829lq.f58093d2 = true;
            }
        }
    }

    /* renamed from: e7 */
    public abstract void mo210752e7(int i, int i2, int i3, int i4);

    /* renamed from: e8 */
    public final void m213972e8(C0829lq c0829lq, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour, int i, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2, int i2) {
        C0813la c0813la;
        C0829lq c0829lq2;
        while (true) {
            c0813la = this.f58345i4;
            if (c0813la != null || (c0829lq2 = this.f58108e7) == null) {
                break;
            } else {
                this.f58345i4 = ((C0830lr) c0829lq2).f58143h6;
            }
        }
        C0418dj c0418dj = this.f58344i3;
        c0418dj.f55819a0 = constraintWidget$DimensionBehaviour;
        c0418dj.f55820a1 = constraintWidget$DimensionBehaviour2;
        c0418dj.f55821a2 = i;
        c0418dj.f55822a3 = i2;
        c0813la.m213800a1(c0829lq, c0418dj);
        c0829lq.m213911e1(c0418dj.f55823a4);
        c0829lq.m213908d8(c0418dj.f55824a5);
        c0829lq.f58091d0 = c0418dj.f55826a7;
        c0829lq.m213905d5(c0418dj.f55825a6);
    }
}
