package p000;

import androidx.constraintlayout.core.widgets.ConstraintAnchor$Type;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class mn0 extends md1 {
    @Override // p000.C0829lq
    /* renamed from: a1 */
    public final void mo210751a1(ab0 ab0Var, boolean z) {
        super.mo210751a1(ab0Var, z);
        if (this.f45712h3 > 0) {
            C0829lq c0829lq = this.f45711h2[0];
            c0829lq.m213902d0();
            c0829lq.f58119f8 = 0.5f;
            c0829lq.f58118f7 = 0.5f;
            ConstraintAnchor$Type constraintAnchor$Type = ConstraintAnchor$Type.f44415a0;
            c0829lq.m213882a5(constraintAnchor$Type, this, constraintAnchor$Type, 0);
            ConstraintAnchor$Type constraintAnchor$Type2 = ConstraintAnchor$Type.f44417a2;
            c0829lq.m213882a5(constraintAnchor$Type2, this, constraintAnchor$Type2, 0);
            ConstraintAnchor$Type constraintAnchor$Type3 = ConstraintAnchor$Type.f44416a1;
            c0829lq.m213882a5(constraintAnchor$Type3, this, constraintAnchor$Type3, 0);
            ConstraintAnchor$Type constraintAnchor$Type4 = ConstraintAnchor$Type.f44418a3;
            c0829lq.m213882a5(constraintAnchor$Type4, this, constraintAnchor$Type4, 0);
        }
    }

    @Override // p000.md1
    /* renamed from: e7 */
    public final void mo210752e7(int i, int i2, int i3, int i4) {
        int iM213891b7 = this.f58339h8 + this.f58340h9;
        int iM213887b1 = this.f58335h4 + this.f58336h5;
        if (this.f45712h3 > 0) {
            iM213891b7 += this.f45711h2[0].m213891b7();
            iM213887b1 += this.f45711h2[0].m213887b1();
        }
        int iMax = Math.max(this.f58116f5, iM213891b7);
        int iMax2 = Math.max(this.f58117f6, iM213887b1);
        if (i != 1073741824) {
            i2 = i == Integer.MIN_VALUE ? Math.min(iMax, i2) : i == 0 ? iMax : 0;
        }
        if (i3 != 1073741824) {
            i4 = i3 == Integer.MIN_VALUE ? Math.min(iMax2, i4) : i3 == 0 ? iMax2 : 0;
        }
        this.f58342i1 = i2;
        this.f58343i2 = i4;
        m213911e1(i2);
        m213908d8(i4);
        this.f58341i0 = this.f45712h3 > 0;
    }
}
