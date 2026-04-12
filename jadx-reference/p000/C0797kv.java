package p000;

import androidx.constraintlayout.core.SolverVariable$Type;
import androidx.constraintlayout.core.widgets.ConstraintAnchor$Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kv */
/* loaded from: classes.dex */
public final class C0797kv {

    /* renamed from: a1 */
    public int f57722a1;

    /* renamed from: a2 */
    public boolean f57723a2;

    /* renamed from: a3 */
    public final C0829lq f57724a3;

    /* renamed from: a4 */
    public final ConstraintAnchor$Type f57725a4;

    /* renamed from: a5 */
    public C0797kv f57726a5;

    /* renamed from: a8 */
    public e11 f57729a8;

    /* renamed from: a0 */
    public HashSet f57721a0 = null;

    /* renamed from: a6 */
    public int f57727a6 = 0;

    /* renamed from: a7 */
    public int f57728a7 = Integer.MIN_VALUE;

    public C0797kv(C0829lq c0829lq, ConstraintAnchor$Type constraintAnchor$Type) {
        this.f57724a3 = c0829lq;
        this.f57725a4 = constraintAnchor$Type;
    }

    /* renamed from: a0 */
    public final void m213746a0(C0797kv c0797kv, int i) {
        m213747a1(c0797kv, i, Integer.MIN_VALUE, false);
    }

    /* renamed from: a1 */
    public final boolean m213747a1(C0797kv c0797kv, int i, int i2, boolean z) {
        if (c0797kv == null) {
            m213755a9();
            return true;
        }
        if (!z && !m213754a8(c0797kv)) {
            return false;
        }
        this.f57726a5 = c0797kv;
        if (c0797kv.f57721a0 == null) {
            c0797kv.f57721a0 = new HashSet();
        }
        HashSet hashSet = this.f57726a5.f57721a0;
        if (hashSet != null) {
            hashSet.add(this);
        }
        this.f57727a6 = i;
        this.f57728a7 = i2;
        return true;
    }

    /* renamed from: a2 */
    public final void m213748a2(int i, qe1 qe1Var, ArrayList arrayList) {
        HashSet hashSet = this.f57721a0;
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                cq0.m212482b2(((C0797kv) it.next()).f57724a3, i, arrayList, qe1Var);
            }
        }
    }

    /* renamed from: a3 */
    public final int m213749a3() {
        if (this.f57723a2) {
            return this.f57722a1;
        }
        return 0;
    }

    /* renamed from: a4 */
    public final int m213750a4() {
        C0797kv c0797kv;
        if (this.f57724a3.f58121g0 == 8) {
            return 0;
        }
        int i = this.f57728a7;
        return (i == Integer.MIN_VALUE || (c0797kv = this.f57726a5) == null || c0797kv.f57724a3.f58121g0 != 8) ? this.f57727a6 : i;
    }

    /* renamed from: a5 */
    public final C0797kv m213751a5() {
        ConstraintAnchor$Type constraintAnchor$Type = this.f57725a4;
        int iOrdinal = constraintAnchor$Type.ordinal();
        C0829lq c0829lq = this.f57724a3;
        switch (iOrdinal) {
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
                return null;
            case 1:
                return c0829lq.f58098d7;
            case 2:
                return c0829lq.f58099d8;
            case 3:
                return c0829lq.f58096d5;
            case 4:
                return c0829lq.f58097d6;
            default:
                throw new AssertionError(constraintAnchor$Type.name());
        }
    }

    /* renamed from: a6 */
    public final boolean m213752a6() {
        HashSet hashSet = this.f57721a0;
        if (hashSet == null) {
            return false;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((C0797kv) it.next()).m213751a5().m213753a7()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a7 */
    public final boolean m213753a7() {
        return this.f57726a5 != null;
    }

    /* renamed from: a8 */
    public final boolean m213754a8(C0797kv c0797kv) {
        if (c0797kv == null) {
            return false;
        }
        C0829lq c0829lq = c0797kv.f57724a3;
        ConstraintAnchor$Type constraintAnchor$Type = c0797kv.f57725a4;
        ConstraintAnchor$Type constraintAnchor$Type2 = ConstraintAnchor$Type.f44419a4;
        ConstraintAnchor$Type constraintAnchor$Type3 = this.f57725a4;
        if (constraintAnchor$Type == constraintAnchor$Type3) {
            return constraintAnchor$Type3 != constraintAnchor$Type2 || (c0829lq.f58091d0 && this.f57724a3.f58091d0);
        }
        int iOrdinal = constraintAnchor$Type3.ordinal();
        ConstraintAnchor$Type constraintAnchor$Type4 = ConstraintAnchor$Type.f44421a6;
        ConstraintAnchor$Type constraintAnchor$Type5 = ConstraintAnchor$Type.f44422a7;
        ConstraintAnchor$Type constraintAnchor$Type6 = ConstraintAnchor$Type.f44417a2;
        ConstraintAnchor$Type constraintAnchor$Type7 = ConstraintAnchor$Type.f44415a0;
        switch (iOrdinal) {
            case 0:
            case 7:
            case 8:
                return false;
            case 1:
            case 3:
                boolean z = constraintAnchor$Type == constraintAnchor$Type7 || constraintAnchor$Type == constraintAnchor$Type6;
                return c0829lq instanceof o30 ? z || constraintAnchor$Type == constraintAnchor$Type4 : z;
            case 2:
            case 4:
                boolean z2 = constraintAnchor$Type == ConstraintAnchor$Type.f44416a1 || constraintAnchor$Type == ConstraintAnchor$Type.f44418a3;
                return c0829lq instanceof o30 ? z2 || constraintAnchor$Type == constraintAnchor$Type5 : z2;
            case 5:
                return (constraintAnchor$Type == constraintAnchor$Type7 || constraintAnchor$Type == constraintAnchor$Type6) ? false : true;
            case 6:
                return (constraintAnchor$Type == constraintAnchor$Type2 || constraintAnchor$Type == constraintAnchor$Type4 || constraintAnchor$Type == constraintAnchor$Type5) ? false : true;
            default:
                throw new AssertionError(constraintAnchor$Type3.name());
        }
    }

    /* renamed from: a9 */
    public final void m213755a9() {
        HashSet hashSet;
        C0797kv c0797kv = this.f57726a5;
        if (c0797kv != null && (hashSet = c0797kv.f57721a0) != null) {
            hashSet.remove(this);
            if (this.f57726a5.f57721a0.size() == 0) {
                this.f57726a5.f57721a0 = null;
            }
        }
        this.f57721a0 = null;
        this.f57726a5 = null;
        this.f57727a6 = 0;
        this.f57728a7 = Integer.MIN_VALUE;
        this.f57723a2 = false;
        this.f57722a1 = 0;
    }

    /* renamed from: b0 */
    public final void m213756b0() {
        e11 e11Var = this.f57729a8;
        if (e11Var == null) {
            this.f57729a8 = new e11(SolverVariable$Type.f44410a0);
        } else {
            e11Var.m212651a2();
        }
    }

    /* renamed from: b1 */
    public final void m213757b1(int i) {
        this.f57722a1 = i;
        this.f57723a2 = true;
    }

    public final String toString() {
        return this.f57724a3.f58123g2 + ":" + this.f57725a4.toString();
    }
}
