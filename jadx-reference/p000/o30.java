package p000;

import androidx.constraintlayout.core.widgets.ConstraintAnchor$Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class o30 extends C0829lq {

    /* renamed from: h2 */
    public float f58726h2 = -1.0f;

    /* renamed from: h3 */
    public int f58727h3 = -1;

    /* renamed from: h4 */
    public int f58728h4 = -1;

    /* renamed from: h5 */
    public C0797kv f58729h5 = this.f58097d6;

    /* renamed from: h6 */
    public int f58730h6 = 0;

    /* renamed from: h7 */
    public boolean f58731h7;

    public o30() {
        this.f58105e4.clear();
        this.f58105e4.add(this.f58729h5);
        int length = this.f58104e3.length;
        for (int i = 0; i < length; i++) {
            this.f58104e3[i] = this.f58729h5;
        }
    }

    @Override // p000.C0829lq
    /* renamed from: a1 */
    public final void mo210751a1(ab0 ab0Var, boolean z) {
        C0830lr c0830lr = (C0830lr) this.f58108e7;
        if (c0830lr == null) {
            return;
        }
        Object objMo213885a9 = c0830lr.mo213885a9(ConstraintAnchor$Type.f44415a0);
        Object objMo213885a92 = c0830lr.mo213885a9(ConstraintAnchor$Type.f44417a2);
        C0829lq c0829lq = this.f58108e7;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44425a1;
        boolean z2 = c0829lq != null && c0829lq.f58107e6[0] == constraintWidget$DimensionBehaviour;
        if (this.f58730h6 == 0) {
            objMo213885a9 = c0830lr.mo213885a9(ConstraintAnchor$Type.f44416a1);
            objMo213885a92 = c0830lr.mo213885a9(ConstraintAnchor$Type.f44418a3);
            C0829lq c0829lq2 = this.f58108e7;
            z2 = c0829lq2 != null && c0829lq2.f58107e6[1] == constraintWidget$DimensionBehaviour;
        }
        if (this.f58731h7) {
            C0797kv c0797kv = this.f58729h5;
            if (c0797kv.f57723a2) {
                e11 e11VarM209769b0 = ab0Var.m209769b0(c0797kv);
                ab0Var.m209762a3(e11VarM209769b0, this.f58729h5.m213749a3());
                if (this.f58727h3 != -1) {
                    if (z2) {
                        ab0Var.m209764a5(ab0Var.m209769b0(objMo213885a92), e11VarM209769b0, 0, 5);
                    }
                } else if (this.f58728h4 != -1 && z2) {
                    e11 e11VarM209769b02 = ab0Var.m209769b0(objMo213885a92);
                    ab0Var.m209764a5(e11VarM209769b0, ab0Var.m209769b0(objMo213885a9), 0, 5);
                    ab0Var.m209764a5(e11VarM209769b02, e11VarM209769b0, 0, 5);
                }
                this.f58731h7 = false;
                return;
            }
        }
        if (this.f58727h3 != -1) {
            e11 e11VarM209769b03 = ab0Var.m209769b0(this.f58729h5);
            ab0Var.m209763a4(e11VarM209769b03, ab0Var.m209769b0(objMo213885a9), this.f58727h3, 8);
            if (z2) {
                ab0Var.m209764a5(ab0Var.m209769b0(objMo213885a92), e11VarM209769b03, 0, 5);
                return;
            }
            return;
        }
        if (this.f58728h4 != -1) {
            e11 e11VarM209769b04 = ab0Var.m209769b0(this.f58729h5);
            e11 e11VarM209769b05 = ab0Var.m209769b0(objMo213885a92);
            ab0Var.m209763a4(e11VarM209769b04, e11VarM209769b05, -this.f58728h4, 8);
            if (z2) {
                ab0Var.m209764a5(e11VarM209769b04, ab0Var.m209769b0(objMo213885a9), 0, 5);
                ab0Var.m209764a5(e11VarM209769b05, e11VarM209769b04, 0, 5);
                return;
            }
            return;
        }
        if (this.f58726h2 != -1.0f) {
            e11 e11VarM209769b06 = ab0Var.m209769b0(this.f58729h5);
            e11 e11VarM209769b07 = ab0Var.m209769b0(objMo213885a92);
            float f = this.f58726h2;
            C0131be c0131beM209770b1 = ab0Var.m209770b1();
            c0131beM209770b1.f45835a3.m210629a6(e11VarM209769b06, -1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11VarM209769b07, f);
            ab0Var.m209761a2(c0131beM209770b1);
        }
    }

    @Override // p000.C0829lq
    /* renamed from: a2 */
    public final boolean mo212532a2() {
        return true;
    }

    @Override // p000.C0829lq
    /* renamed from: a6 */
    public final void mo210535a6(C0829lq c0829lq, HashMap map) {
        super.mo210535a6(c0829lq, map);
        o30 o30Var = (o30) c0829lq;
        this.f58726h2 = o30Var.f58726h2;
        this.f58727h3 = o30Var.f58727h3;
        this.f58728h4 = o30Var.f58728h4;
        m214153e5(o30Var.f58730h6);
    }

    @Override // p000.C0829lq
    /* renamed from: a9 */
    public final C0797kv mo213885a9(ConstraintAnchor$Type constraintAnchor$Type) {
        int iOrdinal = constraintAnchor$Type.ordinal();
        if (iOrdinal != 1) {
            if (iOrdinal != 2) {
                if (iOrdinal != 3) {
                    if (iOrdinal != 4) {
                        return null;
                    }
                }
            }
            if (this.f58730h6 == 0) {
                return this.f58729h5;
            }
            return null;
        }
        if (this.f58730h6 == 1) {
            return this.f58729h5;
        }
        return null;
    }

    @Override // p000.C0829lq
    /* renamed from: c7 */
    public final boolean mo212533c7() {
        return this.f58731h7;
    }

    @Override // p000.C0829lq
    /* renamed from: c8 */
    public final boolean mo212534c8() {
        return this.f58731h7;
    }

    @Override // p000.C0829lq
    /* renamed from: e3 */
    public final void mo213913e3(ab0 ab0Var, boolean z) {
        if (this.f58108e7 == null) {
            return;
        }
        C0797kv c0797kv = this.f58729h5;
        ab0Var.getClass();
        int iM209758b3 = ab0.m209758b3(c0797kv);
        if (this.f58730h6 == 1) {
            this.f58113f2 = iM209758b3;
            this.f58114f3 = 0;
            m213908d8(this.f58108e7.m213887b1());
            m213911e1(0);
            return;
        }
        this.f58113f2 = 0;
        this.f58114f3 = iM209758b3;
        m213911e1(this.f58108e7.m213891b7());
        m213908d8(0);
    }

    /* renamed from: e4 */
    public final void m214152e4(int i) {
        this.f58729h5.m213757b1(i);
        this.f58731h7 = true;
    }

    /* renamed from: e5 */
    public final void m214153e5(int i) {
        if (this.f58730h6 == i) {
            return;
        }
        this.f58730h6 = i;
        ArrayList arrayList = this.f58105e4;
        arrayList.clear();
        if (this.f58730h6 == 1) {
            this.f58729h5 = this.f58096d5;
        } else {
            this.f58729h5 = this.f58097d6;
        }
        arrayList.add(this.f58729h5);
        C0797kv[] c0797kvArr = this.f58104e3;
        int length = c0797kvArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            c0797kvArr[i2] = this.f58729h5;
        }
    }
}
