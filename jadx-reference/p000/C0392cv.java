package p000;

import androidx.constraintlayout.core.widgets.ConstraintAnchor$Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: cv */
/* loaded from: classes.dex */
public final class C0392cv extends b40 {

    /* renamed from: h4 */
    public int f55531h4 = 0;

    /* renamed from: h5 */
    public boolean f55532h5 = true;

    /* renamed from: h6 */
    public int f55533h6 = 0;

    /* renamed from: h7 */
    public boolean f55534h7 = false;

    @Override // p000.C0829lq
    /* renamed from: a1 */
    public final void mo210751a1(ab0 ab0Var, boolean z) {
        boolean z2;
        int i;
        C0797kv[] c0797kvArr = this.f58104e3;
        C0797kv c0797kv = this.f58096d5;
        c0797kvArr[0] = c0797kv;
        int i2 = 2;
        C0797kv c0797kv2 = this.f58097d6;
        c0797kvArr[2] = c0797kv2;
        C0797kv c0797kv3 = this.f58098d7;
        c0797kvArr[1] = c0797kv3;
        C0797kv c0797kv4 = this.f58099d8;
        c0797kvArr[3] = c0797kv4;
        for (C0797kv c0797kv5 : c0797kvArr) {
            c0797kv5.f57729a8 = ab0Var.m209769b0(c0797kv5);
        }
        int i3 = this.f55531h4;
        if (i3 < 0 || i3 >= 4) {
            return;
        }
        C0797kv c0797kv6 = c0797kvArr[i3];
        if (!this.f55534h7) {
            m212535e7();
        }
        if (this.f55534h7) {
            this.f55534h7 = false;
            int i4 = this.f55531h4;
            if (i4 == 0 || i4 == 1) {
                ab0Var.m209762a3(c0797kv.f57729a8, this.f58113f2);
                ab0Var.m209762a3(c0797kv3.f57729a8, this.f58113f2);
                return;
            } else {
                if (i4 == 2 || i4 == 3) {
                    ab0Var.m209762a3(c0797kv2.f57729a8, this.f58114f3);
                    ab0Var.m209762a3(c0797kv4.f57729a8, this.f58114f3);
                    return;
                }
                return;
            }
        }
        for (int i5 = 0; i5 < this.f45712h3; i5++) {
            C0829lq c0829lq = this.f45711h2[i5];
            if (this.f55532h5 || c0829lq.mo212532a2()) {
                int i6 = this.f55531h4;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
                if (((i6 == 0 || i6 == 1) && c0829lq.f58107e6[0] == constraintWidget$DimensionBehaviour && c0829lq.f58096d5.f57726a5 != null && c0829lq.f58098d7.f57726a5 != null) || ((i6 == 2 || i6 == 3) && c0829lq.f58107e6[1] == constraintWidget$DimensionBehaviour && c0829lq.f58097d6.f57726a5 != null && c0829lq.f58099d8.f57726a5 != null)) {
                    z2 = true;
                    break;
                }
            }
        }
        z2 = false;
        boolean z3 = c0797kv.m213752a6() || c0797kv3.m213752a6();
        boolean z4 = c0797kv2.m213752a6() || c0797kv4.m213752a6();
        int i7 = !(!z2 && (((i = this.f55531h4) == 0 && z3) || ((i == 2 && z4) || ((i == 1 && z3) || (i == 3 && z4))))) ? 4 : 5;
        int i8 = 0;
        while (i8 < this.f45712h3) {
            C0829lq c0829lq2 = this.f45711h2[i8];
            if (this.f55532h5 || c0829lq2.mo212532a2()) {
                e11 e11VarM209769b0 = ab0Var.m209769b0(c0829lq2.f58104e3[this.f55531h4]);
                C0797kv[] c0797kvArr2 = c0829lq2.f58104e3;
                int i9 = this.f55531h4;
                C0797kv c0797kv7 = c0797kvArr2[i9];
                c0797kv7.f57729a8 = e11VarM209769b0;
                C0797kv c0797kv8 = c0797kv7.f57726a5;
                int i10 = (c0797kv8 == null || c0797kv8.f57724a3 != this) ? 0 : c0797kv7.f57727a6;
                if (i9 == 0 || i9 == i2) {
                    e11 e11Var = c0797kv6.f57729a8;
                    int i11 = this.f55533h6 - i10;
                    C0131be c0131beM209770b1 = ab0Var.m209770b1();
                    e11 e11VarM209771b2 = ab0Var.m209771b2();
                    e11VarM209771b2.f55899a3 = 0;
                    c0131beM209770b1.m210676a2(e11Var, e11VarM209769b0, e11VarM209771b2, i11);
                    ab0Var.m209761a2(c0131beM209770b1);
                } else {
                    e11 e11Var2 = c0797kv6.f57729a8;
                    int i12 = this.f55533h6 + i10;
                    C0131be c0131beM209770b12 = ab0Var.m209770b1();
                    e11 e11VarM209771b22 = ab0Var.m209771b2();
                    e11VarM209771b22.f55899a3 = 0;
                    c0131beM209770b12.m210675a1(e11Var2, e11VarM209769b0, e11VarM209771b22, i12);
                    ab0Var.m209761a2(c0131beM209770b12);
                }
                ab0Var.m209763a4(c0797kv6.f57729a8, e11VarM209769b0, this.f55533h6 + i10, i7);
            }
            i8++;
            i2 = 2;
        }
        int i13 = this.f55531h4;
        if (i13 == 0) {
            ab0Var.m209763a4(c0797kv3.f57729a8, c0797kv.f57729a8, 0, 8);
            ab0Var.m209763a4(c0797kv.f57729a8, this.f58108e7.f58098d7.f57729a8, 0, 4);
            ab0Var.m209763a4(c0797kv.f57729a8, this.f58108e7.f58096d5.f57729a8, 0, 0);
            return;
        }
        if (i13 == 1) {
            ab0Var.m209763a4(c0797kv.f57729a8, c0797kv3.f57729a8, 0, 8);
            ab0Var.m209763a4(c0797kv.f57729a8, this.f58108e7.f58096d5.f57729a8, 0, 4);
            ab0Var.m209763a4(c0797kv.f57729a8, this.f58108e7.f58098d7.f57729a8, 0, 0);
        } else if (i13 == 2) {
            ab0Var.m209763a4(c0797kv4.f57729a8, c0797kv2.f57729a8, 0, 8);
            ab0Var.m209763a4(c0797kv2.f57729a8, this.f58108e7.f58099d8.f57729a8, 0, 4);
            ab0Var.m209763a4(c0797kv2.f57729a8, this.f58108e7.f58097d6.f57729a8, 0, 0);
        } else if (i13 == 3) {
            ab0Var.m209763a4(c0797kv2.f57729a8, c0797kv4.f57729a8, 0, 8);
            ab0Var.m209763a4(c0797kv2.f57729a8, this.f58108e7.f58097d6.f57729a8, 0, 4);
            ab0Var.m209763a4(c0797kv2.f57729a8, this.f58108e7.f58099d8.f57729a8, 0, 0);
        }
    }

    @Override // p000.C0829lq
    /* renamed from: a2 */
    public final boolean mo212532a2() {
        return true;
    }

    @Override // p000.b40, p000.C0829lq
    /* renamed from: a6 */
    public final void mo210535a6(C0829lq c0829lq, HashMap map) {
        super.mo210535a6(c0829lq, map);
        C0392cv c0392cv = (C0392cv) c0829lq;
        this.f55531h4 = c0392cv.f55531h4;
        this.f55532h5 = c0392cv.f55532h5;
        this.f55533h6 = c0392cv.f55533h6;
    }

    @Override // p000.C0829lq
    /* renamed from: c7 */
    public final boolean mo212533c7() {
        return this.f55534h7;
    }

    @Override // p000.C0829lq
    /* renamed from: c8 */
    public final boolean mo212534c8() {
        return this.f55534h7;
    }

    /* renamed from: e7 */
    public final boolean m212535e7() {
        int i;
        int i2;
        int i3;
        boolean z = true;
        int i4 = 0;
        while (true) {
            i = this.f45712h3;
            if (i4 >= i) {
                break;
            }
            C0829lq c0829lq = this.f45711h2[i4];
            if ((this.f55532h5 || c0829lq.mo212532a2()) && ((((i2 = this.f55531h4) == 0 || i2 == 1) && !c0829lq.mo212533c7()) || (((i3 = this.f55531h4) == 2 || i3 == 3) && !c0829lq.mo212534c8()))) {
                z = false;
            }
            i4++;
        }
        if (!z || i <= 0) {
            return false;
        }
        int iMax = 0;
        boolean z2 = false;
        for (int i5 = 0; i5 < this.f45712h3; i5++) {
            C0829lq c0829lq2 = this.f45711h2[i5];
            if (this.f55532h5 || c0829lq2.mo212532a2()) {
                ConstraintAnchor$Type constraintAnchor$Type = ConstraintAnchor$Type.f44418a3;
                ConstraintAnchor$Type constraintAnchor$Type2 = ConstraintAnchor$Type.f44416a1;
                ConstraintAnchor$Type constraintAnchor$Type3 = ConstraintAnchor$Type.f44417a2;
                ConstraintAnchor$Type constraintAnchor$Type4 = ConstraintAnchor$Type.f44415a0;
                if (!z2) {
                    int i6 = this.f55531h4;
                    if (i6 == 0) {
                        iMax = c0829lq2.mo213885a9(constraintAnchor$Type4).m213749a3();
                    } else if (i6 == 1) {
                        iMax = c0829lq2.mo213885a9(constraintAnchor$Type3).m213749a3();
                    } else if (i6 == 2) {
                        iMax = c0829lq2.mo213885a9(constraintAnchor$Type2).m213749a3();
                    } else if (i6 == 3) {
                        iMax = c0829lq2.mo213885a9(constraintAnchor$Type).m213749a3();
                    }
                    z2 = true;
                }
                int i7 = this.f55531h4;
                if (i7 == 0) {
                    iMax = Math.min(iMax, c0829lq2.mo213885a9(constraintAnchor$Type4).m213749a3());
                } else if (i7 == 1) {
                    iMax = Math.max(iMax, c0829lq2.mo213885a9(constraintAnchor$Type3).m213749a3());
                } else if (i7 == 2) {
                    iMax = Math.min(iMax, c0829lq2.mo213885a9(constraintAnchor$Type2).m213749a3());
                } else if (i7 == 3) {
                    iMax = Math.max(iMax, c0829lq2.mo213885a9(constraintAnchor$Type).m213749a3());
                }
            }
        }
        int i8 = iMax + this.f55533h6;
        int i9 = this.f55531h4;
        if (i9 == 0 || i9 == 1) {
            m213906d6(i8, i8);
        } else {
            m213907d7(i8, i8);
        }
        this.f55534h7 = true;
        return true;
    }

    /* renamed from: e8 */
    public final int m212536e8() {
        int i = this.f55531h4;
        if (i == 0 || i == 1) {
            return 0;
        }
        return (i == 2 || i == 3) ? 1 : -1;
    }

    @Override // p000.C0829lq
    public final String toString() {
        String strM35b6 = AbstractC0003a2.m35b6(new StringBuilder("[Barrier] "), this.f58123g2, " {");
        for (int i = 0; i < this.f45712h3; i++) {
            C0829lq c0829lq = this.f45711h2[i];
            if (i > 0) {
                strM35b6 = AbstractC0003a2.m32b3(strM35b6, ", ");
            }
            StringBuilder sbM37b8 = AbstractC0003a2.m37b8(strM35b6);
            sbM37b8.append(c0829lq.f58123g2);
            strM35b6 = sbM37b8.toString();
        }
        return AbstractC0003a2.m32b3(strM35b6, "}");
    }
}
