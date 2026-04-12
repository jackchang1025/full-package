package p000;

import androidx.constraintlayout.core.widgets.ConstraintAnchor$Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lr */
/* loaded from: classes.dex */
public final class C0830lr extends C0829lq {

    /* renamed from: h2 */
    public ArrayList f58139h2 = new ArrayList();

    /* renamed from: h3 */
    public final pg1 f58140h3 = new pg1(this);

    /* renamed from: h4 */
    public final C1218sd f58141h4;

    /* renamed from: h5 */
    public int f58142h5;

    /* renamed from: h6 */
    public C0813la f58143h6;

    /* renamed from: h7 */
    public boolean f58144h7;

    /* renamed from: h8 */
    public final ab0 f58145h8;

    /* renamed from: h9 */
    public int f58146h9;

    /* renamed from: i0 */
    public int f58147i0;

    /* renamed from: i1 */
    public int f58148i1;

    /* renamed from: i2 */
    public int f58149i2;

    /* renamed from: i3 */
    public C0554gr[] f58150i3;

    /* renamed from: i4 */
    public C0554gr[] f58151i4;

    /* renamed from: i5 */
    public int f58152i5;

    /* renamed from: i6 */
    public boolean f58153i6;

    /* renamed from: i7 */
    public boolean f58154i7;

    /* renamed from: i8 */
    public WeakReference f58155i8;

    /* renamed from: i9 */
    public WeakReference f58156i9;

    /* renamed from: j0 */
    public WeakReference f58157j0;

    /* renamed from: j1 */
    public WeakReference f58158j1;

    /* renamed from: j2 */
    public final HashSet f58159j2;

    /* renamed from: j3 */
    public final C0418dj f58160j3;

    public C0830lr() {
        C1218sd c1218sd = new C1218sd();
        c1218sd.f59956a1 = true;
        c1218sd.f59957a2 = true;
        c1218sd.f59959a4 = new ArrayList();
        new ArrayList();
        c1218sd.f59960a5 = null;
        c1218sd.f59961a6 = new C0418dj();
        c1218sd.f59962a7 = new ArrayList();
        c1218sd.f59955a0 = this;
        c1218sd.f59958a3 = this;
        this.f58141h4 = c1218sd;
        this.f58143h6 = null;
        this.f58144h7 = false;
        this.f58145h8 = new ab0();
        this.f58148i1 = 0;
        this.f58149i2 = 0;
        this.f58150i3 = new C0554gr[4];
        this.f58151i4 = new C0554gr[4];
        this.f58152i5 = 257;
        this.f58153i6 = false;
        this.f58154i7 = false;
        this.f58155i8 = null;
        this.f58156i9 = null;
        this.f58157j0 = null;
        this.f58158j1 = null;
        this.f58159j2 = new HashSet();
        this.f58160j3 = new C0418dj();
    }

    /* renamed from: e8 */
    public static void m213920e8(C0829lq c0829lq, C0813la c0813la, C0418dj c0418dj) {
        int i;
        int i2;
        if (c0813la == null) {
            return;
        }
        int i3 = c0829lq.f58121g0;
        int[] iArr = c0829lq.f58080b9;
        if (i3 == 8 || (c0829lq instanceof o30) || (c0829lq instanceof C0392cv)) {
            c0418dj.f55823a4 = 0;
            c0418dj.f55824a5 = 0;
            return;
        }
        ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
        c0418dj.f55819a0 = constraintWidget$DimensionBehaviourArr[0];
        c0418dj.f55820a1 = constraintWidget$DimensionBehaviourArr[1];
        c0418dj.f55821a2 = c0829lq.m213891b7();
        c0418dj.f55822a3 = c0829lq.m213887b1();
        c0418dj.f55827a8 = false;
        c0418dj.f55828a9 = 0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = c0418dj.f55819a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44426a2;
        boolean z = constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour2;
        boolean z2 = c0418dj.f55820a1 == constraintWidget$DimensionBehaviour2;
        boolean z3 = z && c0829lq.f58111f0 > 0.0f;
        boolean z4 = z2 && c0829lq.f58111f0 > 0.0f;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44425a1;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4 = ConstraintWidget$DimensionBehaviour.f44424a0;
        if (z && c0829lq.m213894c0(0) && c0829lq.f58078b7 == 0 && !z3) {
            c0418dj.f55819a0 = constraintWidget$DimensionBehaviour3;
            if (z2 && c0829lq.f58079b8 == 0) {
                c0418dj.f55819a0 = constraintWidget$DimensionBehaviour4;
            }
            z = false;
        }
        if (z2 && c0829lq.m213894c0(1) && c0829lq.f58079b8 == 0 && !z4) {
            c0418dj.f55820a1 = constraintWidget$DimensionBehaviour3;
            if (z && c0829lq.f58078b7 == 0) {
                c0418dj.f55820a1 = constraintWidget$DimensionBehaviour4;
            }
            z2 = false;
        }
        if (c0829lq.mo212533c7()) {
            c0418dj.f55819a0 = constraintWidget$DimensionBehaviour4;
            z = false;
        }
        if (c0829lq.mo212534c8()) {
            c0418dj.f55820a1 = constraintWidget$DimensionBehaviour4;
            z2 = false;
        }
        if (z3) {
            if (iArr[0] == 4) {
                c0418dj.f55819a0 = constraintWidget$DimensionBehaviour4;
            } else if (!z2) {
                if (c0418dj.f55820a1 == constraintWidget$DimensionBehaviour4) {
                    i2 = c0418dj.f55822a3;
                } else {
                    c0418dj.f55819a0 = constraintWidget$DimensionBehaviour3;
                    c0813la.m213800a1(c0829lq, c0418dj);
                    i2 = c0418dj.f55824a5;
                }
                c0418dj.f55819a0 = constraintWidget$DimensionBehaviour4;
                c0418dj.f55821a2 = (int) (c0829lq.f58111f0 * i2);
            }
        }
        if (z4) {
            if (iArr[1] == 4) {
                c0418dj.f55820a1 = constraintWidget$DimensionBehaviour4;
            } else if (!z) {
                if (c0418dj.f55819a0 == constraintWidget$DimensionBehaviour4) {
                    i = c0418dj.f55821a2;
                } else {
                    c0418dj.f55820a1 = constraintWidget$DimensionBehaviour3;
                    c0813la.m213800a1(c0829lq, c0418dj);
                    i = c0418dj.f55823a4;
                }
                c0418dj.f55820a1 = constraintWidget$DimensionBehaviour4;
                if (c0829lq.f58112f1 == -1) {
                    c0418dj.f55822a3 = (int) (i / c0829lq.f58111f0);
                } else {
                    c0418dj.f55822a3 = (int) (c0829lq.f58111f0 * i);
                }
            }
        }
        c0813la.m213800a1(c0829lq, c0418dj);
        c0829lq.m213911e1(c0418dj.f55823a4);
        c0829lq.m213908d8(c0418dj.f55824a5);
        c0829lq.f58091d0 = c0418dj.f55826a7;
        c0829lq.m213905d5(c0418dj.f55825a6);
        c0418dj.f55828a9 = 0;
    }

    @Override // p000.C0829lq
    /* renamed from: b4 */
    public final void mo213890b4(StringBuilder sb) {
        sb.append(this.f58070a9 + ":{\n");
        StringBuilder sb2 = new StringBuilder("  actualWidth:");
        sb2.append(this.f58109e8);
        sb.append(sb2.toString());
        sb.append("\n");
        sb.append("  actualHeight:" + this.f58110e9);
        sb.append("\n");
        ArrayList arrayList = this.f58139h2;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((C0829lq) obj).mo213890b4(sb);
            sb.append(",\n");
        }
        sb.append("}");
    }

    @Override // p000.C0829lq
    /* renamed from: c9 */
    public final void mo213901c9() {
        this.f58145h8.m209777b9();
        this.f58146h9 = 0;
        this.f58147i0 = 0;
        this.f58139h2.clear();
        super.mo213901c9();
    }

    @Override // p000.C0829lq
    /* renamed from: d2 */
    public final void mo213904d2(zg1 zg1Var) {
        super.mo213904d2(zg1Var);
        int size = this.f58139h2.size();
        for (int i = 0; i < size; i++) {
            ((C0829lq) this.f58139h2.get(i)).mo213904d2(zg1Var);
        }
    }

    @Override // p000.C0829lq
    /* renamed from: e2 */
    public final void mo213912e2(boolean z, boolean z2) {
        super.mo213912e2(z, z2);
        int size = this.f58139h2.size();
        for (int i = 0; i < size; i++) {
            ((C0829lq) this.f58139h2.get(i)).mo213912e2(z, z2);
        }
    }

    /* renamed from: e4 */
    public final void m213921e4(C0829lq c0829lq, int i) {
        if (i == 0) {
            int i2 = this.f58148i1 + 1;
            C0554gr[] c0554grArr = this.f58151i4;
            if (i2 >= c0554grArr.length) {
                this.f58151i4 = (C0554gr[]) Arrays.copyOf(c0554grArr, c0554grArr.length * 2);
            }
            C0554gr[] c0554grArr2 = this.f58151i4;
            int i3 = this.f58148i1;
            c0554grArr2[i3] = new C0554gr(c0829lq, 0, this.f58144h7);
            this.f58148i1 = i3 + 1;
            return;
        }
        if (i == 1) {
            int i4 = this.f58149i2 + 1;
            C0554gr[] c0554grArr3 = this.f58150i3;
            if (i4 >= c0554grArr3.length) {
                this.f58150i3 = (C0554gr[]) Arrays.copyOf(c0554grArr3, c0554grArr3.length * 2);
            }
            C0554gr[] c0554grArr4 = this.f58150i3;
            int i5 = this.f58149i2;
            c0554grArr4[i5] = new C0554gr(c0829lq, 1, this.f58144h7);
            this.f58149i2 = i5 + 1;
        }
    }

    /* renamed from: e5 */
    public final void m213922e5(ab0 ab0Var) {
        C0830lr c0830lr;
        ab0 ab0Var2;
        boolean zM213925e9 = m213925e9(64);
        mo210751a1(ab0Var, zM213925e9);
        int size = this.f58139h2.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            C0829lq c0829lq = (C0829lq) this.f58139h2.get(i);
            boolean[] zArr = c0829lq.f58106e5;
            zArr[0] = false;
            zArr[1] = false;
            if (c0829lq instanceof C0392cv) {
                z = true;
            }
        }
        if (z) {
            for (int i2 = 0; i2 < size; i2++) {
                C0829lq c0829lq2 = (C0829lq) this.f58139h2.get(i2);
                if (c0829lq2 instanceof C0392cv) {
                    C0392cv c0392cv = (C0392cv) c0829lq2;
                    for (int i3 = 0; i3 < c0392cv.f45712h3; i3++) {
                        C0829lq c0829lq3 = c0392cv.f45711h2[i3];
                        if (c0392cv.f55532h5 || c0829lq3.mo212532a2()) {
                            int i4 = c0392cv.f55531h4;
                            if (i4 == 0 || i4 == 1) {
                                c0829lq3.f58106e5[0] = true;
                            } else if (i4 == 2 || i4 == 3) {
                                c0829lq3.f58106e5[1] = true;
                            }
                        }
                    }
                }
            }
        }
        HashSet hashSet = this.f58159j2;
        hashSet.clear();
        for (int i5 = 0; i5 < size; i5++) {
            C0829lq c0829lq4 = (C0829lq) this.f58139h2.get(i5);
            c0829lq4.getClass();
            boolean z2 = c0829lq4 instanceof md1;
            if (z2 || (c0829lq4 instanceof o30)) {
                if (z2) {
                    hashSet.add(c0829lq4);
                } else {
                    c0829lq4.mo210751a1(ab0Var, zM213925e9);
                }
            }
        }
        while (hashSet.size() > 0) {
            int size2 = hashSet.size();
            Iterator it = hashSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                md1 md1Var = (md1) ((C0829lq) it.next());
                for (int i6 = 0; i6 < md1Var.f45712h3; i6++) {
                    if (hashSet.contains(md1Var.f45711h2[i6])) {
                        md1Var.mo210751a1(ab0Var, zM213925e9);
                        hashSet.remove(md1Var);
                        break;
                    }
                }
            }
            if (size2 == hashSet.size()) {
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    ((C0829lq) it2.next()).mo210751a1(ab0Var, zM213925e9);
                }
                hashSet.clear();
            }
        }
        boolean z3 = ab0.f43590b5;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44425a1;
        if (z3) {
            HashSet hashSet2 = new HashSet();
            for (int i7 = 0; i7 < size; i7++) {
                C0829lq c0829lq5 = (C0829lq) this.f58139h2.get(i7);
                c0829lq5.getClass();
                if (!(c0829lq5 instanceof md1) && !(c0829lq5 instanceof o30)) {
                    hashSet2.add(c0829lq5);
                }
            }
            c0830lr = this;
            ab0Var2 = ab0Var;
            c0830lr.m213879a0(this, ab0Var2, hashSet2, this.f58107e6[0] == constraintWidget$DimensionBehaviour ? 0 : 1, false);
            Iterator it3 = hashSet2.iterator();
            while (it3.hasNext()) {
                C0829lq c0829lq6 = (C0829lq) it3.next();
                kj1.m213558a5(this, ab0Var2, c0829lq6);
                c0829lq6.mo210751a1(ab0Var2, zM213925e9);
            }
        } else {
            c0830lr = this;
            ab0Var2 = ab0Var;
            for (int i8 = 0; i8 < size; i8++) {
                C0829lq c0829lq7 = (C0829lq) c0830lr.f58139h2.get(i8);
                if (c0829lq7 instanceof C0830lr) {
                    ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq7.f58107e6;
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviourArr[0];
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviourArr[1];
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4 = ConstraintWidget$DimensionBehaviour.f44424a0;
                    if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                        c0829lq7.m213909d9(constraintWidget$DimensionBehaviour4);
                    }
                    if (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour) {
                        c0829lq7.m213910e0(constraintWidget$DimensionBehaviour4);
                    }
                    c0829lq7.mo210751a1(ab0Var2, zM213925e9);
                    if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                        c0829lq7.m213909d9(constraintWidget$DimensionBehaviour2);
                    }
                    if (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour) {
                        c0829lq7.m213910e0(constraintWidget$DimensionBehaviour3);
                    }
                } else {
                    kj1.m213558a5(this, ab0Var2, c0829lq7);
                    if (!(c0829lq7 instanceof md1) && !(c0829lq7 instanceof o30)) {
                        c0829lq7.mo210751a1(ab0Var2, zM213925e9);
                    }
                }
            }
        }
        if (c0830lr.f58148i1 > 0) {
            b81.m210560a0(this, ab0Var2, null, 0);
        }
        if (c0830lr.f58149i2 > 0) {
            b81.m210560a0(this, ab0Var2, null, 1);
        }
    }

    /* renamed from: e6 */
    public final boolean m213923e6(int i, boolean z) {
        boolean z2;
        boolean z3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour;
        boolean z4;
        C1218sd c1218sd = this.f58141h4;
        ArrayList arrayList = c1218sd.f59959a4;
        C0830lr c0830lr = c1218sd.f59955a0;
        boolean z5 = false;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviourM213886b0 = c0830lr.m213886b0(0);
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviourM213886b02 = c0830lr.m213886b0(1);
        int iM213892b8 = c0830lr.m213892b8();
        int iM213893b9 = c0830lr.m213893b9();
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44424a0;
        if (z && (constraintWidget$DimensionBehaviourM213886b0 == (constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44425a1) || constraintWidget$DimensionBehaviourM213886b02 == constraintWidget$DimensionBehaviour)) {
            int size = arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    z4 = z;
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                AbstractC0055a5 abstractC0055a5 = (AbstractC0055a5) obj;
                if (abstractC0055a5.f44462a5 == i && !abstractC0055a5.mo209955b0()) {
                    z4 = false;
                    break;
                }
            }
            if (i == 0) {
                if (z4 && constraintWidget$DimensionBehaviourM213886b0 == constraintWidget$DimensionBehaviour) {
                    c0830lr.m213909d9(constraintWidget$DimensionBehaviour2);
                    c0830lr.m213911e1(c1218sd.m214603a3(c0830lr, 0));
                    c0830lr.f58064a3.f44461a4.mo209951a3(c0830lr.m213891b7());
                }
            } else if (z4 && constraintWidget$DimensionBehaviourM213886b02 == constraintWidget$DimensionBehaviour) {
                c0830lr.m213910e0(constraintWidget$DimensionBehaviour2);
                c0830lr.m213908d8(c1218sd.m214603a3(c0830lr, 1));
                c0830lr.f58065a4.f44461a4.mo209951a3(c0830lr.m213887b1());
            }
        }
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44427a3;
        if (i == 0) {
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4 = c0830lr.f58107e6[0];
            if (constraintWidget$DimensionBehaviour4 == constraintWidget$DimensionBehaviour2 || constraintWidget$DimensionBehaviour4 == constraintWidget$DimensionBehaviour3) {
                int iM213891b7 = c0830lr.m213891b7() + iM213892b8;
                c0830lr.f58064a3.f44465a8.mo209951a3(iM213891b7);
                c0830lr.f58064a3.f44461a4.mo209951a3(iM213891b7 - iM213892b8);
                z3 = true;
                z2 = true;
            } else {
                z2 = true;
                z3 = false;
            }
        } else {
            z2 = true;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5 = c0830lr.f58107e6[1];
            if (constraintWidget$DimensionBehaviour5 == constraintWidget$DimensionBehaviour2 || constraintWidget$DimensionBehaviour5 == constraintWidget$DimensionBehaviour3) {
                int iM213887b1 = c0830lr.m213887b1() + iM213893b9;
                c0830lr.f58065a4.f44465a8.mo209951a3(iM213887b1);
                c0830lr.f58065a4.f44461a4.mo209951a3(iM213887b1 - iM213893b9);
                z3 = true;
            } else {
                z3 = false;
            }
        }
        c1218sd.m214606a6();
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList.get(i3);
            i3++;
            AbstractC0055a5 abstractC0055a52 = (AbstractC0055a5) obj2;
            if (abstractC0055a52.f44462a5 == i && (abstractC0055a52.f44458a1 != c0830lr || abstractC0055a52.f44463a6)) {
                abstractC0055a52.mo209953a4();
            }
        }
        int size3 = arrayList.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size3) {
                z5 = z2;
                break;
            }
            Object obj3 = arrayList.get(i4);
            i4++;
            AbstractC0055a5 abstractC0055a53 = (AbstractC0055a5) obj3;
            if (abstractC0055a53.f44462a5 == i && (z3 || abstractC0055a53.f44458a1 != c0830lr)) {
                if (!abstractC0055a53.f44464a7.f44450a9 || !abstractC0055a53.f44465a8.f44450a9 || (!(abstractC0055a53 instanceof C0555gs) && !abstractC0055a53.f44461a4.f44450a9)) {
                    break;
                }
            }
        }
        c0830lr.m213909d9(constraintWidget$DimensionBehaviourM213886b0);
        c0830lr.m213910e0(constraintWidget$DimensionBehaviourM213886b02);
        return z5;
    }

    /* JADX WARN: Removed duplicated region for block: B:338:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x063a  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x066b  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x06a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:383:0x06ad  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x06b6  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x06bd  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x06e4  */
    /* JADX WARN: Removed duplicated region for block: B:462:0x07f3  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0830  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x084a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:492:0x08b2  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x08c4  */
    /* JADX WARN: Removed duplicated region for block: B:498:0x08e0  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x08ec  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x08ef  */
    /* JADX WARN: Removed duplicated region for block: B:513:0x0927 A[PHI: r14 r25
      0x0927: PHI (r14v9 boolean) = (r14v8 boolean), (r14v12 boolean), (r14v12 boolean), (r14v12 boolean) binds: [B:500:0x08ed, B:508:0x090f, B:509:0x0911, B:511:0x0917] A[DONT_GENERATE, DONT_INLINE]
      0x0927: PHI (r25v6 boolean) = (r25v5 boolean), (r25v7 boolean), (r25v7 boolean), (r25v7 boolean) binds: [B:500:0x08ed, B:508:0x090f, B:509:0x0911, B:511:0x0917] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:515:0x092e  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x0930  */
    /* JADX WARN: Removed duplicated region for block: B:520:0x0940  */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6, types: [boolean] */
    /* renamed from: e7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m213924e7() {
        boolean[] zArr;
        int i;
        C0797kv c0797kv;
        int i2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3;
        C0797kv c0797kv2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4;
        int i3;
        ab0 ab0Var;
        int i4;
        boolean z;
        char c;
        int i5;
        int i6;
        boolean z2;
        boolean z3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5;
        boolean z4;
        boolean z5;
        boolean z6;
        int iMax;
        int iMax2;
        ?? r15;
        boolean z7;
        int i7;
        C0797kv c0797kv3;
        int i8;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour6;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour7;
        qe1 qe1Var;
        int i9;
        int iM213891b7;
        int i10;
        int iM213887b1;
        int iM214383a1;
        qe1 qe1Var2;
        qe1 qe1Var3;
        int i11;
        C0797kv c0797kv4;
        boolean[] zArr2 = kj1.f57535a3;
        this.f58113f2 = 0;
        this.f58114f3 = 0;
        this.f58153i6 = false;
        this.f58154i7 = false;
        int size = this.f58139h2.size();
        int iMax3 = Math.max(0, m213891b7());
        int iMax4 = Math.max(0, m213887b1());
        ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = this.f58107e6;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour8 = constraintWidget$DimensionBehaviourArr[1];
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour9 = constraintWidget$DimensionBehaviourArr[0];
        int i12 = this.f58142h5;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour10 = ConstraintWidget$DimensionBehaviour.f44426a2;
        C0797kv c0797kv5 = this.f58097d6;
        C0797kv c0797kv6 = this.f58096d5;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour11 = ConstraintWidget$DimensionBehaviour.f44424a0;
        if (i12 == 0 && kj1.m213565b2(this.f58152i5, 1)) {
            C0813la c0813la = this.f58143h6;
            ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr2 = this.f58107e6;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour12 = constraintWidget$DimensionBehaviourArr2[0];
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour13 = constraintWidget$DimensionBehaviourArr2[1];
            m213903d1();
            ArrayList arrayList = this.f58139h2;
            int size2 = arrayList.size();
            zArr = zArr2;
            for (int i13 = 0; i13 < size2; i13++) {
                ((C0829lq) arrayList.get(i13)).m213903d1();
            }
            boolean z8 = this.f58144h7;
            if (constraintWidget$DimensionBehaviour12 == constraintWidget$DimensionBehaviour11) {
                i = iMax4;
                m213906d6(0, m213891b7());
            } else {
                i = iMax4;
                c0797kv6.m213757b1(0);
                this.f58113f2 = 0;
            }
            boolean z9 = false;
            int i14 = 0;
            boolean z10 = false;
            while (i14 < size2) {
                boolean z11 = z9;
                C0829lq c0829lq = (C0829lq) arrayList.get(i14);
                int i15 = i14;
                if (c0829lq instanceof o30) {
                    o30 o30Var = (o30) c0829lq;
                    c0797kv4 = c0797kv6;
                    if (o30Var.f58730h6 == 1) {
                        int i16 = o30Var.f58727h3;
                        if (i16 != -1) {
                            o30Var.m214152e4(i16);
                        } else if (o30Var.f58728h4 != -1 && mo212533c7()) {
                            o30Var.m214152e4(m213891b7() - o30Var.f58728h4);
                        } else if (mo212533c7()) {
                            o30Var.m214152e4((int) ((o30Var.f58726h2 * m213891b7()) + 0.5f));
                        }
                        z11 = true;
                    }
                } else {
                    c0797kv4 = c0797kv6;
                    if ((c0829lq instanceof C0392cv) && ((C0392cv) c0829lq).m212536e8() == 0) {
                        z9 = z11;
                        z10 = true;
                    }
                    i14 = i15 + 1;
                    c0797kv6 = c0797kv4;
                }
                z9 = z11;
                i14 = i15 + 1;
                c0797kv6 = c0797kv4;
            }
            c0797kv = c0797kv6;
            if (z9) {
                for (int i17 = 0; i17 < size2; i17 = i11 + 1) {
                    C0829lq c0829lq2 = (C0829lq) arrayList.get(i17);
                    if (c0829lq2 instanceof o30) {
                        o30 o30Var2 = (o30) c0829lq2;
                        i11 = i17;
                        if (o30Var2.f58730h6 == 1) {
                            cq0.m212486b8(0, c0813la, o30Var2, z8);
                        }
                    } else {
                        i11 = i17;
                    }
                }
            }
            cq0.m212486b8(0, c0813la, this, z8);
            if (z10) {
                for (int i18 = 0; i18 < size2; i18++) {
                    C0829lq c0829lq3 = (C0829lq) arrayList.get(i18);
                    if (c0829lq3 instanceof C0392cv) {
                        C0392cv c0392cv = (C0392cv) c0829lq3;
                        if (c0392cv.m212536e8() == 0 && c0392cv.m212535e7()) {
                            cq0.m212486b8(1, c0813la, c0392cv, z8);
                        }
                    }
                }
            }
            if (constraintWidget$DimensionBehaviour13 == constraintWidget$DimensionBehaviour11) {
                m213907d7(0, m213887b1());
            } else {
                c0797kv5.m213757b1(0);
                this.f58114f3 = 0;
            }
            int i19 = 0;
            boolean z12 = false;
            boolean z13 = false;
            while (i19 < size2) {
                C0829lq c0829lq4 = (C0829lq) arrayList.get(i19);
                int i20 = i19;
                if (c0829lq4 instanceof o30) {
                    o30 o30Var3 = (o30) c0829lq4;
                    if (o30Var3.f58730h6 == 0) {
                        int i21 = o30Var3.f58727h3;
                        if (i21 != -1) {
                            o30Var3.m214152e4(i21);
                        } else if (o30Var3.f58728h4 != -1 && mo212534c8()) {
                            o30Var3.m214152e4(m213887b1() - o30Var3.f58728h4);
                        } else if (mo212534c8()) {
                            o30Var3.m214152e4((int) ((o30Var3.f58726h2 * m213887b1()) + 0.5f));
                        }
                        z12 = true;
                    }
                } else if ((c0829lq4 instanceof C0392cv) && ((C0392cv) c0829lq4).m212536e8() == 1) {
                    z13 = true;
                }
                i19 = i20 + 1;
            }
            if (z12) {
                for (int i22 = 0; i22 < size2; i22++) {
                    C0829lq c0829lq5 = (C0829lq) arrayList.get(i22);
                    if (c0829lq5 instanceof o30) {
                        o30 o30Var4 = (o30) c0829lq5;
                        if (o30Var4.f58730h6 == 0) {
                            cq0.m212499e3(1, c0813la, o30Var4);
                        }
                    }
                }
            }
            cq0.m212499e3(0, c0813la, this);
            if (z13) {
                for (int i23 = 0; i23 < size2; i23++) {
                    C0829lq c0829lq6 = (C0829lq) arrayList.get(i23);
                    if (c0829lq6 instanceof C0392cv) {
                        C0392cv c0392cv2 = (C0392cv) c0829lq6;
                        if (c0392cv2.m212536e8() == 1 && c0392cv2.m212535e7()) {
                            cq0.m212499e3(1, c0813la, c0392cv2);
                        }
                    }
                }
            }
            for (int i24 = 0; i24 < size2; i24++) {
                C0829lq c0829lq7 = (C0829lq) arrayList.get(i24);
                if (c0829lq7.m213900c6() && cq0.m212473a1(c0829lq7)) {
                    m213920e8(c0829lq7, c0813la, cq0.f55467a1);
                    if (!(c0829lq7 instanceof o30)) {
                        cq0.m212486b8(0, c0813la, c0829lq7, z8);
                        cq0.m212499e3(0, c0813la, c0829lq7);
                    } else if (((o30) c0829lq7).f58730h6 == 0) {
                        cq0.m212499e3(0, c0813la, c0829lq7);
                    } else {
                        cq0.m212486b8(0, c0813la, c0829lq7, z8);
                    }
                }
            }
            for (int i25 = 0; i25 < size; i25++) {
                C0829lq c0829lq8 = (C0829lq) this.f58139h2.get(i25);
                if (c0829lq8.m213900c6() && !(c0829lq8 instanceof o30) && !(c0829lq8 instanceof C0392cv) && !(c0829lq8 instanceof md1) && !c0829lq8.f58093d2) {
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviourM213886b0 = c0829lq8.m213886b0(0);
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviourM213886b02 = c0829lq8.m213886b0(1);
                    if (constraintWidget$DimensionBehaviourM213886b0 != constraintWidget$DimensionBehaviour10 || c0829lq8.f58078b7 == 1 || constraintWidget$DimensionBehaviourM213886b02 != constraintWidget$DimensionBehaviour10 || c0829lq8.f58079b8 == 1) {
                        m213920e8(c0829lq8, this.f58143h6, new C0418dj());
                    }
                }
            }
        } else {
            zArr = zArr2;
            i = iMax4;
            c0797kv = c0797kv6;
        }
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour14 = ConstraintWidget$DimensionBehaviour.f44425a1;
        ab0 ab0Var2 = this.f58145h8;
        if (size <= 2 || !((constraintWidget$DimensionBehaviour9 == constraintWidget$DimensionBehaviour14 || constraintWidget$DimensionBehaviour8 == constraintWidget$DimensionBehaviour14) && kj1.m213565b2(this.f58152i5, Segment.SHARE_MINIMUM))) {
            i2 = size;
            constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviour14;
            constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour9;
            constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviour8;
            c0797kv2 = c0797kv5;
            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour11;
            i3 = iMax3;
            ab0Var = ab0Var2;
            i4 = i;
        } else {
            C0813la c0813la2 = this.f58143h6;
            ArrayList arrayList2 = this.f58139h2;
            int size3 = arrayList2.size();
            int i26 = 0;
            while (i26 < size3) {
                C0829lq c0829lq9 = (C0829lq) arrayList2.get(i26);
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr3 = this.f58107e6;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour15 = constraintWidget$DimensionBehaviourArr3[0];
                int i27 = i26;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour16 = constraintWidget$DimensionBehaviourArr3[1];
                c0797kv2 = c0797kv5;
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr4 = c0829lq9.f58107e6;
                i2 = size;
                if (!cq0.m212498e2(constraintWidget$DimensionBehaviour15, constraintWidget$DimensionBehaviour16, constraintWidget$DimensionBehaviourArr4[0], constraintWidget$DimensionBehaviourArr4[1]) || (c0829lq9 instanceof C0154c)) {
                    i8 = iMax3;
                    constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviour14;
                    constraintWidget$DimensionBehaviour7 = constraintWidget$DimensionBehaviour9;
                    constraintWidget$DimensionBehaviour6 = constraintWidget$DimensionBehaviour8;
                    constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour11;
                    ab0Var = ab0Var2;
                    break;
                }
                i26 = i27 + 1;
                c0797kv5 = c0797kv2;
                size = i2;
            }
            i2 = size;
            c0797kv2 = c0797kv5;
            i8 = iMax3;
            constraintWidget$DimensionBehaviour6 = constraintWidget$DimensionBehaviour8;
            ArrayList arrayList3 = null;
            int i28 = 0;
            ArrayList arrayList4 = null;
            ArrayList arrayList5 = null;
            ArrayList arrayList6 = null;
            ArrayList arrayList7 = null;
            ArrayList arrayList8 = null;
            while (i28 < size3) {
                int i29 = i28;
                C0829lq c0829lq10 = (C0829lq) arrayList2.get(i28);
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour17 = constraintWidget$DimensionBehaviour9;
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr5 = this.f58107e6;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour18 = constraintWidget$DimensionBehaviourArr5[0];
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour19 = constraintWidget$DimensionBehaviour11;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour20 = constraintWidget$DimensionBehaviourArr5[1];
                ab0 ab0Var3 = ab0Var2;
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr6 = c0829lq10.f58107e6;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour21 = constraintWidget$DimensionBehaviour14;
                if (!cq0.m212498e2(constraintWidget$DimensionBehaviour18, constraintWidget$DimensionBehaviour20, constraintWidget$DimensionBehaviourArr6[0], constraintWidget$DimensionBehaviourArr6[1])) {
                    m213920e8(c0829lq10, c0813la2, this.f58160j3);
                }
                boolean z14 = c0829lq10 instanceof o30;
                if (z14) {
                    o30 o30Var5 = (o30) c0829lq10;
                    if (o30Var5.f58730h6 == 0) {
                        if (arrayList7 == null) {
                            arrayList7 = new ArrayList();
                        }
                        arrayList7.add(o30Var5);
                    }
                    if (o30Var5.f58730h6 == 1) {
                        if (arrayList4 == null) {
                            arrayList4 = new ArrayList();
                        }
                        arrayList4.add(o30Var5);
                    }
                }
                if (c0829lq10 instanceof b40) {
                    if (c0829lq10 instanceof C0392cv) {
                        C0392cv c0392cv3 = (C0392cv) c0829lq10;
                        if (c0392cv3.m212536e8() == 0) {
                            if (arrayList5 == null) {
                                arrayList5 = new ArrayList();
                            }
                            arrayList5.add(c0392cv3);
                        }
                        if (c0392cv3.m212536e8() == 1) {
                            if (arrayList8 == null) {
                                arrayList8 = new ArrayList();
                            }
                            arrayList8.add(c0392cv3);
                        }
                    } else {
                        b40 b40Var = (b40) c0829lq10;
                        if (arrayList5 == null) {
                            arrayList5 = new ArrayList();
                        }
                        arrayList5.add(b40Var);
                        if (arrayList8 == null) {
                            arrayList8 = new ArrayList();
                        }
                        arrayList8.add(b40Var);
                    }
                }
                if (c0829lq10.f58096d5.f57726a5 == null && c0829lq10.f58098d7.f57726a5 == null && !z14 && !(c0829lq10 instanceof C0392cv)) {
                    if (arrayList6 == null) {
                        arrayList6 = new ArrayList();
                    }
                    arrayList6.add(c0829lq10);
                }
                if (c0829lq10.f58097d6.f57726a5 == null && c0829lq10.f58099d8.f57726a5 == null && c0829lq10.f58100d9.f57726a5 == null && !z14 && !(c0829lq10 instanceof C0392cv)) {
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                    }
                    arrayList3.add(c0829lq10);
                }
                i28 = i29 + 1;
                constraintWidget$DimensionBehaviour9 = constraintWidget$DimensionBehaviour17;
                ab0Var2 = ab0Var3;
                constraintWidget$DimensionBehaviour11 = constraintWidget$DimensionBehaviour19;
                constraintWidget$DimensionBehaviour14 = constraintWidget$DimensionBehaviour21;
            }
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour22 = constraintWidget$DimensionBehaviour14;
            constraintWidget$DimensionBehaviour7 = constraintWidget$DimensionBehaviour9;
            ab0 ab0Var4 = ab0Var2;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour23 = constraintWidget$DimensionBehaviour11;
            ArrayList arrayList9 = new ArrayList();
            if (arrayList4 != null) {
                int size4 = arrayList4.size();
                int i30 = 0;
                while (i30 < size4) {
                    Object obj = arrayList4.get(i30);
                    i30++;
                    cq0.m212482b2((o30) obj, 0, arrayList9, null);
                }
            }
            if (arrayList5 != null) {
                int size5 = arrayList5.size();
                int i31 = 0;
                while (i31 < size5) {
                    Object obj2 = arrayList5.get(i31);
                    i31++;
                    b40 b40Var2 = (b40) obj2;
                    qe1 qe1VarM212482b2 = cq0.m212482b2(b40Var2, 0, arrayList9, null);
                    b40Var2.m210537e5(0, qe1VarM212482b2, arrayList9);
                    qe1VarM212482b2.m214382a0(arrayList9);
                }
            }
            HashSet hashSet = mo213885a9(ConstraintAnchor$Type.f44415a0).f57721a0;
            if (hashSet != null) {
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    cq0.m212482b2(((C0797kv) it.next()).f57724a3, 0, arrayList9, null);
                }
            }
            HashSet hashSet2 = mo213885a9(ConstraintAnchor$Type.f44417a2).f57721a0;
            if (hashSet2 != null) {
                Iterator it2 = hashSet2.iterator();
                while (it2.hasNext()) {
                    cq0.m212482b2(((C0797kv) it2.next()).f57724a3, 0, arrayList9, null);
                }
            }
            ConstraintAnchor$Type constraintAnchor$Type = ConstraintAnchor$Type.f44420a5;
            HashSet hashSet3 = mo213885a9(constraintAnchor$Type).f57721a0;
            if (hashSet3 != null) {
                Iterator it3 = hashSet3.iterator();
                while (it3.hasNext()) {
                    cq0.m212482b2(((C0797kv) it3.next()).f57724a3, 0, arrayList9, null);
                }
            }
            if (arrayList6 != null) {
                int size6 = arrayList6.size();
                int i32 = 0;
                while (i32 < size6) {
                    Object obj3 = arrayList6.get(i32);
                    i32++;
                    cq0.m212482b2((C0829lq) obj3, 0, arrayList9, null);
                }
            }
            if (arrayList7 != null) {
                int size7 = arrayList7.size();
                int i33 = 0;
                while (i33 < size7) {
                    Object obj4 = arrayList7.get(i33);
                    i33++;
                    cq0.m212482b2((o30) obj4, 1, arrayList9, null);
                }
            }
            if (arrayList8 != null) {
                int size8 = arrayList8.size();
                int i34 = 0;
                while (i34 < size8) {
                    Object obj5 = arrayList8.get(i34);
                    i34++;
                    b40 b40Var3 = (b40) obj5;
                    qe1 qe1VarM212482b22 = cq0.m212482b2(b40Var3, 1, arrayList9, null);
                    b40Var3.m210537e5(1, qe1VarM212482b22, arrayList9);
                    qe1VarM212482b22.m214382a0(arrayList9);
                }
            }
            HashSet hashSet4 = mo213885a9(ConstraintAnchor$Type.f44416a1).f57721a0;
            if (hashSet4 != null) {
                Iterator it4 = hashSet4.iterator();
                while (it4.hasNext()) {
                    cq0.m212482b2(((C0797kv) it4.next()).f57724a3, 1, arrayList9, null);
                }
            }
            HashSet hashSet5 = mo213885a9(ConstraintAnchor$Type.f44419a4).f57721a0;
            if (hashSet5 != null) {
                Iterator it5 = hashSet5.iterator();
                while (it5.hasNext()) {
                    cq0.m212482b2(((C0797kv) it5.next()).f57724a3, 1, arrayList9, null);
                }
            }
            HashSet hashSet6 = mo213885a9(ConstraintAnchor$Type.f44418a3).f57721a0;
            if (hashSet6 != null) {
                Iterator it6 = hashSet6.iterator();
                while (it6.hasNext()) {
                    cq0.m212482b2(((C0797kv) it6.next()).f57724a3, 1, arrayList9, null);
                }
            }
            HashSet hashSet7 = mo213885a9(constraintAnchor$Type).f57721a0;
            if (hashSet7 != null) {
                Iterator it7 = hashSet7.iterator();
                while (it7.hasNext()) {
                    cq0.m212482b2(((C0797kv) it7.next()).f57724a3, 1, arrayList9, null);
                }
            }
            if (arrayList3 != null) {
                int size9 = arrayList3.size();
                int i35 = 0;
                while (i35 < size9) {
                    Object obj6 = arrayList3.get(i35);
                    i35++;
                    cq0.m212482b2((C0829lq) obj6, 1, arrayList9, null);
                }
            }
            char c2 = 1;
            int i36 = 0;
            while (i36 < size3) {
                C0829lq c0829lq11 = (C0829lq) arrayList2.get(i36);
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr7 = c0829lq11.f58107e6;
                if (constraintWidget$DimensionBehaviourArr7[0] == constraintWidget$DimensionBehaviour10 && constraintWidget$DimensionBehaviourArr7[c2] == constraintWidget$DimensionBehaviour10) {
                    int i37 = c0829lq11.f58131h0;
                    int size10 = arrayList9.size();
                    int i38 = 0;
                    while (true) {
                        if (i38 >= size10) {
                            qe1Var2 = null;
                            break;
                        }
                        qe1Var2 = (qe1) arrayList9.get(i38);
                        if (i37 == qe1Var2.f59486a1) {
                            break;
                        } else {
                            i38++;
                        }
                    }
                    int i39 = c0829lq11.f58132h1;
                    int size11 = arrayList9.size();
                    int i40 = 0;
                    while (true) {
                        if (i40 >= size11) {
                            qe1Var3 = null;
                            break;
                        }
                        qe1Var3 = (qe1) arrayList9.get(i40);
                        if (i39 == qe1Var3.f59486a1) {
                            break;
                        } else {
                            i40++;
                        }
                    }
                    if (qe1Var2 != null && qe1Var3 != null) {
                        qe1Var2.m214384a2(0, qe1Var3);
                        qe1Var3.f59487a2 = 2;
                        arrayList9.remove(qe1Var2);
                    }
                }
                i36++;
                c2 = 1;
            }
            if (arrayList9.size() > 1) {
                constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviour22;
                if (this.f58107e6[0] == constraintWidget$DimensionBehaviour) {
                    int size12 = arrayList9.size();
                    int i41 = 0;
                    int i42 = 0;
                    qe1Var = null;
                    while (i42 < size12) {
                        Object obj7 = arrayList9.get(i42);
                        i42++;
                        qe1 qe1Var4 = (qe1) obj7;
                        if (qe1Var4.f59487a2 != 1) {
                            ab0 ab0Var5 = ab0Var4;
                            int iM214383a12 = qe1Var4.m214383a1(ab0Var5, 0);
                            if (iM214383a12 > i41) {
                                qe1Var = qe1Var4;
                                i41 = iM214383a12;
                            }
                            ab0Var4 = ab0Var5;
                        }
                    }
                    ab0Var = ab0Var4;
                    constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour23;
                    if (qe1Var != null) {
                        m213909d9(constraintWidget$DimensionBehaviour4);
                        m213911e1(i41);
                    }
                    if (this.f58107e6[1] != constraintWidget$DimensionBehaviour) {
                        int size13 = arrayList9.size();
                        int i43 = 0;
                        int i44 = 0;
                        qe1 qe1Var5 = null;
                        while (i44 < size13) {
                            Object obj8 = arrayList9.get(i44);
                            i44++;
                            qe1 qe1Var6 = (qe1) obj8;
                            if (qe1Var6.f59487a2 != 0 && (iM214383a1 = qe1Var6.m214383a1(ab0Var, 1)) > i43) {
                                qe1Var5 = qe1Var6;
                                i43 = iM214383a1;
                            }
                        }
                        if (qe1Var5 != null) {
                            m213910e0(constraintWidget$DimensionBehaviour4);
                            m213908d8(i43);
                        } else {
                            qe1Var5 = null;
                        }
                        if (qe1Var != null || qe1Var5 != null) {
                            constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour7;
                            if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                                i9 = i8;
                                if (i9 >= m213891b7() || i9 <= 0) {
                                    iM213891b7 = m213891b7();
                                    constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviour6;
                                    if (constraintWidget$DimensionBehaviour3 != constraintWidget$DimensionBehaviour) {
                                        i10 = i;
                                        if (i10 >= m213887b1() || i10 <= 0) {
                                            iM213887b1 = m213887b1();
                                            i4 = iM213887b1;
                                            i3 = iM213891b7;
                                            z = true;
                                        } else {
                                            m213908d8(i10);
                                            this.f58154i7 = true;
                                        }
                                    } else {
                                        i10 = i;
                                    }
                                    iM213887b1 = i10;
                                    i4 = iM213887b1;
                                    i3 = iM213891b7;
                                    z = true;
                                } else {
                                    m213911e1(i9);
                                    this.f58153i6 = true;
                                }
                            } else {
                                i9 = i8;
                            }
                            iM213891b7 = i9;
                            constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviour6;
                            if (constraintWidget$DimensionBehaviour3 != constraintWidget$DimensionBehaviour) {
                            }
                            iM213887b1 = i10;
                            i4 = iM213887b1;
                            i3 = iM213891b7;
                            z = true;
                        }
                    }
                    boolean z15 = m213925e9(64) || m213925e9(128);
                    ab0Var.getClass();
                    ab0Var.f43598a6 = false;
                    if (this.f58152i5 == 0 || !z15) {
                        c = 1;
                    } else {
                        c = 1;
                        ab0Var.f43598a6 = true;
                    }
                    ArrayList arrayList10 = this.f58139h2;
                    ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr8 = this.f58107e6;
                    boolean z16 = constraintWidget$DimensionBehaviourArr8[0] == constraintWidget$DimensionBehaviour || constraintWidget$DimensionBehaviourArr8[c] == constraintWidget$DimensionBehaviour;
                    this.f58148i1 = 0;
                    this.f58149i2 = 0;
                    i5 = i2;
                    for (i6 = 0; i6 < i5; i6++) {
                        C0829lq c0829lq12 = (C0829lq) this.f58139h2.get(i6);
                        if (c0829lq12 instanceof C0830lr) {
                            ((C0830lr) c0829lq12).m213924e7();
                        }
                    }
                    boolean zM213925e9 = m213925e9(64);
                    boolean z17 = z;
                    int i45 = 0;
                    z2 = true;
                    while (z2) {
                        int i46 = i45 + 1;
                        try {
                            ab0Var.m209777b9();
                            constraintWidget$DimensionBehaviour5 = constraintWidget$DimensionBehaviour4;
                        } catch (Exception e) {
                            e = e;
                            constraintWidget$DimensionBehaviour5 = constraintWidget$DimensionBehaviour4;
                        }
                        try {
                            this.f58148i1 = 0;
                            this.f58149i2 = 0;
                            m213883a7(ab0Var);
                            for (int i47 = 0; i47 < i5; i47++) {
                                ((C0829lq) this.f58139h2.get(i47)).m213883a7(ab0Var);
                            }
                            m213922e5(ab0Var);
                            try {
                                WeakReference weakReference = this.f58155i8;
                                if (weakReference == null || weakReference.get() == null) {
                                    z4 = z16;
                                    z5 = z17;
                                    c0797kv3 = c0797kv2;
                                } else {
                                    c0797kv3 = c0797kv2;
                                    try {
                                        z4 = z16;
                                        z5 = z17;
                                        try {
                                            ab0Var.m209764a5(ab0Var.m209769b0((C0797kv) this.f58155i8.get()), ab0Var.m209769b0(c0797kv3), 0, 5);
                                            this.f58155i8 = null;
                                        } catch (Exception e2) {
                                            e = e2;
                                            c0797kv2 = c0797kv3;
                                            z2 = true;
                                            System.out.println("EXCEPTION : " + e);
                                            if (z2) {
                                            }
                                            if (z4) {
                                            }
                                            iMax = Math.max(this.f58116f5, m213891b7());
                                            if (iMax > m213891b7()) {
                                            }
                                            iMax2 = Math.max(this.f58117f6, m213887b1());
                                            if (iMax2 > m213887b1()) {
                                            }
                                            if (z5) {
                                            }
                                            if (i46 > i7) {
                                            }
                                            i45 = i46;
                                            z16 = z4;
                                            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour5;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        z4 = z16;
                                        z5 = z17;
                                    }
                                }
                                try {
                                    WeakReference weakReference2 = this.f58157j0;
                                    if (weakReference2 != null && weakReference2.get() != null) {
                                        ab0Var.m209764a5(ab0Var.m209769b0(this.f58099d8), ab0Var.m209769b0((C0797kv) this.f58157j0.get()), 0, 5);
                                        this.f58157j0 = null;
                                    }
                                    WeakReference weakReference3 = this.f58156i9;
                                    if (weakReference3 != null && weakReference3.get() != null) {
                                        C0797kv c0797kv7 = c0797kv;
                                        try {
                                            c0797kv = c0797kv7;
                                            ab0Var.m209764a5(ab0Var.m209769b0((C0797kv) this.f58156i9.get()), ab0Var.m209769b0(c0797kv7), 0, 5);
                                            this.f58156i9 = null;
                                        } catch (Exception e4) {
                                            e = e4;
                                            c0797kv = c0797kv7;
                                            c0797kv2 = c0797kv3;
                                            z2 = true;
                                            System.out.println("EXCEPTION : " + e);
                                            if (z2) {
                                            }
                                            if (z4) {
                                            }
                                            iMax = Math.max(this.f58116f5, m213891b7());
                                            if (iMax > m213891b7()) {
                                            }
                                            iMax2 = Math.max(this.f58117f6, m213887b1());
                                            if (iMax2 > m213887b1()) {
                                            }
                                            if (z5) {
                                            }
                                            if (i46 > i7) {
                                            }
                                            i45 = i46;
                                            z16 = z4;
                                            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour5;
                                        }
                                    }
                                    WeakReference weakReference4 = this.f58158j1;
                                    if (weakReference4 != null && weakReference4.get() != null) {
                                        ab0Var.m209764a5(ab0Var.m209769b0(this.f58098d7), ab0Var.m209769b0((C0797kv) this.f58158j1.get()), 0, 5);
                                        try {
                                            this.f58158j1 = null;
                                        } catch (Exception e5) {
                                            e = e5;
                                            c0797kv2 = c0797kv3;
                                            z2 = true;
                                            System.out.println("EXCEPTION : " + e);
                                            if (z2) {
                                            }
                                            if (z4) {
                                            }
                                            iMax = Math.max(this.f58116f5, m213891b7());
                                            if (iMax > m213891b7()) {
                                            }
                                            iMax2 = Math.max(this.f58117f6, m213887b1());
                                            if (iMax2 > m213887b1()) {
                                            }
                                            if (z5) {
                                            }
                                            if (i46 > i7) {
                                            }
                                            i45 = i46;
                                            z16 = z4;
                                            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour5;
                                        }
                                    }
                                    ab0Var.m209773b5();
                                    c0797kv2 = c0797kv3;
                                    z2 = true;
                                } catch (Exception e6) {
                                    e = e6;
                                }
                            } catch (Exception e7) {
                                e = e7;
                                z4 = z16;
                                z5 = z17;
                            }
                        } catch (Exception e8) {
                            e = e8;
                            z4 = z16;
                            z5 = z17;
                            System.out.println("EXCEPTION : " + e);
                            if (z2) {
                            }
                            if (z4) {
                            }
                            iMax = Math.max(this.f58116f5, m213891b7());
                            if (iMax > m213891b7()) {
                            }
                            iMax2 = Math.max(this.f58117f6, m213887b1());
                            if (iMax2 > m213887b1()) {
                            }
                            if (z5) {
                            }
                            if (i46 > i7) {
                            }
                            i45 = i46;
                            z16 = z4;
                            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour5;
                        }
                        if (z2) {
                            zArr[2] = false;
                            boolean zM213925e92 = m213925e9(64);
                            mo213913e3(ab0Var, zM213925e92);
                            int size14 = this.f58139h2.size();
                            z6 = false;
                            int i48 = 0;
                            while (i48 < size14) {
                                C0829lq c0829lq13 = (C0829lq) this.f58139h2.get(i48);
                                c0829lq13.mo213913e3(ab0Var, zM213925e92);
                                boolean z18 = zM213925e92;
                                int i49 = size14;
                                if (c0829lq13.f58068a7 != -1 || c0829lq13.f58069a8 != -1) {
                                    z6 = true;
                                }
                                i48++;
                                zM213925e92 = z18;
                                size14 = i49;
                            }
                        } else {
                            mo213913e3(ab0Var, zM213925e9);
                            for (int i50 = 0; i50 < i5; i50++) {
                                ((C0829lq) this.f58139h2.get(i50)).mo213913e3(ab0Var, zM213925e9);
                            }
                            z6 = false;
                        }
                        if (z4 && i46 < 8) {
                            if (zArr[2]) {
                                int iMax5 = 0;
                                int iMax6 = 0;
                                for (int i51 = 0; i51 < i5; i51++) {
                                    C0829lq c0829lq14 = (C0829lq) this.f58139h2.get(i51);
                                    iMax6 = Math.max(iMax6, c0829lq14.m213891b7() + c0829lq14.f58113f2);
                                    iMax5 = Math.max(iMax5, c0829lq14.m213887b1() + c0829lq14.f58114f3);
                                }
                                int iMax7 = Math.max(this.f58116f5, iMax6);
                                int iMax8 = Math.max(this.f58117f6, iMax5);
                                if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour && m213891b7() < iMax7) {
                                    m213911e1(iMax7);
                                    this.f58107e6[0] = constraintWidget$DimensionBehaviour;
                                    z6 = true;
                                    z5 = true;
                                }
                                if (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour && m213887b1() < iMax8) {
                                    m213908d8(iMax8);
                                    this.f58107e6[1] = constraintWidget$DimensionBehaviour;
                                    z6 = true;
                                    z5 = true;
                                }
                            }
                        }
                        iMax = Math.max(this.f58116f5, m213891b7());
                        if (iMax > m213891b7()) {
                            m213911e1(iMax);
                            this.f58107e6[0] = constraintWidget$DimensionBehaviour5;
                            z6 = true;
                            z5 = true;
                        }
                        iMax2 = Math.max(this.f58117f6, m213887b1());
                        if (iMax2 > m213887b1()) {
                            m213908d8(iMax2);
                            r15 = 1;
                            this.f58107e6[1] = constraintWidget$DimensionBehaviour5;
                            z6 = true;
                            z5 = true;
                        } else {
                            r15 = 1;
                        }
                        if (z5) {
                            z7 = z6;
                            z17 = z5;
                            i7 = 8;
                        } else {
                            if (this.f58107e6[0] == constraintWidget$DimensionBehaviour && i3 > 0 && m213891b7() > i3) {
                                this.f58153i6 = r15;
                                this.f58107e6[0] = constraintWidget$DimensionBehaviour5;
                                m213911e1(i3);
                                z6 = r15;
                                z5 = z6;
                            }
                            if (this.f58107e6[r15] == constraintWidget$DimensionBehaviour && i4 > 0 && m213887b1() > i4) {
                                this.f58154i7 = r15;
                                this.f58107e6[r15] = constraintWidget$DimensionBehaviour5;
                                m213908d8(i4);
                                i7 = 8;
                                z7 = true;
                                z17 = true;
                            }
                        }
                        z2 = i46 > i7 ? false : z7;
                        i45 = i46;
                        z16 = z4;
                        constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour5;
                    }
                    z3 = z17;
                    this.f58139h2 = arrayList10;
                    if (z3) {
                        ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr9 = this.f58107e6;
                        constraintWidget$DimensionBehaviourArr9[0] = constraintWidget$DimensionBehaviour2;
                        constraintWidget$DimensionBehaviourArr9[1] = constraintWidget$DimensionBehaviour3;
                    }
                    mo213904d2(ab0Var.f43603b1);
                }
                ab0Var = ab0Var4;
                constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour23;
                qe1Var = null;
                if (this.f58107e6[1] != constraintWidget$DimensionBehaviour) {
                }
                if (m213925e9(64)) {
                }
                ab0Var.getClass();
                ab0Var.f43598a6 = false;
                if (this.f58152i5 == 0) {
                    c = 1;
                }
                ArrayList arrayList102 = this.f58139h2;
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr82 = this.f58107e6;
                if (constraintWidget$DimensionBehaviourArr82[0] == constraintWidget$DimensionBehaviour) {
                }
                this.f58148i1 = 0;
                this.f58149i2 = 0;
                i5 = i2;
                while (i6 < i5) {
                }
                boolean zM213925e93 = m213925e9(64);
                boolean z172 = z;
                int i452 = 0;
                z2 = true;
                while (z2) {
                }
                z3 = z172;
                this.f58139h2 = arrayList102;
                if (z3) {
                }
                mo213904d2(ab0Var.f43603b1);
            }
            ab0Var = ab0Var4;
            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour23;
            constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviour22;
            i4 = i;
            constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviour6;
            i3 = i8;
            constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour7;
        }
        z = false;
        if (m213925e9(64)) {
        }
        ab0Var.getClass();
        ab0Var.f43598a6 = false;
        if (this.f58152i5 == 0) {
        }
        ArrayList arrayList1022 = this.f58139h2;
        ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr822 = this.f58107e6;
        if (constraintWidget$DimensionBehaviourArr822[0] == constraintWidget$DimensionBehaviour) {
        }
        this.f58148i1 = 0;
        this.f58149i2 = 0;
        i5 = i2;
        while (i6 < i5) {
        }
        boolean zM213925e932 = m213925e9(64);
        boolean z1722 = z;
        int i4522 = 0;
        z2 = true;
        while (z2) {
        }
        z3 = z1722;
        this.f58139h2 = arrayList1022;
        if (z3) {
        }
        mo213904d2(ab0Var.f43603b1);
    }

    /* renamed from: e9 */
    public final boolean m213925e9(int i) {
        return (this.f58152i5 & i) == i;
    }
}
